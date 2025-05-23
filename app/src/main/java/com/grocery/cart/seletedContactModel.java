package com.grocery.cart;

public class seletedContactModel {

    String product_info, quantity, price, nQuantity;
     String img;
    public seletedContactModel(String img, String product_info, String quantity, String price, String nQuantity){
        this.img =img;
        this.product_info=product_info;
        this.quantity=quantity;
        this.price=price;
        this.nQuantity=nQuantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
//
//    public String getProduct_info() {
//        return product_info;
//    }
//
//    public void setProduct_info(String product_info) {
//        this.product_info = product_info;
//    }
//    public String getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(String quantity) {
//        this.quantity = quantity;
//    }
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    public seletedContactModel(){
//
//    }
}
