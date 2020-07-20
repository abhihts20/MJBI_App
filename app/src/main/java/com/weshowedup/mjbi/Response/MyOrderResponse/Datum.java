
package com.weshowedup.mjbi.Response.MyOrderResponse;

import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("category_id")
    private String mCategoryId;
    @SerializedName("created")
    private String mCreated;
    @SerializedName("id")
    private String mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("name")
    private String mName;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("quantity")
    private String mQuantity;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("stock")
    private String mStock;
    @SerializedName("total_price")
    private String mTotalPrice;
    @SerializedName("transaction")
    private String mTransaction;

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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

    public String getStock() {
        return mStock;
    }

    public void setStock(String stock) {
        mStock = stock;
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

}
