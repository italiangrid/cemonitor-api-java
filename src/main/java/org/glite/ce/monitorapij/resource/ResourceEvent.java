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

import java.util.List;

/**
 * This class represents an event regarding a particular set of resources. This
 * event informs the CEMonitor that a set of resources has been added, removed
 * or updated.
 */
public class ResourceEvent {
    public static final int RESOURCE_ADDED = 0;
    public static final int RESOURCE_REMOVED = 1;
    public static final int RESOURCE_UPDATED = 2;
    private ResourceHolder holder;
    private List<Resource> resources;
    private int eventId;

    /**
     * Creates a new ResourceEvent object.
     * 
     * @param holder
     *            The <code>ResourceHolder</code> holding the resources
     * @param eventID
     *            The int representing the event type identifier.
     * @param resources
     *            An array of the <code>Resource</code>s which produced this
     *            event.
     */
    public ResourceEvent(int eventId, List<Resource> resources, ResourceHolder holder) {
        this.holder = holder;
        this.eventId = eventId;
        this.resources = resources;
    }

    /**
     * Get the identifier of the type of this event.
     * 
     * @return An int identifying the event type ( RESOURCE_ADDED =
     *         0;RESOURCE_REMOVED = 1;RESOURCE_UPDATED = 2).
     */
    public int getEvent() {
        return eventId;
    }

    /**
     * Get the <code>ResourceHolder</code> holding the Resources which produced
     * this event.
     * 
     * @return The <code>ResourceHolder</code> of this event.
     */
    public ResourceHolder getResourceHolder() {
        return holder;
    }

    /**
     * Get an array of the <code>Resource</code>s which produced this event.
     * 
     * @return The array of the <code>Resource</code>s which produced this
     *         event.
     */
    public List<Resource> getResources() {
        return resources;
    }
}
