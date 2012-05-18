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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.net.URI;
import java.util.Calendar;

import org.glite.ce.monitorapij.types.CEResource;
import org.glite.ce.monitorapij.resource.Resource;
import org.glite.ce.monitorapij.types.Subscription;

/**
 * This class is used to create a subscription which differs from a normal
 * subscription for its persistence.<br>
 * This persistence must be intended as the capability of remaining in the
 * CEMonitor registries and being resumed if the framework goes down.<br>
 * It extends the normal <code>Subscription</code>.
 */
public class SubscriptionPersistent
    extends CEMonResource
    implements Externalizable, CEResource, Resource, Serializable {

    public static final long serialVersionUID = 1319032441;

    private Calendar pauseTime;

    private Boolean pauseNotify;

    private int maxRetryCount = 20;

    private int retryCount = maxRetryCount;

    private String subscriberId;

    private String subscriberGroup;

    private String credentialFilename;

    private String passphrase = "";

    private Boolean removeOnStartup = new Boolean(false);

    private String sslProtocol = "SSLv3";

    private URI monitorConsumerURL;

    private Calendar expirationTime;

    private Topic topic;

    private Policy policy;

    /**
     * Creates a new SubscriptionPersistent object. Its name is set to the value
     * of subscriptionID which is now generated.
     */
    public SubscriptionPersistent() {
        super();

        setCreationTime(Calendar.getInstance());
        pauseNotify = new Boolean(false);
        setId(generateSubscriptionID());
        setName(getId());
    }

    public SubscriptionPersistent(Subscription subscription) throws IllegalArgumentException, Exception {
        
        this();

        if (subscription == null) {
            throw (new IllegalArgumentException("the subscription is null"));
        }

        this.setExpirationTime(subscription.getExpirationTime());
        this.setType(subscription.getType());
        this.setMonitorConsumerURL(new URI(subscription.getMonitorConsumerURL().toString()));

        if (subscription.getJarPath() != null) {
            this.setJarPath(new URI(subscription.getJarPath().toString()));
        }

        org.glite.ce.monitorapij.types.Property[] property = subscription.getProperty();
        if (property != null) {
            Property[] newProperty = new Property[property.length];

            for (int i = 0; i < property.length; i++) {
                newProperty[i] = new Property(property[i].getName(), property[i].getValue());
            }
            this.setProperty(newProperty);
        }

        org.glite.ce.monitorapij.types.Topic topic = subscription.getTopic();
        org.glite.ce.monitorapij.types.Dialect[] dialect = topic.getDialect();

        Topic newtopic = new Topic(topic.getName());
        if (dialect != null) {
            Dialect[] newDialect = new Dialect[dialect.length];

            for (int i = 0; i < dialect.length; i++) {
                newDialect[i] = new Dialect(dialect[i].getName(), dialect[i].getQueryLanguage());
            }
            newtopic.setDialect(newDialect);
        }

        this.setTopic(newtopic);
        org.glite.ce.monitorapij.types.Policy policy = subscription.getPolicy();

        if (policy != null) {
            Policy newPolicy = new Policy();
            newPolicy.setRate(policy.getRate());

            org.glite.ce.monitorapij.types.Query query = policy.getQuery();
            if (query != null) {
                newPolicy.setQuery(new Query(query.getExpression(), query.getQueryLanguage()));
            }

            org.glite.ce.monitorapij.types.Action[] action = policy.getAction();
            if (action != null) {
                Action[] newAction = new Action[action.length];

                for (int i = 0; i < action.length; i++) {
                    newAction[i] = new Action(action[i].getName());
                    newAction[i].setDoActionWhenQueryIs(action[i].getDoActionWhenQueryIs());

                    org.glite.ce.monitorapij.types.Parameter[] parameter = action[i].getParameter();
                    if (parameter != null) {
                        Parameter[] newParameter = new Parameter[parameter.length];

                        for (int j = 0; j < parameter.length; j++) {
                            newParameter[j] = new Parameter(parameter[j].getName(), parameter[j].getValue());
                        }
                        newAction[i].setParameter(newParameter);
                    }
                }

                newPolicy.setAction(newAction);
            }
            this.setPolicy(newPolicy);
        }
    }

    public Object clone() {
        SubscriptionPersistent subscription = new SubscriptionPersistent();
        subscription.setRemoveOnStartup(removeOnStartup());
        subscription.setId(getId());
        subscription.setSubscriberId(getSubscriberId());
        subscription.setSubscriberGroup(getSubscriberGroup());
        subscription.setMonitorConsumerURL(getMonitorConsumerURL());
        subscription.setTopic(getTopic());
        subscription.setPolicy(getPolicy());
        subscription.setExpirationTime(getExpirationTime());
        subscription.setCreationTime(getCreationTime());
        subscription.setName(getName());
        subscription.setMaxRetryCount(getMaxRetryCount());
        subscription.setRetryCount(getRetryCount());
        subscription.setProperty(getProperty());
        subscription.setJarPath(getJarPath());
        subscription.setType(getType());
        subscription.setCredentialFile(getCredentialFile());
        subscription.setPassphrase(getPassphrase());
        subscription.setSSLProtocol(getSSLProtocol());

        if (!pauseNotify.booleanValue()) {
            subscription.resume();
        } else {
            subscription.pause(getPauseTime());
        }

        return subscription;
    }

    /**
     * Decrement the number of retries by one.
     * 
     * @return The decremented number of retries.
     */
    public int decrRetryCount() {
        if (maxRetryCount != -1 && retryCount > 0) {
            retryCount -= 1;
        }

        return retryCount;
    }

    /**
     * Return the (almost) complete subscription description with all relevant
     * fields that can be used for logfile-based debugging
     * 
     * @return The subscription description
     */
    public String getCompleteInfo() {
        StringBuffer info = new StringBuffer(super.getCompleteInfo());
        info.append(" - SubscriberID=[");
        info.append(getSubscriberId());
        info.append("] - TopicName=[");
        info.append(getTopic().getName());
        info.append("] - ConsumerURL=[");
        info.append(getMonitorConsumerURL().toString());
        info.append("] - IsExpired=[");
        info.append(isExpired());
        info.append("] - IsPaused=[");
        info.append(isPaused());
        info.append("]");

        return info.toString();
    }

    /**
     * Generate the subscriptionID String.<br>
     * This is actually generated from the string "uuid- " and the creation time
     * expressed in milliseconds.
     * 
     * @return The generates subscriptionID String.
     */
    private String generateSubscriptionID() {
        return "uuid-" + getCreationTime().getTimeInMillis();
    }

    public String getCredentialFile() {
        return credentialFilename;
    }

    public String getCredentialFilename() {
        return credentialFilename;
    }

    public Calendar getExpirationTime() {
        return expirationTime;
    }

    /**
     * Get the number of retries.
     * 
     * @return Returns the number of retries. This number is hardcoded to 20.
     */
    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public URI getMonitorConsumerURL() {
        return monitorConsumerURL;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public Boolean getPauseNotify() {
        return pauseNotify;
    }

    /**
     * Get the pause time which is the limit time before which the subscription
     * will be in "paused" status.
     * 
     * @return Returns the pauseTime.
     */
    public Calendar getPauseTime() {
        return pauseTime;
    }

    public Policy getPolicy() {
        return policy;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public String getSslProtocol() {
        return sslProtocol;
    }

    public String getSSLProtocol() {
        return sslProtocol;
    }

    public String getSubscriberGroup() {
        return subscriberGroup;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public Topic getTopic() {
        return topic;
    }

    /**
     * Check if this subscription is expired. This is done by controlling the
     * value of expiration time.
     * 
     * @return True if this subscription is expired. False otherwise.
     */
    public boolean isExpired() {
        Calendar currentTime = Calendar.getInstance();
        Calendar terminationTime = getExpirationTime();

        if (terminationTime == null) {
            return false;
        } else if (currentTime.before(terminationTime)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if this subscription is in the "paused" status.
     * 
     * 
     * @return True if the subscription is paused, false if it is not paused of
     *         if the pauseTime is expired.
     */
    public boolean isPaused() {
        if (!pauseNotify.booleanValue()) {
            return false;
        }

        if (pauseTime == null) {
            return true;
        }

        Calendar currentTime = Calendar.getInstance();

        if (currentTime.before(pauseTime)) {
            return true;
        } else {
            resume();

            return false;
        }
    }

    /**
     * Pause the subscription, which means setting to true the pauseNotify flag.
     */
    public void pause() {
        pauseNotify = new Boolean(true);
    }

    /**
     * Pause the subscription, specifying a pause time. This means setting to
     * true the pauseNotify flag and setting the pause time to the specified
     * value.<br>
     * When passed this pause time, the subscription can be considered not in
     * pause.
     * 
     * @param pauseTime
     *            The time to wait before considering resumed a subscription.
     */
    public void pause(Calendar pauseTime) {
        this.pauseTime = pauseTime;
        pauseNotify = new Boolean(true);
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

    public void readExternal(ObjectInput in)
        throws IOException, ClassNotFoundException {
        super.readExternal(in);

        pauseTime = readCalendar(in);
        setExpirationTime(readCalendar(in));
        setSubscriberId(readString(in));
        setSubscriberGroup(readString(in));
        setCredentialFilename(readString(in));
        setPassphrase(readString(in));
        setSslProtocol(readString(in));
        setMaxRetryCount(in.readInt());
        setRetryCount(in.readInt());
        pauseNotify = new Boolean(in.readBoolean());
        setRemoveOnStartup(in.readBoolean());

        try {
            String uri = readString(in);
            if (uri != null) {
                setMonitorConsumerURL(new URI(uri));
            }
        } catch (java.net.URISyntaxException ex) {
        }

        String name = readString(in);

        if (!name.equals("topic_null")) {
            Topic topic = new Topic(name);
            topic.setVisibility(readString(in));

            int size = in.readInt();
            if (size >= 0) {
                Dialect[] dialect = new Dialect[size];
                for (int k = 0; k < size; k++) {
                    dialect[k] = new Dialect(readString(in), readStringArray(in));
                }
                topic.setDialect(dialect);
            }

            setTopic(topic);
        }

        int rate = in.readInt();

        if (rate > -1) {
            Policy policy = new Policy();
            policy.setRate(rate);

            name = in.readUTF();
            if (!name.equals("query_null")) {
                policy.setQuery(new Query(name, readString(in)));
            }

            int size = in.readInt();
            if (size >= 0) {
                Action[] action = new Action[size];

                for (int k = 0; k < size; k++) {
                    action[k] = new Action(readString(in));
                    action[k].setDoActionWhenQueryIs(in.readBoolean());

                    int paramSize = in.readInt();
                    if (paramSize >= 0) {
                        Parameter[] param = new Parameter[paramSize];

                        for (int y = 0; y < paramSize; y++) {
                            param[y] = new Parameter(readString(in), in.readObject());
                        }

                        action[k].setParameter(param);
                    }
                }

                policy.setAction(action);
            }
            setPolicy(policy);
        }
    }

    private String readString(ObjectInput in)
        throws IOException {
        String s = in.readUTF();

        return s.equals("_null_") ? null : s;
    }

    private String[] readStringArray(ObjectInput in)
        throws IOException {
        int size = in.readInt();
        if (size >= 0) {
            String[] result = new String[size];
            for (int k = 0; k < size; k++) {
                result[k] = in.readUTF();
            }
            return result;
        }

        return null;
    }

    public boolean removeOnStartup() {
        return removeOnStartup.booleanValue();
    }

    public void resetRetryCount() {
        retryCount = maxRetryCount;
    }

    /**
     * Resume the subscription, which means setting to false the pauseNotify
     * flag.
     */
    public void resume() {
        pauseNotify = new Boolean(false);
        pauseTime = null;
    }

    public void setCredentialFile(String filename) {
        credentialFilename = filename;
    }

    public void setCredentialFilename(String credentialFilename) {
        this.credentialFilename = credentialFilename;
    }

    public void setExpirationTime(Calendar expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * Set the number of retries.
     * 
     * @param retryCount
     *            The retryCount value to set.
     */

    public void setMaxRetryCount(int max) {
        this.maxRetryCount = max;
        this.retryCount = max;
    }

    public void setMonitorConsumerURL(URI consumerURL) {
        monitorConsumerURL = consumerURL;
    }

    public void setPassphrase(String pass) {
        passphrase = pass;
    }

    public void setPauseNotify(Boolean pauseNotify) {
        this.pauseNotify = pauseNotify;
    }

    public void setPauseTime(Calendar pauseTime) {
        this.pauseTime = pauseTime;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public void setRemoveOnStartup(boolean b) {
        removeOnStartup = new Boolean(b);
    }

    public void setRetryCount(int retryCount)
        throws IllegalArgumentException {
        if (retryCount > maxRetryCount) {
            resetRetryCount();
            throw new IllegalArgumentException("the specified retryCount value (" + retryCount
                    + ") exceeds the maxRetryCount (" + maxRetryCount + "): going to use the default");
        }

        this.retryCount = retryCount;
    }

    public void setSslProtocol(String sslProtocol) {
        this.sslProtocol = sslProtocol;
    }

    public void setSSLProtocol(String protocol) {
        sslProtocol = protocol;
    }

    public void setSubscriberGroup(String subscriberGroup) {
        this.subscriberGroup = subscriberGroup;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    private void writeCalendar(ObjectOutput out, Calendar cal)
        throws IOException {
        if (cal != null)
            out.writeLong(cal.getTimeInMillis());
        else
            out.writeLong(0);
    }

    public void writeExternal(ObjectOutput out)
        throws IOException {
        super.writeExternal(out);

        writeCalendar(out, pauseTime);
        writeCalendar(out, expirationTime);
        writeString(out, subscriberId);
        writeString(out, subscriberGroup);
        writeString(out, credentialFilename);
        writeString(out, passphrase);
        writeString(out, sslProtocol);
        out.writeInt(maxRetryCount);
        out.writeInt(retryCount);
        out.writeBoolean(pauseNotify.booleanValue());
        out.writeBoolean(removeOnStartup());
        writeString(out, monitorConsumerURL != null ? monitorConsumerURL.toString() : null);

        if (topic != null) {
            writeString(out, topic.getName());
            writeString(out, topic.getVisibility());
            Dialect[] dialect = topic.getDialect();

            if (dialect != null) {
                out.writeInt(dialect.length);

                for (int i = 0; i < dialect.length; i++) {
                    writeString(out, dialect[i].getName());
                    writeStringArray(out, dialect[i].getQueryLanguage());
                }
            } else {
                out.writeInt(-1);
            }
        } else {
            out.writeUTF("topic_null");
        }

        if (policy != null) {
            out.writeInt(policy.getRate());

            Query query = policy.getQuery();

            if (query != null) {
                writeString(out, query.getExpression());
                writeString(out, query.getQueryLanguage());
            } else {
                out.writeUTF("query_null");
            }

            Action[] action = policy.getAction();

            if (action != null) {
                out.writeInt(action.length);

                for (int i = 0; i < action.length; i++) {
                    writeString(out, action[i].getName());
                    out.writeBoolean(action[i].isDoActionWhenQueryIs());
                    Parameter[] param = action[i].getParameter();

                    if (param != null) {
                        out.writeInt(param.length);

                        for (int j = 0; j < param.length; j++) {
                            writeString(out, param[j].getName());
                            out.writeObject(param[j].getValue());
                        }
                    } else {
                        out.writeInt(-1);
                    }
                }
            } else {
                out.writeInt(-1);
            }
        } else {
            out.writeInt(-1);
        }
    }

    public Subscription cloneSubscription()
        throws IllegalArgumentException, Exception {

        Subscription subscription = new Subscription();
        subscription.setId(this.getId());
        subscription.setName(this.getName());
        subscription.setExpirationTime(this.getExpirationTime());
        subscription.setType(this.getType());
        subscription.setMonitorConsumerURL(new org.apache.axis2.databinding.types.URI(this.getMonitorConsumerURL()
                .toString()));

        if (this.getJarPath() != null) {
            subscription.setJarPath(new org.apache.axis2.databinding.types.URI(this.getJarPath().toString()));
        }

        Property[] property = this.getProperty();
        if (property != null) {
            org.glite.ce.monitorapij.types.Property[] newProperty = new org.glite.ce.monitorapij.types.Property[property.length];

            for (int i = 0; i < property.length; i++) {
                newProperty[i] = new org.glite.ce.monitorapij.types.Property();
                newProperty[i].setName(property[i].getName());
                newProperty[i].setValue(property[i].getValue());
            }
            subscription.setProperty(newProperty);
        }

        Topic topic = this.getTopic();

        org.glite.ce.monitorapij.types.Topic newtopic = new org.glite.ce.monitorapij.types.Topic();
        newtopic.setName(topic.getName());

        Dialect[] dialect = topic.getDialect();
        if (dialect != null) {
            org.glite.ce.monitorapij.types.Dialect[] newDialect = new org.glite.ce.monitorapij.types.Dialect[dialect.length];

            for (int i = 0; i < dialect.length; i++) {
                newDialect[i] = new org.glite.ce.monitorapij.types.Dialect();
                newDialect[i].setName(dialect[i].getName());
                newDialect[i].setQueryLanguage(dialect[i].getQueryLanguage());
            }
            newtopic.setDialect(newDialect);
        }

        subscription.setTopic(newtopic);
        Policy policy = this.getPolicy();

        if (policy != null) {
            org.glite.ce.monitorapij.types.Policy newPolicy = new org.glite.ce.monitorapij.types.Policy();
            newPolicy.setRate(policy.getRate());

            Query query = policy.getQuery();
            if (query != null) {
                org.glite.ce.monitorapij.types.Query tmpQuery = new org.glite.ce.monitorapij.types.Query();
                tmpQuery.setExpression(query.getExpression());
                tmpQuery.setQueryLanguage(query.getQueryLanguage());
                newPolicy.setQuery(tmpQuery);

            }

            Action[] action = policy.getAction();
            if (action != null) {
                org.glite.ce.monitorapij.types.Action[] newAction = new org.glite.ce.monitorapij.types.Action[action.length];

                for (int i = 0; i < action.length; i++) {
                    newAction[i] = new org.glite.ce.monitorapij.types.Action();
                    newAction[i].setName(action[i].getName());
                    newAction[i].setDoActionWhenQueryIs(action[i].isDoActionWhenQueryIs());

                    Parameter[] parameter = action[i].getParameter();
                    if (parameter != null) {
                        org.glite.ce.monitorapij.types.Parameter[] newParameter = new org.glite.ce.monitorapij.types.Parameter[parameter.length];

                        for (int j = 0; j < parameter.length; j++) {
                            newParameter[j] = new org.glite.ce.monitorapij.types.Parameter();
                            newParameter[j].setName(parameter[j].getName());
                            newParameter[j].setValue((String) parameter[j].getValue());
                        }
                        newAction[i].setParameter(newParameter);
                    }
                }

                newPolicy.setAction(newAction);
            }
            subscription.setPolicy(newPolicy);
        }

        return subscription;
    }

    private void writeString(ObjectOutput out, String s)
        throws IOException {
        if (s != null)
            out.writeUTF(s);
        else
            out.writeUTF("_null_");
    }

    private void writeStringArray(ObjectOutput out, String[] array)
        throws IOException {
        if (array != null) {
            out.writeInt(array.length);
            for (int k = 0; k < array.length; k++) {
                if (array[k] != null)
                    out.writeUTF(array[k]);
                else
                    out.writeUTF("");
            }
        } else {
            out.writeInt(-1);
        }
    }

}
