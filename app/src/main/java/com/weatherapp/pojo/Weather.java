
package com.weatherapp.pojo;

public class Weather {



    private Integer id;


    private String main;


    private String description;


    private String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconDrawable() {
        if (id >= 0 && id <= 300) {
            return "@drawable/thunderstrom";
        } else if (id >= 300 && id <= 500) {
            return "@drawable/lightrain";
        } else if (id >= 500 && id <= 600) {
            return "@drawable/lightrain";
        } else if (id >= 600 && id <= 700) {
            return "@drawable/thunderstrom";
        } else if (id >= 701 && id <= 771) {
            return "@drawable/sunny";
        } else if (id >= 772 && id <= 800) {
            return "@drawable/sunny";
        } else if (id == 801) {
            return "@drawable/sunny";
        } else if (id >= 801 && id <= 804) {
            return "@drawable/cloud";
        } else if (id >= 900 && id <= 902) {
            return "@drawable/thunderstrom";
        }
        if (id == 903) {
            return "@drawable/lightrain";
        }
        if (id == 904) {
            return "@drawable/sunny";
        }
        if (id >= 905 && id <= 1000) {
            return "@drawable/thunderstrom";
        }
        return "@drawable/sunny";
    }

}
