
package com.weshowedup.mjbi.Response.ChangePasswordResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChangePasswordResponse {

    @SerializedName("data")
    private List<Object> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public List<Object> getData() {
        return mData;
    }

    public void setData(List<Object> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
