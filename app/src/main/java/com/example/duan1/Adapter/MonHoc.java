package com.example.duan1.Adapter;

import java.io.Serializable;

public class MonHoc implements Serializable {
    String ma;
    String ten;
    int soTC;
    String GiangVien;

    public MonHoc(String ma, String ten, int soTC, String GiangVien) {
        this.ma = ma;
        this.ten = ten;
        this.soTC = soTC;
        this.GiangVien = GiangVien;
    }

    public MonHoc(String ten) {
        this.ten = ten;
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

    public int getSoTC() {
        return soTC;
    }

    public void setSoTC(int soTC) {
        this.soTC = soTC;
    }

    public String getGiangVien() {
        return GiangVien;
    }

    public void setGiangVien(String giangVien) {
        GiangVien = giangVien;
    }
}
