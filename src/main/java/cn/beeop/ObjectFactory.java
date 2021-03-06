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

import java.util.Properties;

/**
 * Object Factory
 *
 * @author Chris.Liao
 * @version 1.0
 */
public class ObjectFactory {
    //create object instance
    public Object create(Properties prop) throws ObjectException {
        return new Object();
    }

    //set default values
    public void setDefault(Object obj) throws ObjectException {

    }

    //set default values
    public void reset(Object obj) throws ObjectException {

    }

    //destroy  object
    public void destroy(Object obj) {

    }

    //test object
    public boolean isAlive(Object obj, long timeout) {
        return true;
    }
}
