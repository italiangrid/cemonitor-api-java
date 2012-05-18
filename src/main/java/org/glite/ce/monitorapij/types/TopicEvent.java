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
 
package org.glite.ce.monitorapij.types;

import java.util.Calendar;
import java.util.Hashtable;

import org.glite.ce.monitorapij.sensor.Sensor;
import org.glite.ce.monitorapij.sensor.SensorException;
import org.glite.ce.monitorapij.sensor.SensorOutputDataFormat;

public interface TopicEvent {
    public void addParameter(String name, Object value);

    public void applyFormat(SensorOutputDataFormat dataformat) throws SensorException;

    public void applyFormat(String format) throws SensorException;

    public Calendar getExpirationTime();

    public int getID();

    public String[] getMessage();

    public String getMessage(int i);

    public Object getParameter(String name);

    public Hashtable<String, Object> getParameters();

    public String getProducer();

    public Sensor getSource();

    public Calendar getTimestamp();

    public boolean isExpired();

    public void setID(int ID);

    public void setMessage(int i, String _value);

    public void setMessage(String[] message);
   
    public void setParameters(Hashtable<String, Object> parameters);

    public void setProducer(String producer);
    
    public void setSource(Sensor source);

    public void setTimestamp(Calendar timestamp);
}
