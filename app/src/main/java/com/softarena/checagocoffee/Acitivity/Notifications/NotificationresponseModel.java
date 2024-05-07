package com.softarena.checagocoffee.Acitivity.Notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationresponseModel {
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("totalRecords")
    @Expose
    private int totalRecords;
    @SerializedName("totalPages")
    @Expose
    private int totalPages;
    @SerializedName("getnotification_result")
    @Expose
    private List<NotificationModel> getnotificationResult = null;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<NotificationModel> getGetnotificationResult() {
        return getnotificationResult;
    }

    public void setGetnotificationResult(List<NotificationModel> getnotificationResult) {
        this.getnotificationResult = getnotificationResult;
    }
}
