package com.grocery.cart;

public class OrderDetailsModel {
    String p_name;

    public OrderDetailsModel(String p_name) {
        this.p_name = p_name;
//        this.p_price = p_price;
//        this.p_quantity = p_quantity;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

//    public String getP_price() {
//        return p_price;
//    }
//
//    public void setP_price(String p_price) {
//        this.p_price = p_price;
//    }

//    public String getP_quantity() {
//        return p_quantity;
//    }
//
//    public void setP_quantity(String p_quantity) {
//        this.p_quantity = p_quantity;
//    }
}
