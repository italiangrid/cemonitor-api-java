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

public class Query
    implements Serializable {

    public static final long serialVersionUID = 1319031596;

    private String expression;

    private String queryLanguage;

    public Query() {
    }

    public Query(String expression, String queryLanguage) {
        this.expression = expression;
        this.queryLanguage = queryLanguage;
    }

    public Query(org.glite.ce.monitorapij.types.Query query) {
        this.expression = query.getExpression();
        this.queryLanguage = query.getQueryLanguage();
    }

    /**
     * Gets the expression value for this Query.
     * 
     * @return expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Sets the expression value for this Query.
     * 
     * @param expression
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getQueryLanguage() {
        return queryLanguage;
    }

    public void setQueryLanguage(String queryLanguage) {
        this.queryLanguage = queryLanguage;
    }

    public org.glite.ce.monitorapij.types.Query cloneQuery() {
        org.glite.ce.monitorapij.types.Query result = new org.glite.ce.monitorapij.types.Query();
        result.setExpression(this.expression);
        result.setQueryLanguage(this.queryLanguage);
        return result;
    }

}
