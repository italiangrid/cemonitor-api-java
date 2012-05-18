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

package org.glite.ce.monitorapij.sensor;

import java.util.Hashtable;


/**
 * This class contains the specifications of the output response.
 * Each Sensor must specify its supported output format extending this class.
 * It is also used by <code>CEMonitor</code> to format its output as desired.
 *
 */
public abstract class SensorOutputDataFormat {
    private String name;

    private String[] supportedQueryLang;

    /**
     * Basic constructor specifying just the name of this output format.
     * The queryLang field is set to null.
     * @param name The name of this output format.
     */
    public SensorOutputDataFormat(String name) {
        this(name, null);
    }

    /**
     * Construct a SensorOutputDataFormat specifying its name and an array of the names of supported query languages.
     * @param name The name of this output format.
     * @param queryLang An array of String specifying the names of supported query languages.
     */
    public SensorOutputDataFormat(String name, String[] queryLang) {
        this.name = name;

        supportedQueryLang = queryLang;

    }

    /**
     * Get the name of the <code>SensorOutputDataFormat</code>.
     * @return The name of the <code>SensorOutputDataFormat</code>.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the query languages supported by this output format.
     * @param queryLangs An array of String specifying the names of supported query languages.
     */
    public void setSupportedQueryLang(String[] queryLangs) {
        supportedQueryLang = queryLangs;
    }

    /**
     * @return An array of String specifying the names of supported query languages.
     */
    public String[] getSupportedQueryLang() {

        return supportedQueryLang;

    }

    /**
     * Check if specified query language is supported by this <code>SensorOutputDataFormat</code>.
     * @param queryLang The query language name.
     * @return True if the query language is supported, false otherwise.
     */
    public boolean isSupportedQueryLang(String queryLang) {
        if ((supportedQueryLang == null) || (queryLang == null)) {
            return false;
        }

        for (int i = 0; i < supportedQueryLang.length; i++) {
            if (supportedQueryLang[i].equalsIgnoreCase(queryLang)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Apply this format to an output message specifying a set of parameters to consider.
     *
     * @param parameters A set of parameters to be considered while applying this format.
     *
     * @return A String array containing the output messages.
     *
     * @throws Exception
     */
    public abstract String[] apply(Hashtable<String, Object> parameters) throws Exception;
}
