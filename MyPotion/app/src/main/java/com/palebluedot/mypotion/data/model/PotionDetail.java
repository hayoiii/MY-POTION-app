package com.palebluedot.mypotion.data.model;

public class PotionDetail {
    private String takeWay;    //섭취방법
    private String name;   //품목명
    private String rawMaterials;  //원재료 (컴마로 구분)
    private String POG_DAYCNT;  //유통기한
    private String effect;  //주된 기능
    private String factory;     //제조사
    private String caution;  //섭취 시 주의사항
    private String storeWay;
    private String shape;  //성상성 (생김새)

    public PotionDetail(String takeWay, String name, String rawMaterials, String effect, String factory, String caution, String storeWay, String shape) {
        this.takeWay = takeWay;
        this.name = name;
        this.rawMaterials = rawMaterials;
        this.effect = effect;
        this.factory = factory;
        this.caution = caution;
        this.storeWay = storeWay;
        this.shape = shape;
    }

    public String getTakeWay() {
        return takeWay;
    }

    public void setTakeWay(String takeWay) {
        this.takeWay = takeWay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRawMaterials() {
        return rawMaterials;
    }

    public void setRawMaterials(String rawMaterials) {
        this.rawMaterials = rawMaterials;
    }

    public String getPOG_DAYCNT() {
        return POG_DAYCNT;
    }

    public void setPOG_DAYCNT(String POG_DAYCNT) {
        this.POG_DAYCNT = POG_DAYCNT;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getStoreWay() {
        return storeWay;
    }

    public void setStoreWay(String storeWay) {
        this.storeWay = storeWay;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}
