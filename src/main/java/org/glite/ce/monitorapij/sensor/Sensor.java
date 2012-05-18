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

import org.glite.ce.monitorapij.resource.types.Property;

/**
 * This interface must be implemented by all monitor sensors.
 */
public interface Sensor {
    public static final String HIGH = "HIGH";    
    public static final String MEDIUM = "MEDIUM";
    public static final String LOW = "LOW";
    
    /**
     * Get the sensor's name.
     *
     * @return A String representing the sensor's name
     */
    public String getName();

    /**
     * Get the sensor's type.
     *
     * @return A String representing the sensor's type
     */
    public String getType();

    //public String getSensorType( );

    public String getScope();

    public void setScope(String i);

    public boolean isEventOverwriteModeActive();

    public void setEventOverwriteModeActive(boolean i);
    
    public Property[] getProperty();
    
    public void setProperty(Property[] property);
    
    /**
     * Get the SensorOutputDataFormat format specified by its name.
     * @param name The name of the SensorOutputDataFormat format.
     * @return The SensorOutputDataFormat named as required.
     */
    public SensorOutputDataFormat getFormat(String name);

    /**
     * Get an array of all supported format.
     *
     * @return An array of SensorOutputDataFormat representing the all supported formats.
     */
    public SensorOutputDataFormat[] getFormats();

    /**
     * Add a format to the list of Sensor's supported formats.
     *
     * @param format The SensorOutputDataFormat to be added.
     */
    public void addFormat(SensorOutputDataFormat format);

    /**
     * Remove a format from the list of Sensor's supported formats.
     *
     * @param format The SensorOutputDataFormat to be removed.
     */
    public void removeFormat(SensorOutputDataFormat format);

    /**
     * Set the default format.
     *
     * @param format Then SensorOutputDataFormat which is the format to be set as default.
     */
    public void setDefaultFormat(SensorOutputDataFormat format);

    /**
     * Get the default format.
     *
     * @return The SensorOutputDataFormat which represents the default format.
     */
    public SensorOutputDataFormat getDefaultFormat();

    /**
     * Check if specified format is supported by the Sensor i.e. has already been added.
     *
     * @param format The <code>SensorOutputDataFormat</code> to check.
     *
     * @return A boolean: true if the specified format is supported, false otherwise.
     */
    public boolean isFormatSupported(SensorOutputDataFormat format);

    /**
     * Check if the <code>SensorOutputDataFormat</code> specified by its name is supported by the Sensor, i.e. has already been added.
     *
     * @param format The String representing the name of the <code>SensorOutputDataFormat</code> to be checked.
     *
     * @return A boolean: true if the format specified is supported, false otherwise.
     */
    public boolean isFormatSupported(String format);

    /**
     * Add a <code>SensorListener</code> interested to notifications regarding <code>MonitorEvent</code>s
     * of this Sensor.
     *
     * @param sIf The <code>SensorListener</code> to add.
     */
    public void addSensorListener(SensorListener sIf);

    /**
     * Remove a <code>SensorListener</code> from the list of listeners interested to notifications regarding <code>MonitorEvent</code>s
     * of this Sensor.
     *
     * @param sIf The <code>SensorListener</code> to be removed.
     */
    public void removeSensorListener(SensorListener sIf);

    /**
     * Get the all <code>SensorListener</code> s registered to this Sensor.
     *
     * @return An array of the all <code>SensorListener</code> s registered to this Sensor.
     */
    public SensorListener[] getSensorListeners();

    /**
     * Initialize this Sensor.
     *
     * @throws SensorException
     */
    public void init() throws SensorException;

    /**
     * Start the execution of this Sensor.
     */
    public void startSensor();

    /**
     * Suspend the execution of this Sensor.
     */
    public void suspendSensor();

    /**
     * Resume the execution of this Sensor.
     */
    public void resumeSensor();

    /**
     * Destroy this Sensor.
     */
    public void destroySensor();
    
    

    /**
     * Execute this Sensor.
     *
     * @throws SensorException
     */
    public void execute() throws SensorException;

	public boolean isPurgeAllEventsOnStartup();
	
	public void setPurgeAllEventsOnStartup( boolean purge );
	
}
