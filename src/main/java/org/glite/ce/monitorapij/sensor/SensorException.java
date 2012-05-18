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


/**
 * Exception raised by a <code>Sensor</code>.
 */
public class SensorException extends Exception {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    public static final int ERROR = 0;
    public static final int WARNING = 1;
    private int exceptionID = ERROR;

    /**
     * Constructor from superclass <code>Exception</code>.
     */
    public SensorException() {
        super();
    }

    /**
     * Constructor with a specified message String.
     * The exceptionID is set to ERROR.
     * @param msg The String specifying the message.
     */
    public SensorException(String msg) {
        this(ERROR, msg);
    }

    /**
     *
     * Constructor with a specified message String and exceptionID int.
     * @param exceptionID The int specifying the type of exception (ERROR = 0; WARNING = 1).
     * @param msg The String specifying the message.
     */
    public SensorException(int exceptionID, String msg) {
        super(msg);
        setExceptionID(exceptionID);
    }

    /**
     * Get the exceptionID int.
     * @return Returns the int exceptionID.
     */
    public int getExceptionID() {
        return exceptionID;
    }

    /**
     * Set the exceptionID int.
     * @param exceptionID The exceptionID int to set (ERROR = 0; WARNING = 1).
     */
    public void setExceptionID(int exceptionID) {
        if ((exceptionID >= 0) && (exceptionID < 2)) {
            this.exceptionID = exceptionID;
        } else {
            this.exceptionID = ERROR;
        }
    }
}
