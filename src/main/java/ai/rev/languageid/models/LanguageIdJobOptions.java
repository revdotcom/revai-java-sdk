package ai.rev.languageid.models;

import ai.rev.speechtotext.models.CustomerUrlData;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * A LanguageIdJobOptions object represents parameters that are submitted along a new job.
 *
 * @see <a
 * href="https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob">https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob</a>
 */
public class LanguageIdJobOptions {
    /**
     * The callback url that Rev AI will send a POST to when the job has finished.
     *
     * @deprecated Use notification_config instead
     */
    @SerializedName("callback_url")
    @Deprecated
    private String callbackUrl;

    /**
     * Object containing information on the callback url that Rev AI will send a POST to when the job has finished.
     */
    @SerializedName("notification_config")
    private CustomerUrlData notificationConfig;

    /**
     * Optional information that can be provided.
     */
    @SerializedName("metadata")
    private String metadata;

    /**
     * Optional number of seconds after job completion when job is auto-deleted
     */
    @SerializedName("delete_after_seconds")
    private Integer deleteAfterSeconds;

    /**
     * The media url where the file can be downloaded.
     *
     * @deprecated Use source_config instead
     */
    @SerializedName("media_url")
    @Deprecated
    private String mediaUrl;

    /**
     * Object containing source media file information.
     */
    @SerializedName("source_config")
    private CustomerUrlData sourceConfig;

    /**
     * Returns the callback url.
     *
     * @return the callback url.
     * @deprecated Use notificationConfig and getNotificationConfig instead
     */
    @Deprecated
    public String getCallbackUrl() {
        return callbackUrl;
    }

    /**
     * Specifies the callback url that Rev AI will POST to when job processing is complete. This
     * property is optional.
     *
     * @param callbackUrl The url to POST to when job processing is complete.
     * @deprecated Use setNotificationConfig instead
     */
    @Deprecated
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    /**
     * Returns the notification config object.
     *
     * @return the notification config.
     */
    public CustomerUrlData getNotificationConfig() {
        return notificationConfig;
    }

    /**
     * Optional property to specify the callback url that Rev AI will POST to when job processing is complete
     *
     * @param callbackUrl The url to POST to when job processing is complete.
     * @param authHeaders Optional parameter to authenticate access to the callback url
     */
    public void setNotificationConfig(String callbackUrl, Map<String, String> authHeaders) {
        this.notificationConfig = new CustomerUrlData(callbackUrl, authHeaders);
    }

    /**
     * Optional property to specify the callback url that Rev AI will POST to when job processing is complete
     *
     * @param callbackUrl The url to POST to when job processing is complete.
     */
    public void setNotificationConfig(String callbackUrl) {
        setNotificationConfig(callbackUrl, null);
    }

    /**
     * Returns the metadata.
     *
     * @return A String that contains the metadata.
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * Optional metadata that is provided during job submission limited to 512 characters.
     *
     * @param metadata A String to set as the metadata.
     */
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    /**
     * Returns the value of deleteAfterSeconds.
     *
     * @return The deleteAfterSeconds value.
     */
    public Integer getDeleteAfterSeconds() {
        return deleteAfterSeconds;
    }

    /**
     * Specifies the number of seconds to be waited until the job is auto-deleted after its
     * completion.
     *
     * @param deleteAfterSeconds The number of seconds after job completion when job is auto-deleted.
     */
    public void setDeleteAfterSeconds(Integer deleteAfterSeconds) {
        this.deleteAfterSeconds = deleteAfterSeconds;
    }

    /**
     * Returns the media url.
     *
     * @return The media url.
     * @deprecated Set sourceConfig and use getSourceConfig instead
     */
    @Deprecated
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     * Specifies the url where the media can be downloaded.
     *
     * @param mediaUrl The direct download url to the file.
     * @deprecated Use setSourceConfig instead
     */
    @Deprecated
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    /**
     * Returns the source config object.
     *
     * @return the source config.
     */
    public CustomerUrlData getSourceConfig() {
        return this.sourceConfig;
    }

    /**
     * Specifies the url and any optional auth headers to access the source media download url.
     *
     * @param sourceMediaUrl The direct download url to the file.
     * @param sourceAuth     The auth headers to the source media download url.
     */

    public void setSourceConfig(String sourceMediaUrl, Map<String, String> sourceAuth) {
        this.sourceConfig = new CustomerUrlData(sourceMediaUrl, sourceAuth);
    }

    /**
     * Specifies the source media download url.
     *
     * @param sourceMediaUrl The direct download url to the file.
     */

    public void setSourceConfig(String sourceMediaUrl) {
        setSourceConfig(sourceMediaUrl, null);
    }
}
