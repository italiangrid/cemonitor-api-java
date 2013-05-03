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

import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.glite.ce.commonj.utils.TimerTask;
import org.glite.ce.monitorapij.resource.types.CEMonResource;

/**
 * A class used to handle a list of <code>Resource</code>s placed in a specified
 * directory as plugins. An XML config file is used to store and retrieve
 * information about these resources. The task of checking if resources are
 * added or removed or modified can be scheduled for repeated executions.
 */
public abstract class ResourceHolder
    extends TimerTask {
    private final static Logger logger = Logger.getLogger(ResourceHolder.class.getName());

    private ArrayList<ResourceListener> listeners;

    protected Hashtable<String, Resource> resourceList;

    protected boolean exit = false;

    /**
     * Creates a new ResourceHolder object specifying the current
     * <code>MessageContext</code>, the resource path and the config file path.
     * 
     * @param context
     *            The current <code>MessageContext</code>.
     * @param resourcePath
     *            The path where new resources are placed.
     * @param configFilePath
     *            The path of the config file.
     */
    public ResourceHolder() {
        resourceList = new Hashtable<String, Resource>(0);
        listeners = new ArrayList<ResourceListener>(0);

    }

    /**
     * Add a <code>Resource</code> to the list of handled resources.
     * 
     * @param resource
     *            The <code>Resource</code> to add. This must have a defined ID.
     */
    public void addResource(Resource resource) {
        if (resource != null) {
            if (getResource(resource.getId()) == null) {
                resourceList.put(resource.getId(), resource);
            }
        }
    }

    /**
     * Remove the specified <code>Resource</code>.
     * 
     * @param resource
     *            The resource to be removed.
     */
    public void removeResource(Resource resource) {
        if (resource != null) {
            if (getResource(resource.getId()) != null) {
                resourceList.remove(resource.getId());
            }
        }
    }

    /**
     * Get the <code>Resource</code> specified by its ID.
     * 
     * @param id
     *            The ID int of the <code>Resource</code> searched.
     * @return The Resource searched if present in the list of handled
     *         resources, null otherwise.
     */
    public Resource getResource(String id) {
        Resource resource = null;

        if (id != null) {
            resource = (Resource) resourceList.get(id);
        }

        return resource;
    }

    public int getResourceListSize() {
        return resourceList.size();
    }

    /**
     * Get all the handled resources.
     * 
     * @return An array containing all the handled resources.
     */
    public Resource[] getResources() {
        Resource[] resources = new Resource[resourceList.values().size()];
        resources = (Resource[]) resourceList.values().toArray(resources);
        return resources;
    }

    public List<Resource> getResourceList() {
        return new ArrayList<Resource>(resourceList.values());
    }

    /**
     * Get the all <code>Resource</code> of the specified type.
     * 
     * @param id
     *            The type of the <code>Resource</code>s searched.
     * @return An Array containing the Resources searched if present in the list
     *         of handled resources, an empty array otherwise.
     */
    public Resource[] getResourceByType(String type) {
        ArrayList<Resource> tmp = new ArrayList<Resource>(0);
        Resource[] resourceArray = getResources();

        for (int i = 0; i < resourceArray.length; i++) {
            if (resourceArray[i].getType().equals(type)) {
                tmp.add(resourceArray[i]);
            }
        }

        return makeResources(tmp);
    }

    /**
     * Get the <code>CEMonResource</code>s with the specified jar path.
     * 
     * @param path
     *            The path of the jar of the resource.
     * @return An array containing the searched resources if present in the list
     *         of handled resources.
     */
    public Resource[] getResourceByJARPath(URI path) {
        ArrayList<Resource> tmp = new ArrayList<Resource>(0);
        Resource[] resourceArray = getResources();

        for (int i = 0; i < resourceArray.length; i++) {
            if (resourceArray[i].getJarPath().equals(path)) {
                tmp.add(resourceArray[i]);
            }
        }

        return makeResources(tmp);
    }

    /**
     * Get the <code>CEMonResource</code>s with the specified name.
     * 
     * @param name
     *            The name of the searched resource.
     * @return An array containing the searched resources if present in the list
     *         of handled resources.
     */
    public Resource[] getResourceByName(String name) {
        ArrayList<Resource> tmp = new ArrayList<Resource>(0);
        Resource[] resourceArray = getResources();

        for (int i = 0; i < resourceArray.length; i++) {
            if (resourceArray[i].getName().equals(name)) {
                tmp.add(resourceArray[i]);
            }
        }

        return makeResources(tmp);
    }

    /**
     * Convert the specified ArrayList in an array of Resources.
     * 
     * @param list
     *            The specified ArrayList
     * @return The Array of Resources containing all the handled Resources.
     */
    private Resource[] makeResources(ArrayList<Resource> list) {
        Resource[] resources = new Resource[list.size()];
        resources = (Resource[]) list.toArray(resources);

        return resources;
    }

    /**
     * Add an array of <code>Resource</code>s to the list of handled resources.
     * 
     * @param resources
     *            An array containing the resources to add.
     */
    public void setResources(Resource[] resources) {
        clear();

        if (resources == null) {
            return;
        }

        for (int i = 0; i < resources.length; i++) {
            addResource(resources[i]);
        }
    }

    /**
     * Clear the list of handled resources.
     */
    public void clear() {
        resourceList.clear();
    }

    protected synchronized void fireEvent(int eventID, ArrayList<Resource> resourceList) {
        logger.debug("ResourceHolder  fireEvent " + resourceList.size() + " eventId=" + eventID);

        if ((resourceList != null) && (resourceList.size() > 0)) {
            ResourceEvent event = new ResourceEvent(eventID, resourceList, this);

            // Process the listeners first to last, notifying
            // those that are interested in this event
            for (int i = 0; i < listeners.size(); i++) {
                Object listener = listeners.get(i);

                if (listener instanceof ResourceListener) {
                    ((ResourceListener) listener).doOnResourceEvent(event);
                }
            }
        }
    }

    /**
     * Returns an array of all the <code>SensorListener</code> s added to this
     * holder with addSensorListener().
     * 
     * @return all of the <code>SensorListener</code> s added or an empty array
     *         if no listeners have been added
     */
    public ResourceListener[] getSensorListeners() {
        ResourceListener[] resourceListener = new ResourceListener[listeners.size()];

        return (ResourceListener[]) listeners.toArray(resourceListener);
    }

    /**
     * Adds a <code>ResourceListener</code> to the holder.
     * 
     * @param l
     *            the <code>ResourceListener</code> to be added
     */
    public void addResourceListener(ResourceListener l) {
        if (l != null) {
            listeners.add(l);
        }
    }

    /**
     * Removes a <code>ResourceListener</code> from the holder.
     * 
     * @param l
     *            the listener to be removed
     */
    public void removeResourceListener(ResourceListener l) {
        if (l != null) {
            listeners.remove(l);
        }
    }

    protected ArrayList<Resource> listDiff(ArrayList<Resource> l1, ArrayList<Resource> l2) {
        ArrayList<Resource> list1 = (l1.size() > l2.size()) ? l1 : l2;
        ArrayList<Resource> list2 = (l1.size() > l2.size()) ? l2 : l1;

        ArrayList<Resource> result = new ArrayList<Resource>(list1.size() - list2.size());

        for (int k = 0; k < list1.size(); k++) {
            CEMonResource resource = (CEMonResource) list1.get(k);
            int idx = -1;
            for (int r = 0; r < list2.size(); r++) {
                CEMonResource tmpr = (CEMonResource) list2.get(r);
                if (resource.getName().equals(tmpr.getName())) {
                    idx = r;
                    break;
                }
            }

            if (idx < 0)
                result.add(resource);
        }

        return result;
    }

    public boolean cancel() {
        exit = true;
        listeners.clear();

        return super.cancel();
    }

    public void destroy() {
        clear();
        cancel();
    }

    public abstract String getCategory();
}
