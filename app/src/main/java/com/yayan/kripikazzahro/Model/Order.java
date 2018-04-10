package com.yayan.kripikazzahro.Model;

import android.widget.ImageButton;

/**
 * Created by yayan dwi kusuma on 1/29/2018.
 */

public class Order {

    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Price;
    private String Berat;
    private String Image;





    public Order() {
    }

    public Order(String productId, String productName, String quantity, String price, String berat,String image) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Berat = berat;
        Image = image;

    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getBerat() {
        return Berat;
    }

    public void setBerat(String berat) {
        Berat = berat;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }



}
