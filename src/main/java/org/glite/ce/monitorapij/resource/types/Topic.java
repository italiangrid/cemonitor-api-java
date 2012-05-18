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

package org.glite.ce.monitorapij.resource.types;

import java.io.Serializable;

/**
 * This class identifies a class of sensor and specifies the
 * <code>_Dialect</code>s it supports.
 */
public class Topic
    implements Serializable {
    public static final String ALL = "ALL";

    public static final String GROUP = "GROUP";

    public static final String USER = "USER";

    private static final long serialVersionUID = 1319031832;

    private boolean isEventOverwriteModeActive = false;

    private boolean purgeAllEventsOnStartup = false;

    private String name;

    private String visibility;

    private Dialect[] dialect;

    /**
     * Creates a new _Topic object without name.
     */
    public Topic() {
        this("");
    }

    /**
     * Creates a new _Topic object specifying its name.
     * 
     * @param topicName
     *            The name of this topic.
     */
    public Topic(String topicName) {
        name = topicName;
    }

    public Topic(String topicName, Dialect[] dialect) {
        this.name = topicName;
        this.dialect = dialect;
    }

    public Topic(org.glite.ce.monitorapij.types.Topic topic) {
        this.name = topic.getName();
        this.visibility = topic.getVisibility();

        org.glite.ce.monitorapij.types.Dialect[] allDials = topic.getDialect();
        this.dialect = new Dialect[allDials.length];
        for (int k = 0; k < allDials.length; k++) {
            this.dialect[k] = new Dialect(allDials[k]);
        }

    }

    public boolean checkSupportedDialect(Dialect dlct) {
        if (dlct == null) {
            return false;
        }

        return checkSupportedDialect(dlct.getName());
    }

    /**
     * Check if the specified <code>_Dialect</code> is supported by this topic.
     * 
     * @param dlct
     *            The <code>_Dialect</code> which is to be verified.
     * 
     * @return True if the specified <code>Dialect</code> is supported. False
     *         otherwise.
     */

    public boolean checkSupportedDialect(String dialectName) {
        if (dialectName == null) {
            return false;
        }

        Dialect[] dialect = getDialect();

        for (int i = 0; i < dialect.length; i++) {
            if (dialect[i].getName().equalsIgnoreCase(dialectName)) {
                return true;
            }
        }

        return false;
    }

    public Dialect[] getDialect() {
        return dialect;
    }

    /**
     * Get the <code>_Dialect</code> specified by its name if it is supported by
     * the topic.
     * 
     * @param dialectName
     *            The name of the searched dialect.
     * 
     * @return The searched <code>Dialect</code> if it is supported by the
     *         topic, null otherwise.
     */
    public Dialect getDialect(String dialectName) {
        if (dialectName == null) {
            return null;
        }

        Dialect[] dialect = getDialect();

        for (int i = 0; i < dialect.length; i++) {
            if (dialect[i].getName().equalsIgnoreCase(dialectName)) {
                return dialect[i];
            }
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public String getVisibility() {
        return visibility;
    }

    public boolean isEventOverwriteModeActive() {
        return isEventOverwriteModeActive;
    }

    public boolean isPurgeAllEventsOnStartup() {
        return purgeAllEventsOnStartup;
    }

    public void setDialect(Dialect[] dialect) {
        this.dialect = dialect;
    }

    public void setEventOverwriteModeActive(boolean i) {
        isEventOverwriteModeActive = i;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPurgeAllEventsOnStartup(boolean purgeAllEventsOnStartup) {
        this.purgeAllEventsOnStartup = purgeAllEventsOnStartup;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
