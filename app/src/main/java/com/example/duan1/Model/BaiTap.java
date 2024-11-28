package com.example.duan1.Model;

import java.io.Serializable;

public class BaiTap implements Serializable {
    String ma;
    String ten;
    String hanNop;
    String TrangThai;
    int mon;

    public BaiTap(String ma, String ten, String hanNop, String trangThai, int mon) {
        this.ma = ma;
        this.ten = ten;
        this.hanNop = hanNop;
        TrangThai = trangThai;
        this.mon = mon;
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

    public String getHanNop() {
        return hanNop;
    }

    public void setHanNop(String hanNop) {
        this.hanNop = hanNop;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }
}
