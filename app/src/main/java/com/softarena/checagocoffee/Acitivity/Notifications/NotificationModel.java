package com.softarena.checagocoffee.Acitivity.Notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel {
    @SerializedName("notification_id")
    @Expose
    private int notificationId;
    @SerializedName("notification_source_id")
    @Expose
    private int notificationSourceId;
    @SerializedName("notification_order_id")
    @Expose
    private int notificationOrderId;
    @SerializedName("notification_sender_image")
    @Expose
    private String notificationSenderImage;
    @SerializedName("notification_title")
    @Expose
    private String notificationTitle;
    @SerializedName("notification_type")
    @Expose
    private String notificationType;
    @SerializedName("notification_created_at")
    @Expose
    private String notificationCreatedAt;

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getNotificationSourceId() {
        return notificationSourceId;
    }

    public void setNotificationSourceId(int notificationSourceId) {
        this.notificationSourceId = notificationSourceId;
    }

    public int getNotificationOrderId() {
        return notificationOrderId;
    }

    public void setNotificationOrderId(int notificationOrderId) {
        this.notificationOrderId = notificationOrderId;
    }

    public String getNotificationSenderImage() {
        return notificationSenderImage;
    }

    public void setNotificationSenderImage(String notificationSenderImage) {
        this.notificationSenderImage = notificationSenderImage;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationCreatedAt() {
        return notificationCreatedAt;
    }

    public void setNotificationCreatedAt(String notificationCreatedAt) {
        this.notificationCreatedAt = notificationCreatedAt;
    }

}
