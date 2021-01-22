package com.cdh.campcar.Data;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class ProductBean {
    private static ProductBean vo = null;
    public static ProductBean getInstance(  ){
        if(vo == null){
            vo = new ProductBean( );
        }
        return vo;
    }

    private int seq   		= 0;
    private String carNm	    = "";
    private String carEmail	    = "";
    private String carYear      = "";
    private String carKm        = "";
    private String carAddr      = "";
    private String carTel       = "";
    private String carFuel      = "";
    private String carAmt       = "";
    private String updDt        = "";
    private String carInfo      = "";
    private String carImg01     = "";
    private String carImg02     = "";
    private String carImg03     = "";
    private String carImg04     = "";
    private String carImg05     = "";
    private String carImg06     = "";
    private String carImg07     = "";
    private String carImg08     = "";
    private String carImg09     = "";
    private String carImg10     = "";
    public static ProductBean prod = null;
    private static String[] dimg = null    ;
    private static ArrayList<ProductBean> plist = null ;

    public String getUpdDt() {
        return updDt;
    }

    public void setUpdDt(String updDt) {
        this.updDt = updDt;
    }

    public static ArrayList<ProductBean> getPlist() {
        return plist;
    }

    public static void setPlist(ArrayList<ProductBean> plist) {
        ProductBean.plist = plist;
    }

    public String getCarEmail() {
        return carEmail;
    }

    public void setCarEmail(String carEmail) {
        this.carEmail = carEmail;
    }

    public static String[] getDimg() {
        return ProductBean.dimg;
    }

    public static void setDimg(String[] dimg) {
        ProductBean.dimg = dimg;
    }

    public ProductBean getProd() {
        return ProductBean.prod;
    }

    public void setProd(ProductBean prod) {
        ProductBean.prod = prod;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getCarNm() {
        return carNm;
    }

    public void setCarNm(String carNm) {
        this.carNm = carNm;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getCarKm() {
        return carKm;
    }

    public void setCarKm(String carKm) {
        this.carKm = carKm;
    }

    public String getCarAddr() {
        return carAddr;
    }

    public void setCarAddr(String carAddr) {
        this.carAddr = carAddr;
    }

    public String getCarTel() {
        return carTel;
    }

    public void setCarTel(String carTel) {
        this.carTel = carTel;
    }

    public String getCarFuel() {
        return carFuel;
    }

    public void setCarFuel(String carFuel) {
        this.carFuel = carFuel;
    }

    public String getCarAmt() {
        return carAmt;
    }

    public void setCarAmt(String carAmt) {
        this.carAmt = carAmt;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public String getCarImg01() {
        return carImg01;
    }

    public void setCarImg01(String carImg01) {
        this.carImg01 = carImg01;
    }

    public String getCarImg02() {
        return carImg02;
    }

    public void setCarImg02(String carImg02) {
        this.carImg02 = carImg02;
    }

    public String getCarImg03() {
        return carImg03;
    }

    public void setCarImg03(String carImg03) {
        this.carImg03 = carImg03;
    }

    public String getCarImg04() {
        return carImg04;
    }

    public void setCarImg04(String carImg04) {
        this.carImg04 = carImg04;
    }

    public String getCarImg05() {
        return carImg05;
    }

    public void setCarImg05(String carImg05) {
        this.carImg05 = carImg05;
    }

    public String getCarImg06() {
        return carImg06;
    }

    public void setCarImg06(String carImg06) {
        this.carImg06 = carImg06;
    }

    public String getCarImg07() {
        return carImg07;
    }

    public void setCarImg07(String carImg07) {
        this.carImg07 = carImg07;
    }

    public String getCarImg08() {
        return carImg08;
    }

    public void setCarImg08(String carImg08) {
        this.carImg08 = carImg08;
    }

    public String getCarImg09() {
        return carImg09;
    }

    public void setCarImg09(String carImg09) {
        this.carImg09 = carImg09;
    }

    public String getCarImg10() {
        return carImg10;
    }

    public void setCarImg10(String carImg10) {
        this.carImg10 = carImg10;
    }
}
