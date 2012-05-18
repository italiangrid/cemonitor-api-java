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

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.glite.ce.commonj.utils.BooleanLock;
import org.glite.ce.monitorapij.resource.Resource;
import org.glite.ce.monitorapij.resource.types.CEMonResource;
import org.glite.ce.monitorapij.resource.types.Property;

/**
 * Base abstract class which must be extended by all sensor plugins. It extends
 * <code>CEMonResource</code> and implements interfaces <code>Runnable</code>,
 * <code>Resource</code>, <code>Sensor</code> and <code>MonitorListener</code>.
 * It provides methods to control the sensor execution and to manage the
 * <code>SensorListener</code>s and supported formats.
 */
public abstract class AbstractSensor
    extends CEMonResource
    implements Runnable, Resource, Sensor, MonitorListener {
    private final static Logger logger = Logger.getLogger(AbstractSensor.class.getName());

    private static final Object objMutex = new Object();

    private ArrayList<SensorListener> listeners;

    private ArrayList<SensorOutputDataFormat> sensorOutputDataFormat;

    private SensorOutputDataFormat defaultFormat;

    private BooleanLock lock;

    private long executionDelay = 0;

    private Thread thisThread;

    private String scope = Sensor.HIGH;

    private boolean isEventOverwriteModeActive = false;

    private boolean purgeAllEventsOnStartup = false;

    private boolean isInitialized = false;

    private boolean isDestroySensor = false;

    /*
     * 
     * 
     * /** Create a new AbstractSensor object named "CE Sensor" and of type
     * "Sensor".
     */
    public AbstractSensor() {
        this("CE Sensor", "Sensor");
    }

    /**
     * Create a new <code>AbstractSensor</code> object.
     * 
     * @param name
     *            The name of <code>AbstractSensor</code>
     * @param type
     *            The type of <code>AbstractSensor</code>
     */
    public AbstractSensor(String name, String type) {
        super(name, type);

        lock = new BooleanLock();
        sensorOutputDataFormat = new ArrayList<SensorOutputDataFormat>(0);
        listeners = new ArrayList<SensorListener>(0);

        thisThread = new Thread(this);
        thisThread.setName(getName());
    }

    /**
     * Add a format to the ArrayList of supported formats. Do nothing if the
     * format has already been added.
     * 
     * @param format
     *            The <code>SensorOutputDataFormat</code> to add
     */
    public void addFormat(SensorOutputDataFormat format) {
        if ((format != null) && !isFormatSupported(format.getName())) {
            sensorOutputDataFormat.add(format);
        }
    }

    /**
     * Add a <code>SensorListener</code> to the sensor.
     * 
     * @param l
     *            The <code>SensorListener</code> to be added.
     */
    public void addSensorListener(SensorListener l) {
        if (l != null) {
            listeners.add(l);
        }
    }

    /**
     * 
     * @see org.glite.ce.monitorapij.sensor.Sensor#destroySensor()
     */
    public void destroySensor() {
        logger.debug("destroying sensor " + getName() + "...");
        isDestroySensor = true;
        lock.setValue(false);

        synchronized (objMutex) {
            objMutex.notify();
        }

        listeners.clear();
        logger.debug("destroying sensor " + getName() + "... done!");
    }

    /**
     * Specify the things to do when a <code>MonitorEvent</code> occurs.
     * 
     * @see org.glite.ce.monitorapij.sensor.MonitorListener#doOnMonitorEvent(org.glite.ce.monitorapij.sensor.MonitorEvent)
     */
    public void doOnMonitorEvent(MonitorEvent mEvt) {
    }

    /**
     * Notify all listeners that have registered interest for notification on
     * this event type. The event instance is created using the
     * <code>event</code> parameter.
     * 
     * @param event
     *            The <code>SensorEvent</code> object.
     */
    protected void fireSensorEvent(SensorEvent event) {
        if (event == null) {
            return;
        }

        // Process the listeners first to last, notifying
        // those that are interested in this event
        for (int i = 0; i < listeners.size(); i++) {
            Object listener = listeners.get(i);

            if (listener instanceof SensorListener) {
                ((SensorListener) listener).doOnSensorEvent(event);
            }
        }
    }

    /**
     * Get the default format if previously set or the first saved supported
     * format.
     * 
     * @return The SensorOutputDataFormat which is the default format
     */
    public SensorOutputDataFormat getDefaultFormat() {
        if ((defaultFormat == null) && (sensorOutputDataFormat.size() > 0)) {
            defaultFormat = (SensorOutputDataFormat) sensorOutputDataFormat.get(0);
        }

        return defaultFormat;
    }

    /**
     * Get the SensorOutputDataFormat named as specified if it is supported.
     * 
     * @param format
     *            The name of the searched format.
     * @return The searched <code> SensorOutputDataFormat </code> if this is
     *         supported, null otherwise.
     * @see org.glite.ce.monitorapij.sensor.Sensor#getFormat(java.lang.String)
     */
    public SensorOutputDataFormat getFormat(String format) {
        if (format == null) {
            return null;
        }

        for (int i = 0; i < sensorOutputDataFormat.size(); i++) {
            SensorOutputDataFormat supportedFormat = (SensorOutputDataFormat) sensorOutputDataFormat.get(i);

            if (supportedFormat.getName().equalsIgnoreCase(format)) {
                return supportedFormat;
            }
        }

        return null;
    }

    /**
     * Get all the supported formats.
     * 
     * @return An array of SensorOutputDataFormat representing the all supported
     *         formats.
     * @see org.glite.ce.monitorapij.sensor.Sensor#getFormats()
     */
    public SensorOutputDataFormat[] getFormats() {
        SensorOutputDataFormat[] formats = new SensorOutputDataFormat[sensorOutputDataFormat.size()];
        formats = (SensorOutputDataFormat[]) sensorOutputDataFormat.toArray(formats);

        return formats;
    }

    public String getScope() {
        return scope;
    }

    /**
     * Get an array of all the <code>SensorListener</code>s added to this
     * AbstractSensor with addSensorListener().
     * 
     * @return All of the <code>SensorListener</code>s added or an empty array
     *         if no listeners have been added.
     */
    public SensorListener[] getSensorListeners() {
        SensorListener[] sensorListener = new SensorListener[listeners.size()];

        return (SensorListener[]) listeners.toArray(sensorListener);
    }

    /**
     * Initialize the Sensor.
     * 
     * @throws SensorException
     *             thrown if something gone wrong during initialization.
     */
    public void init()
        throws SensorException {
        if (isInitialized != true) {
            logger.debug("init() - Initializing sensor [" + getName() + "]");

            Property[] propertyList = getProperty();
            if (propertyList != null) {
                for (int i = 0; i < propertyList.length; i++) {

                    logger.debug("init() - property name = " + propertyList[i].getName());

                }
            }

            Property property = getProperty("purgeAllEventsOnStartup");

            if (property != null) {
                if (property.getValue() != null) {
                    purgeAllEventsOnStartup = Boolean.parseBoolean((String) property.getValue());
                } else {
                    purgeAllEventsOnStartup = false;
                }

                logger.debug("init() - purgeAllEventsOnStartup is [" + purgeAllEventsOnStartup + "]");

            }
            isInitialized = true;
            isDestroySensor = false;
        }
    }

    public boolean isEventOverwriteModeActive() {
        return isEventOverwriteModeActive;
    }

    /**
     * Check if the specified <code>SensorOutputDataFormat</code> is supported.
     * 
     * @see isFormatSupported(java.lang.String)
     * @see org.glite.ce.monitorapij.sensor.Sensor#isFormatSupported(org.glite.ce.monitorapij.sensor.SensorOutputDataFormat)
     */
    public boolean isFormatSupported(SensorOutputDataFormat sensorOutputDataFormat) {
        if (sensorOutputDataFormat == null) {
            return false;
        }

        return isFormatSupported(sensorOutputDataFormat.getName());
    }

    /**
     * Check if the <code>SensorOutputDataFormat</code> named as specified is
     * supported.
     * 
     * @param format
     *            The name of the <code>SensorOutputDataFormat</code> to be
     *            checked.
     * @return A boolean representing the response.
     * @see org.glite.ce.monitorapij.sensor.Sensor#isFormatSupported(java.lang.String)
     */
    public boolean isFormatSupported(String format) {
        if (format == null) {
            return false;
        }

        for (int i = 0; i < sensorOutputDataFormat.size(); i++) {
            SensorOutputDataFormat supportedFormat = (SensorOutputDataFormat) sensorOutputDataFormat.get(i);

            if (supportedFormat.getName().equals(format)) {
                return true;
            }
        }

        return false;
    }

    public boolean isPurgeAllEventsOnStartup() {
        return purgeAllEventsOnStartup;
    }

    /**
     * Remove the specified format. If this is the default format, the first
     * saved format will be set as new default format.
     * 
     * @param format
     *            the <code>SensorOutputDataFormat</code> to be removed
     * @see org.glite.ce.monitorapij.sensor.Sensor#removeFormat(org.glite.ce.monitorapij.sensor.SensorOutputDataFormat)
     */
    public void removeFormat(SensorOutputDataFormat format) {
        if (format != null) {
            sensorOutputDataFormat.remove(format);

            if (getDefaultFormat().getName().equals(format.getName())) {
                getDefaultFormat();
            }
        }
    }

    /**
     * Remove a <code>SensorListener</code> from the sensor.
     * 
     * @param l
     *            The listener to be removed.
     */
    public void removeSensorListener(SensorListener l) {
        if (l != null) {
            listeners.remove(l);
        }
    }

    /**
     * Resumes the execution of the Sensor.
     * 
     * @see org.glite.ce.monitorapij.sensor.Sensor#resumeSensor()
     */
    public synchronized void resumeSensor() {
        lock.setValue(false);
    }

    /**
     * Launch the execute() method of Sensor thread each executionDelay time
     * interval.
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        while (!isDestroySensor) {
            try {
                lock.waitUntilFalse(0L);
            } catch (InterruptedException ex) {
                logger.error(ex.toString());
            }

            if (isDestroySensor) {
                logger.debug("destroying sensor " + getName() + " inside run method");
                return;
            }

            try {
                logger.debug("sensor " + getName() + ": invoking execute()");
                execute();
                logger.debug("sensor " + getName() + ": execute() invoked!");
            } catch (SensorException se) {
                logger.error(se.toString(), se);

                if (se.getExceptionID() == SensorException.ERROR) {
                    destroySensor();
                    logger.error("sensor " + getName() + " destroyed \"" + getName()
                            + "\" sensor because of previous errors");
                }
            } finally {
                synchronized (objMutex) {
                    if (executionDelay > 0 && !isDestroySensor) {
                        try {
                            objMutex.wait(executionDelay);
                        } catch (InterruptedException e) {
                            logger.error("InterruptedException: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * Set the default format.
     * 
     * @param format
     *            The SensorOutputDataFormat to be set as default. This must be
     *            not null and supported.
     * @see org.glite.ce.monitorapij.sensor.Sensor#setDefaultFormat(org.glite.ce.monitorapij.sensor.SensorOutputDataFormat)
     */
    public void setDefaultFormat(SensorOutputDataFormat format) {
        if (format != null) {
            defaultFormat = format;
            addFormat(format);
        }
    }

    public void setEventOverwriteModeActive(boolean i) {
        isEventOverwriteModeActive = i;
    }

    public void setPurgeAllEventsOnStartup(boolean purgeAllEventsOnStartup) {
        this.purgeAllEventsOnStartup = purgeAllEventsOnStartup;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Start the execution of the Sensor intended as a Thread if the
     * <code>Property</code> executionDelay has been defined. Otherwise log a
     * warning "executionDelay property not found!".
     * 
     * @see org.glite.ce.monitorapij.sensor.Sensor#startSensor()
     */
    public void startSensor() {
        Property property = getProperty("executionDelay");

        if (property != null) {
            String delay = getProperty("executionDelay").getValue();

            if (delay != null) {
                executionDelay = Long.parseLong(delay);
                executionDelay *= 1000;
            }

        } else {
            logger.warn("ExecutionDelay property not found, using default 60");
            executionDelay = 60000;
        }

        thisThread.start();

    }

    /**
     * Suspend the execution of the Sensor.
     * 
     * @see org.glite.ce.monitorapij.sensor.Sensor#suspendSensor()
     */
    public synchronized void suspendSensor() {
        lock.setValue(true);
    }
}
