package com.example.canteen.canteenapp.Custom;

/**
 * Created by VARAD on 12/03/2017.
 */

public class Orderlistdetails  {
    String menu;
    int menuprice,quantity,totalprice,imgResource;

    public Orderlistdetails(String menu, int menuprice, int quantity, int totalprice, int imgResource) {
        this.menu = menu;
        this.menuprice = menuprice;
        this.quantity = quantity;
        this.totalprice = totalprice;
        this.imgResource = imgResource;
    }

    public String getMenu() {
        return menu;
    }

    public int getMenuprice() {
        return menuprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public int getImgResource() {
        return imgResource;
    }
}
