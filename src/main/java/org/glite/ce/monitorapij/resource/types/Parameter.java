/*
 * Copyright (c) Members of the EGEE Collaboration. 2004. 
 * See http://www.eu-egee.org/partners/ for details on the copyright
 * holders.  
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */


package org.glite.ce.monitorapij.resource.types;

import java.io.Serializable;

/**
 * This class is used by <code>_Action</code> class to to keep trace of a single
 * parameter which must be analyzed while performing an action or a
 * notification.
 * 
 */
public class Parameter
    implements Serializable {

    public static final long serialVersionUID = 1319027349;

    private String name;

    private Object value;

    /**
     * Create a new _Parameter object without name and a value.
     */
    public Parameter() {
    }

    /**
     * Creates a new _Parameter object specifying a name and a value.
     * 
     * @param name
     *            The name of this parameter.
     * @param value
     *            The associated value.
     */
    public Parameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Parameter(org.glite.ce.monitorapij.types.Parameter param) {
        this.name = param.getName();
        this.value = param.getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
