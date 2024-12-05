package com.example.duan1.Model;

import java.io.Serializable;

public class BaiTap implements Serializable {
    int ma;
    String ten;
    String hanNop;
    int TrangThai;
    String NoiDung;
    int mon;

    public BaiTap(int ma, String ten, String hanNop, int trangThai, int mon) {
        this.ma = ma;
        this.ten = ten;
        this.hanNop = hanNop;
        TrangThai = trangThai;
        this.mon = mon;
    }

    public BaiTap(String ten, String hanNop, int trangThai, int mon) {
        this.ten = ten;
        this.hanNop = hanNop;
        TrangThai = trangThai;
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

    public String getHanNop() {
        return hanNop;
    }

    public void setHanNop(String hanNop) {
        this.hanNop = hanNop;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }
}
