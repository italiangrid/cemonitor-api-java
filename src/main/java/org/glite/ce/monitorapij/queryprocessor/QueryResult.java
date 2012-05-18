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

package org.glite.ce.monitorapij.queryprocessor;


/**
 * This class is used to store the result of a query processing operation. It
 * contains a boolean specifying if an analized event matches a query
 * requirement, the event message and a description String added by the
 * <code>QueryProcessor</code>.
 */
public class QueryResult {
    // private boolean successfully;
    // private String message;
    // private String description;
    boolean[] resultTable;



    /**
     * Creates a new QueryResult object.
     * 
     * @param successfully
     *            A boolean specifying if an analyzed event matches a query
     *            requirement
     * @param message
     *            The event message.
     * @param description
     *            A description added by the <code>QueryProcessor</code>.
     */
    // public QueryResult(boolean successfully, String message, String
    // description) {
    // this.successfully = successfully;
    // this.message = message;
    // this.description = description;
    // }
    public QueryResult(int size) {
        if(size < 0) {
            resultTable = new boolean[0];
        } else {
            resultTable = new boolean[size];
            for (int i = 0; i < size; i++) {
                resultTable[i] = false;
            }
        }
    }



    /**
     * Check if the result of the query is positive or not.
     * 
     * @return A boolean result.
     */
    public boolean isSuccessfully(int i) {
        return resultTable[i];
    }



    public void setResult(int i, boolean b) {
        resultTable[i] = b;
    }



    public int size() {
        return resultTable.length;
    }

    /**
     * Get the message of the evaluated event.
     * 
     * @return The message of the evaluated event.
     */
    // public String getMessage() {
    // return message;
    // }
    /**
     * Get the description of the evaluation.
     * 
     * @return The description of the evaluation.
     */
    // public String getDescription() {
    // return description;
    // }
}
