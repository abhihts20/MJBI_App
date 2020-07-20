
package com.weshowedup.mjbi.Response.TodayOrderResponse;

import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("created")
    private String mCreated;
    @SerializedName("id")
    private String mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("material_id")
    private String mMaterialId;
    @SerializedName("modify")
    private String mModify;
    @SerializedName("name")
    private String mName;
    @SerializedName("order_number")
    private String mOrderNumber;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("quantity")
    private String mQuantity;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("total_price")
    private String mTotalPrice;
    @SerializedName("transaction")
    private String mTransaction;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("user_name")
    private String mUserName;

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = created;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getMaterialId() {
        return mMaterialId;
    }

    public void setMaterialId(String materialId) {
        mMaterialId = materialId;
    }

    public String getModify() {
        return mModify;
    }

    public void setModify(String modify) {
        mModify = modify;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOrderNumber() {
        return mOrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        mOrderNumber = orderNumber;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        mTotalPrice = totalPrice;
    }

    public String getTransaction() {
        return mTransaction;
    }

    public void setTransaction(String transaction) {
        mTransaction = transaction;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

}
