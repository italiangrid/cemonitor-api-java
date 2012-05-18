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
import java.util.HashMap;

import org.glite.ce.monitorapij.resource.Resource;

/**
 * This class is used to make a set of actions regarding notifications when a
 * <code>QueryResult</code> brings a successful result.
 */
public class Action
    extends CEMonResource
    implements Resource, Serializable {

    private boolean doActionWhenQueryIs;

    private HashMap<String, Parameter> parameterMap;

    /**
     * Creates a new _Action object without a name.
     */
    public Action() {
        this("");
    }

    /**
     * Creates a new _Action object specifying a name.
     * 
     * @param name
     *            The specified name.
     */
    public Action(String name) {
        super();

        setName(name);
        setType("Action");
        parameterMap = new HashMap<String, Parameter>();
    }

    public Action(String name, Parameter[] parameter) {
        super();

        setName(name);
        setType("Action");

        parameterMap = new HashMap<String, Parameter>();

        for (int i = 0; i < parameter.length; i++) {
            parameterMap.put(parameter[i].getName(), parameter[i]);
        }
    }

    public Action(org.glite.ce.monitorapij.types.Action action) {
        super(action);

        this.doActionWhenQueryIs = action.getDoActionWhenQueryIs();

        parameterMap = new HashMap<String, Parameter>();

        org.glite.ce.monitorapij.types.Parameter[] oldParams = action.getParameter();
        for (int k = 0; k < oldParams.length; k++) {
            parameterMap.put(oldParams[k].getName(), new Parameter(oldParams[k]));
        }

    }

    public Parameter getParameter(String name) {
        return (Parameter) parameterMap.get(name);
    }

    /**
     * Execute a set of actions on specified notification when results are
     * successful or not.
     * 
     * @param notification
     *            The notification to be performed.
     * @param results
     *            The evaluated <code>QueryResult</code>s
     * 
     * @throws Exception
     *             Throw when _Notification or QueryResults are not correctly
     *             specified.
     */
    public void execute()
        throws Exception {
    }

    /**
     * Gets the doActionWhenQueryIs value for this ActionBase.
     * 
     * @return doActionWhenQueryIs
     */
    public boolean isDoActionWhenQueryIs() {
        return doActionWhenQueryIs;
    }

    /**
     * Sets the doActionWhenQueryIs value for this ActionBase.
     * 
     * @param doActionWhenQueryIs
     */
    public void setDoActionWhenQueryIs(boolean doActionWhenQueryIs) {
        this.doActionWhenQueryIs = doActionWhenQueryIs;
    }

    /**
     * Gets the parameter value for this ActionBase.
     * 
     * @return parameter
     */
    public Parameter[] getParameter() {
        Parameter[] parameter = new Parameter[parameterMap.size()];
        if (parameterMap.size() > 0) {
            parameter = (Parameter[]) parameterMap.values().toArray(parameter);
        }
        return parameter;
    }

    /**
     * Sets the parameter value for this ActionBase.
     * 
     * @param parameter
     */
    public void setParameter(Parameter[] parameter) {

        parameterMap.clear();

        for (int i = 0; i < parameter.length; i++) {
            parameterMap.put(parameter[i].getName(), parameter[i]);
        }
    }

    public void addParameter(Parameter parameter) {
        if (parameter != null) {
            parameterMap.put(parameter.getName(), parameter);
        }
    }

    public void addParameter(Parameter[] parameter) {
        if (parameter != null) {
            for (int i = 0; i < parameter.length; i++) {
                parameterMap.put(parameter[i].getName(), parameter[i]);
            }
        }
    }

    public org.glite.ce.monitorapij.types.Action cloneAction() {

        org.glite.ce.monitorapij.types.Action result = new org.glite.ce.monitorapij.types.Action();
        result.setDoActionWhenQueryIs(this.doActionWhenQueryIs);
        result.setName(this.name);
        result.setId(this.id);
        result.setType(this.type);
        result.setJarPath(this.cloneURI());
        result.setProperty(this.cloneProperties());

        org.glite.ce.monitorapij.types.Parameter[] params = new org.glite.ce.monitorapij.types.Parameter[parameterMap
                .size()];
        int k = 0;
        for (Parameter par : parameterMap.values()) {
            params[k].setName(par.getName());
            params[k].setValue(par.getValue().toString());
            k++;
        }

        return result;
    }

}
