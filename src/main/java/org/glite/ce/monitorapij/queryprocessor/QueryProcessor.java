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

import org.glite.ce.monitorapij.sensor.SensorEvent;
import org.glite.ce.monitorapij.resource.types.Query;


/**
 * Instances of classes that implement this interface are used to process queries,
 * that is evaluating if a sensor event matches a specified query.
 * 
 */
public interface QueryProcessor {
    /**
     * Get the name of this QueryProcessor.
     *
     * @return The name of this QueryProcessor.
     */
    public String getName();

    /**
     * Evaluate if the specified event satisfies the query requirements. 
     *
     * @param query The <code>Query</code> to be processed.
     * @param event The <code>Event</code> to be evaluated.
     *
     * @return An array of <code>QueryResult</code> containing the response of the evaluation.
     *
     * @throws Exception Throws exception if parameters are not correctly specified.
     */
    public QueryResult evaluate(Query query, SensorEvent event) throws Exception;
}
