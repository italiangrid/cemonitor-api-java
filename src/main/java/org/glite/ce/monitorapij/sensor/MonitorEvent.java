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

import java.io.Serializable;


/**
 * This class represents an event generated by the monitor.
 */
public class MonitorEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private int eventID;
    private long when;
    private String idStringValue;
    private String message;

    /**
     * Creates a new MonitorEvent object. The creation time is set to the current time.
     */
    public MonitorEvent() {
        when = System.currentTimeMillis();
    }

    /**
     * Creates a new MonitorEvent object with specified eventID and creation time.
     *
     * @param eventID This is an int identifying a type of message.
     * @param when The creation time expressed in milliseconds.
     */
    public MonitorEvent(int eventID, long when) {
        this.eventID = eventID;
        this.when = when;
    }

    /**
     * Get the eventID.
     *
     * @return eventID
     * The int number representing the type of event.
     */
    public int getID() {
        return eventID;
    }

    /**
     * Set eventID.
     *
     * @param id The eventID will be set to this int value
     */
    public void setID(int id) {
        this.eventID = id;
    }

    /**
     * Get the identificative String of the event.
     *
     *
     * @return idStringValue The identificative String of the event.
     */
    public String getIDStringValue() {
        return idStringValue;
    }

    /**
     * Set the identificative String of the event.
     *
     *
     * @param idStringValue The identificative String of the event.
     */
    public void setIDStringValue(String value) {
        idStringValue = value;
    }

    /**
     * Get the creation time of the MonitorEvent.
     *
     * @return The creation time of the MonitorEvent.
     */
    public long getWhen() {
        return when;
    }

    /**
     * Get the message of the MonitorEvent.
     *
     * @return A String representing the message of the MonitorEvent.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message of the MonitorEvent.
     *
     * @param msg  String representing the message of the MonitorEvent.
     */
    public void setMessage(String msg) {
        message = msg;
    }
}
