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

package org.glite.ce.monitorapij.resource;

import org.glite.ce.monitorapij.resource.types.Property;

import java.net.URI;
import java.util.Calendar;


/**
 * Instances of classes that implement this interface are used to store 
 * information about a resource like a sensor. 
 * This classes are intended as plugins for the CEMonitor passed as jar files.
 * It is also used for actions and subscriptions.
 */
public interface Resource {
    /**
     * Check if this resource has recently been added (i.e. inserted its jar in the jar directory).
     *
     * @return True if this resource is new, false otherwise.
     */
    public boolean isNew();

    /**
     * Set the "new" flag of this resource.
     *
     * @param b The value of the "new" flag.
     */
    public void setNew(boolean b);

    /**
     * Check if this resource still exists.
     *
     * @return True if the resource exists, false otherwise.
     */
    public boolean exists();

    /**
     * Get the Integer identifying this resource.
     *
     * @return The Integer identifying this resource.
     */
    public String getId();

    /**
     * Set the int identifying this resource.
     *
     * @param The int identifying this resource.
     */
    public void setId(String id);

    /**
     * Set the Integer identifying this resource.
     *
     * @param The Integer identifying this resource.
     */
  //  public void setId(Integer id);

    /**
     * Get the name of this resource.
     *
     * @return The name of this resource.
     */
    public String getName();

    /**
     * Set the name of this resource.
     *
     *
     * @param name The name of this resource.
     */
    public void setName(String name);

    /**
     * Get the type of this resource.
     *
     * @return The type of this resource.
     */
    public String getType();

    /**
     * Set the type of this resource.
     *
     *
     * @param name The type of this resource.
     */
    public void setType(String type);

    //public int getHashCode( );
    //public void setHashCode( int hashCode );
    
    /**
     * Get the creation time.
     * @return The creation time.
     */
    public Calendar getCreationTime();

    /**
     * Get the time of the last modification occurred to this resource.
     *
     * @return The time of the last modification to this resource.
     */
    public Calendar lastModified();

    //  public boolean isModified( );
    /**
     * Set the time of the last modification occurred to this resource.
     * @param time The time of the last modification to this resource.
     */
    public void setCreationTime(Calendar time);

    /**
     * Get the URI representing the path to the jar file of this resource.
     *
     * @return The path to the jar of this resource.
     */
    public URI getJarPath();

    /**
     * Set the URI representing the path to the jar file of this resource.
     *
     * @param JARPath The path to the jar of this resource.
     */
    public void setJarPath(URI JARPath);

    /**
     * Get a <code>Property</code> specifying its name
     *
     * @param key The name of the property.
     *
     * @return The searched <code>Property</code> if it exists.
     */
    public Property getProperty(String key);

    /**
     * Get a <code>Property</code> specifying its array index.
     *
     * @param i  The array index of the searched <code>Property</code>.
     *
     * @return The searched <code>Property</code>.
     */
    public Property getProperty(int i);

    /**
     * Get the array of the all <code>Property</code>s of this resource.
     *
     * @return The array of the all <code>Property</code>s of this resource.
     */
    public Property[] getProperty();

    /**
     * Set the array of the all <code>Property</code>s of this resource.
     *
     * @param The array of the all <code>Property</code>s of this resource.
     */
    public void setProperty(Property[] properties);

    /**
     * Set the <code>Property</code> array slot specified by i to the <code>Property</code> specified by value.  
     *
     * @param i The index of the <code>Property</code> array where to put the new <code>Property</code>.
     * @param value The new <code>Property</code>.
     */
    public void setProperty(int i, Property value);
}
