package com.yayan.kripikazzahro.Model;

import java.util.List;

/**
 * Created by yayan dwi kusuma on 1/31/2018.
 */

public class Request {

    private String phone;
    private String name;
    private String addres;
    private String total;
    private String status;
    private List<Order> foods;  //list data keripik

    public Request() {
    }

    public Request(String phone, String name, String addres, String total, List<Order> foods) {
        this.phone = phone;
        this.name = name;
        this.addres = addres;
        this.total = total;
        this.foods = foods;
        this.status = "0"; // default is=0 ; 1= Proses Pengiriman ;3 = Terkirim
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {

    }


}
