package com.grocery.cart;

public class ContactModel {
    String img,product_info, quantity, price;
    public ContactModel(String img, String product_info, String quantity, String price){
        this.img=img;
        this.product_info=product_info;
        this.quantity=quantity;
        this.price=price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProduct_info() {
        return product_info;
    }

    public void setProduct_info(String product_info) {
        this.product_info = product_info;
    }
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ContactModel(){

    }
}
