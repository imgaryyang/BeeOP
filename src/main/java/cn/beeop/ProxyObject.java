/*
 * Copyright Chris2018998
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.beeop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static cn.beeop.StaticCenter.ObjectClosedException;
import static cn.beeop.StaticCenter.ObjectMethodMap;

/**
 * Object Proxy
 *
 * @author Chris.Liao
 * @version 1.0
 */
public class ProxyObject {
    private Object delegate;
    private PooledEntry pEntry;
    private boolean isClosed;

    public ProxyObject(PooledEntry pEntry) {
        this.pEntry = pEntry;
        pEntry.proxyObject = this;
        this.delegate = pEntry.object;
    }

    public boolean isClosed() throws ObjectException {
        return isClosed;
    }

    public String toString() {
        return pEntry.toString();
    }

    public final void close() throws ObjectException {
        synchronized (this) {//safe close
            if (isClosed) return;
            isClosed = true;
        }
        pEntry.recycleSelf();
    }

    final void trySetAsClosed() {//called from ObjectPool
        try {
            close();
        } catch (ObjectException e) {
        }
    }

    public final Object call(String name, Class[] types, Object[] params) throws ObjectException {
        try {
            if (isClosed) throw ObjectClosedException;
            MethodCallKey key = new MethodCallKey(name, types);
            Method method = ObjectMethodMap.get(key);
            if (method == null) {
                method = delegate.getClass().getMethod(name, types);
                ObjectMethodMap.put(key, method);
            }

            Object v = method.invoke(delegate, params);
            pEntry.updateAccessTime();
            return v;
        } catch (NoSuchMethodException e) {
            throw new ObjectException(e);
        } catch (IllegalAccessException e) {
            throw new ObjectException(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() != null) {
                throw new ObjectException(e.getCause());
            } else {
                throw new ObjectException(e);
            }
        }
    }
}
