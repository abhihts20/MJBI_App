
package com.weshowedup.mjbi.Response.AddCartResponse;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("cart_id")
    private Long mCartId;
    @SerializedName("created")
    private Long mCreated;
    @SerializedName("material_id")
    private String mMaterialId;
    @SerializedName("quantity")
    private String mQuantity;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("total_price")
    private Long mTotalPrice;
    @SerializedName("transaction")
    private String mTransaction;
    @SerializedName("user_id")
    private String mUserId;

    public Long getCartId() {
        return mCartId;
    }

    public void setCartId(Long cartId) {
        mCartId = cartId;
    }

    public Long getCreated() {
        return mCreated;
    }

    public void setCreated(Long created) {
        mCreated = created;
    }

    public String getMaterialId() {
        return mMaterialId;
    }

    public void setMaterialId(String materialId) {
        mMaterialId = materialId;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public Long getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
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

}
