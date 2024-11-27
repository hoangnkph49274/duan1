package com.example.duan1.Model;

import java.io.Serializable;

public class TaiLieu implements Serializable {
    int ma;
    String ten;
    String loai;
    String Ddan;
    int mon;

    public TaiLieu() {
    }

    public TaiLieu(int ma, String ten, String loai, String ddan, int mon) {
        this.ma = ma;
        this.ten = ten;
        this.loai = loai;
        Ddan = ddan;
        this.mon = mon;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getDdan() {
        return Ddan;
    }

    public void setDdan(String ddan) {
        Ddan = ddan;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }
}
