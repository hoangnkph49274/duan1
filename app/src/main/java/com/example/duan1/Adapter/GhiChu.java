package com.example.duan1.Adapter;

import java.io.Serializable;

public class GhiChu implements Serializable {
    String ma;
    String ten;
    String ngay;

    public GhiChu(String ma, String ten, String ngay) {
        this.ma = ma;
        this.ten = ten;
        this.ngay = ngay;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
