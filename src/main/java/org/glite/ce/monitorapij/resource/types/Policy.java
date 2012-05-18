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

public class Policy
    implements Serializable {

    public static final long serialVersionUID = 1319031104;

    private Query query;

    private Action[] action;

    private int rate;

    public Policy() {
    }

    public Policy(Query query, Action[] action, int rate) {
        this.query = query;
        this.action = action;
        this.rate = rate;
    }

    public Policy(org.glite.ce.monitorapij.types.Policy policy) {
        this.query = new Query(policy.getQuery());

        org.glite.ce.monitorapij.types.Action[] actList = policy.getAction();
        this.action = new Action[actList.length];
        for (int k = 0; k < actList.length; k++) {
            this.action[k] = new Action(actList[k]);
        }

        this.rate = policy.getRate();
    }

    /**
     * Gets the query value for this Policy.
     * 
     * @return query
     */
    public Query getQuery() {
        return query;
    }

    /**
     * Sets the query value for this Policy.
     * 
     * @param query
     */
    public void setQuery(Query query) {
        this.query = query;
    }

    /**
     * Gets the action value for this Policy.
     * 
     * @return action
     */
    public Action[] getAction() {
        return action;
    }

    /**
     * Sets the action value for this Policy.
     * 
     * @param action
     */
    public void setAction(Action[] action) {
        this.action = action;
    }

    public Action getAction(int i) {
        return this.action[i];
    }

    public void setAction(int i, Action _value) {
        this.action[i] = _value;
    }

    /**
     * Gets the rate value for this Policy.
     * 
     * @return rate
     */
    public int getRate() {
        return rate;
    }

    /**
     * Sets the rate value for this Policy.
     * 
     * @param rate
     */
    public void setRate(int rate) {
        this.rate = rate;
    }

    public org.glite.ce.monitorapij.types.Policy clonePolicy() {
        org.glite.ce.monitorapij.types.Policy result = new org.glite.ce.monitorapij.types.Policy();
        result.setRate(rate);
        result.setQuery(this.query.cloneQuery());

        org.glite.ce.monitorapij.types.Action[] actList = new org.glite.ce.monitorapij.types.Action[this.action.length];
        for (int k = 0; k < this.action.length; k++) {
            actList[k] = action[k].cloneAction();
        }

        result.setAction(actList);

        return result;
    }

}
