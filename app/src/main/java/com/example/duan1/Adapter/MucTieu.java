package com.example.duan1.Adapter;

import java.io.Serializable;

public class MucTieu implements Serializable {
    String ma;
    String ten;
    String ngay;
    String trangthai;

    public MucTieu(String ma, String ten, String ngay, String trangthai) {
        this.ma = ma;
        this.ten = ten;
        this.ngay = ngay;
        this.trangthai = trangthai;
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

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
