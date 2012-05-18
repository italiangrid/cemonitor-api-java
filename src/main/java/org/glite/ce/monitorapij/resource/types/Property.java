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

/*
 *
 * Author Luigi Zangrando <zangrando@pd.infn.it>
 *
 */

package org.glite.ce.monitorapij.resource.types;

import java.io.Serializable;

/**
 * This class represents a property of a <code>Resource</code>. Each property
 * applies to all instances of that type of resource.
 */
public class Property
    implements Serializable {

    public static final long serialVersionUID = 1319027474;

    private String name, value;

    /**
     * Creates a new Property object without a name and a value.
     */
    public Property() {
        this(null, null);
    }

    /**
     * Creates a new Property object specifying a name and a value.
     * 
     * @param name
     *            The name of this property.
     * @param value
     *            The associated value.
     */
    public Property(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Property(org.glite.ce.monitorapij.types.Property prop) {
        this.name = prop.getName();
        this.value = prop.getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
