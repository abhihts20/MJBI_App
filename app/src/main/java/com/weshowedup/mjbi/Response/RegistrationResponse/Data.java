
package com.weshowedup.mjbi.Response.RegistrationResponse;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("pincode")
    private String mPincode;
    @SerializedName("created")
    private Long mCreated;
    @SerializedName("device_token")
    private String mDeviceToken;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private Long mId;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name")
    private String mName;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("user_type")
    private Long mUserType;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getPincode(){return mPincode;}

    public void setPincode(String pincode){mPincode=pincode;}

    public Long getCreated() {
        return mCreated;
    }

    public void setCreated(Long created) {
        mCreated = created;
    }

    public String getDeviceToken() {
        return mDeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        mDeviceToken = deviceToken;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public Long getUserType() {
        return mUserType;
    }

    public void setUserType(Long userType) {
        mUserType = userType;
    }

}
