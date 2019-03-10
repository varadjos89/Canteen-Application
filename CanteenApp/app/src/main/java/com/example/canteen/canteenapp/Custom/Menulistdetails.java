package com.example.canteen.canteenapp.Custom;

/**
 * Created by VARAD on 24/01/2017.
 */
public class Menulistdetails {
    String name,price,preference;
    int menuicon;
    public Menulistdetails(String name, String price, int menuicon, String preference) {
        this.name = name;
        this.price = price;
        this.menuicon = menuicon;
        this.preference = preference;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getMenuicon() {
        return menuicon;
    }

    public String getPreference() {
        return preference;
    }
}
