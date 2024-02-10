package com.example.mobilestore;

public class Product {
    public  String id;
    public  String  product_name;
    public  String  description;
    public  String  pkey;

    public Product(String id, String product_name, String description,String pkey) {
        this.id = id;
        this.product_name = product_name;
        this.description = description;
        this.pkey = pkey;

    }

    public Product() {
    }

    public String getid() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getproduct_name() {
        return product_name;
    }



    public String getdescription() {
        return description;
    }

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }
}
