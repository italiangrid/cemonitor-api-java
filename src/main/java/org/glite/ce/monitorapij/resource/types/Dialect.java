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
 * This class identifies a particular dialect supported by a sensor. A Dialect
 * is intended as a particular way of expressing the output of a sensor and
 * supports a certain number of query languages.
 */
public class Dialect
    implements Serializable {

    public static final long serialVersionUID = 1319030838;

    private String name;

    private String[] queryLanguage;

    /**
     * Creates a new _Dialect object without a name.
     */
    public Dialect() {
        this("");
    }

    /**
     * Creates a new _Dialect object with the specified name.
     * 
     * @param dialectName
     *            The name of the dialect.
     */
    public Dialect(String dialectName) {
        name = dialectName;
    }

    public Dialect(String dialectName, String[] queryLanguage) {
        this.name = dialectName;
        this.queryLanguage = queryLanguage;
    }

    public Dialect(org.glite.ce.monitorapij.types.Dialect dialect) {
        this.name = dialect.getName();
        this.queryLanguage = dialect.getQueryLanguage();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getQueryLanguage() {
        return queryLanguage;
    }

    public void setQueryLanguage(String[] queryLanguage) {
        this.queryLanguage = queryLanguage;
    }

    public org.glite.ce.monitorapij.types.Dialect cloneDialect() {
        org.glite.ce.monitorapij.types.Dialect result = new org.glite.ce.monitorapij.types.Dialect();
        result.setName(this.name);
        result.setQueryLanguage(this.queryLanguage);
        return result;
    }

    /**
     * Check if this dialect supports the query language specified by its name.
     * 
     * @param ql_name
     *            The checked query language.
     * 
     * @return True if the query language is supported, false otherwise.
     */
    public boolean checkSupportedQueryLanguage(String ql_name) {
        if (ql_name == null) {
            return false;
        }

        String[] queryLanguages = getQueryLanguage();

        for (int i = 0; i < queryLanguages.length; i++) {
            if (queryLanguages[i].equalsIgnoreCase(ql_name)) {
                return true;
            }
        }

        return false;
    }
}
