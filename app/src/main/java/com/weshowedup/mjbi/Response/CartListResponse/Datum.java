
package com.weshowedup.mjbi.Response.CartListResponse;

import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("cart_id")
    private String mCartId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("material_id")
    private String mMaterialId;
    @SerializedName("name")
    private String mName;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("quantity")
    private String mQuantity;
    @SerializedName("stock")
    private String mStock;
    @SerializedName("total_price")
    private String mTotalPrice;
    @SerializedName("transaction")
    private String mTransaction;

    public String getCartId() {
        return mCartId;
    }

    public void setCartId(String cartId) {
        mCartId = cartId;
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
