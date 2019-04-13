package com.example.bookmanager.model;

import java.io.Serializable;

/**
 * action (activity or event) info entity class
 */
public class ActionInfo extends BaseBean implements Serializable {

    private long actionId;
    private String actionImage;
    private String actionName;
    private String actionContent;
    private String actionUrl;

    public ActionInfo() {
    }

    public ActionInfo(long actionId, String actionImage, String actionName, String actionContent, String actionUrl) {
        this.actionId = actionId;
        this.actionImage = actionImage;
        this.actionName = actionName;
        this.actionContent = actionContent;
        this.actionUrl = actionUrl;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public String getActionImage() {
        return actionImage;
    }

    public void setActionImage(String actionImage) {
        this.actionImage = actionImage;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionContent() {
        return actionContent;
    }

    public void setActionContent(String actionContent) {
        this.actionContent = actionContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionInfo that = (ActionInfo) o;

        return actionId == that.actionId;
    }

    @Override
    public int hashCode() {
        return (int) (actionId ^ (actionId >>> 32));
    }
}
