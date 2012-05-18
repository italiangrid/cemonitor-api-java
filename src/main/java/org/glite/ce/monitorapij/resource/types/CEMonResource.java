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
 * Authors: Paolo Andreetto, <paolo.andreetto@pd.infn.it>
 *
 */

package org.glite.ce.monitorapij.resource.types;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URI;
import java.util.Calendar;

import org.glite.ce.commonj.configuration.CEConfigResource;
import org.glite.ce.monitorapij.resource.Resource;

public class CEMonResource
    implements Externalizable, Resource, CEConfigResource {
    protected transient boolean newResource;

    protected transient Calendar creationTime;

    protected String id;

    protected String name;

    protected String type;

    protected URI jarPath;

    protected Property[] property;

    public CEMonResource() {
    }

    public CEMonResource(String name, String type) {
        super();

        this.name = name;
        this.type = type;
    }

    public CEMonResource(org.glite.ce.monitorapij.types.CEMonResource resource) {

        this.name = resource.getName();
        this.id = resource.getId();
        this.type = resource.getType();

        try {
            this.jarPath = new URI(resource.getJarPath().toString());
        } catch (Throwable th) {
            this.jarPath = null;
        }

        org.glite.ce.monitorapij.types.Property[] oldProps = resource.getProperty();
        this.property = new Property[oldProps.length];
        for (int k = 0; k < oldProps.length; k++) {
            this.property[k] = new Property(oldProps[k]);
        }

    }

    /**
     * Return the (almost) complete resource description with all relevant
     * fields that can be used for logfile-based debugging
     * 
     * @return The resource description
     */
    public String getCompleteInfo() {
        StringBuffer info = new StringBuffer("id=");
        info.append(id);
        info.append("] - name=[");
        info.append(name);
        info.append("] - type=[");
        info.append(type);
        info.append("] - CreationTime=[");
        info.append(getCreationTime().getTime().toString());
        info.append("]");

        return info.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public URI getJarPath() {
        return jarPath;
    }

    public org.apache.axis2.databinding.types.URI cloneURI() {
        org.apache.axis2.databinding.types.URI result = new org.apache.axis2.databinding.types.URI();
        try {
            result.setScheme(this.jarPath.getScheme());
            result.setHost(this.jarPath.getHost());
            result.setPort(this.jarPath.getPort());
            result.setPath(this.jarPath.getPath());
        } catch (Throwable th) {
        }

        return result;
    }

    public void setJarPath(URI jarPath) {
        this.jarPath = jarPath;
    }

    public Property[] getProperty() {
        return property;
    }

    public org.glite.ce.monitorapij.types.Property[] cloneProperties() {
        org.glite.ce.monitorapij.types.Property[] result = new org.glite.ce.monitorapij.types.Property[property.length];
        for (int k = 0; k < property.length; k++) {
            result[k] = new org.glite.ce.monitorapij.types.Property();
            result[k].setName(property[k].getName());
            result[k].setValue(property[k].getValue());
        }
        return result;
    }

    public Property getProperty(int i) {
        if (i >= 0 && i < property.length) {
            return property[i];
        }

        return null;
    }

    public void setProperty(Property[] property) {
        this.property = property;
    }

    public void setProperty(int i, Property p) {
        if (i >= 0 && i < property.length) {
            property[i] = p;
        }
    }

    public boolean isNew() {
        return newResource;
    }

    public void setNew(boolean b) {
        newResource = b;
    }

    public Calendar getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Calendar time) {
        creationTime = time;
    }

    public Property getProperty(String name) {
        Property[] localProps = getProperty();
        if (localProps == null) {
            return null;
        }

        for (int i = 0; i < localProps.length; i++) {
            if (localProps[i].getName().equalsIgnoreCase(name)) {
                return localProps[i];
            }
        }

        return null;
    }

    public boolean exists() {
        try {
            File file = new File(getJarPath().getPath());
            return file.exists();
        } catch (Exception ex) {
        }

        return false;
    }

    public Calendar lastModified() {
        Calendar time = null;
        try {
            File file = new File(getJarPath().getPath());
            time = Calendar.getInstance();
            time.setTimeInMillis(file.lastModified());
        } catch (Exception ex) {
        }

        return time;
    }

    public Object clone() {
        CEMonResource result = new CEMonResource();
        result.setId(this.getId());
        result.setName(this.getName());
        result.setType(this.getType());
        result.setJarPath(this.getJarPath());

        Property[] oldProps = this.getProperty();
        if (oldProps == null) {
            result.setProperty(null);
        } else {
            Property[] newProps = new Property[oldProps.length];
            for (int k = 0; k < oldProps.length; k++) {
                newProps[k] = new Property(oldProps[k].getName(), oldProps[k].getValue());
            }
            result.setProperty(newProps);
        }

        result.setNew(false);
        result.setCreationTime(Calendar.getInstance());
        return result;
    }

    public void writeExternal(ObjectOutput out)
        throws IOException {
        out.writeBoolean(newResource);
        writeCalendar(out, creationTime);
        writeString(out, id);
        writeString(out, name);
        writeString(out, type);
        writeString(out, jarPath != null ? jarPath.toString() : null);

        if (property != null) {
            out.writeInt(property.length);

            for (int i = 0; i < property.length; i++) {
                writeString(out, property[i].getName());
                writeString(out, property[i].getValue());
            }
        } else {
            out.writeInt(-1);
        }
    }

    public void readExternal(ObjectInput in)
        throws IOException, ClassNotFoundException {
        newResource = in.readBoolean();
        setCreationTime(readCalendar(in));
        setId(readString(in));
        setName(readString(in));
        setType(readString(in));

        try {
            String uri = readString(in);
            if (uri != null) {
                setJarPath(new URI(uri));
            }
        } catch (java.net.URISyntaxException ex) {
        }

        int size = in.readInt();
        if (size >= 0) {
            Property[] property = new Property[size];
            for (int k = 0; k < size; k++) {
                property[k] = new Property(readString(in), readString(in));
            }
            setProperty(property);
        }
    }

    private void writeString(ObjectOutput out, String s)
        throws IOException {
        if (s != null)
            out.writeUTF(s);
        else
            out.writeUTF("_null_");
    }

    private String readString(ObjectInput in)
        throws IOException {
        String s = in.readUTF();

        return s.equals("_null_") ? null : s;
    }

    private void writeCalendar(ObjectOutput out, Calendar cal)
        throws IOException {
        if (cal != null)
            out.writeLong(cal.getTimeInMillis());
        else
            out.writeLong(0);
    }

    private Calendar readCalendar(ObjectInput in)
        throws IOException {
        long ts = in.readLong();
        if (ts == 0)
            return null;
        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(ts);
        return result;
    }

}
