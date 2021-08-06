package com.palebluedot.mypotion.data.repository.detail.model;

public class Row {
    private String NTK_MTHD;    //섭취방법
    private String PRDLST_NM;   //품목명
    private String RAWMTRL_NM;  //원재료 (컴마로 구분)
    private String PRMS_DT;
    private String CRET_DTM;
    private String POG_DAYCNT;  //유통기한
    private String PRDLST_REPORT_NO;
    private String PRIMARY_FNCLTY;  //주된 기능
    private String CSTDY_MTHD;
    private String BSSH_NM;     //제조사
    private String LAST_UPDT_DTM;
    private String LCNS_NO;
    private String IFTKN_ATNT_MATR_CN;  //섭취 시 주의사항
    private String STDR_STND;
    private String DISPOS;  //성상성 (생김새)
   private String SHAP;

    @Override
    public String toString() {
        return "Row{" +
                "NTK_MTHD='" + NTK_MTHD + '\'' +
                ", PRDLST_NM='" + PRDLST_NM + '\'' +
                ", RAWMTRL_NM='" + RAWMTRL_NM + '\'' +
                ", PRMS_DT='" + PRMS_DT + '\'' +
                ", CRET_DTM='" + CRET_DTM + '\'' +
                ", POG_DAYCNT='" + POG_DAYCNT + '\'' +
                ", PRDLST_REPORT_NO='" + PRDLST_REPORT_NO + '\'' +
                ", PRIMARY_FNCLTY='" + PRIMARY_FNCLTY + '\'' +
                ", CSTDY_MTHD='" + CSTDY_MTHD + '\'' +
                ", BSSH_NM='" + BSSH_NM + '\'' +
                ", LAST_UPDT_DTM='" + LAST_UPDT_DTM + '\'' +
                ", LCNS_NO='" + LCNS_NO + '\'' +
                ", IFTKN_ATNT_MATR_CN='" + IFTKN_ATNT_MATR_CN + '\'' +
                ", STDR_STND='" + STDR_STND + '\'' +
                ", DISPOS='" + DISPOS + '\'' +
                ", SHAP='" + SHAP + '\'' +
                '}';
    }

    public String getNTK_MTHD() {
        return NTK_MTHD;
    }

    public void setNTK_MTHD(String NTK_MTHD) {
        this.NTK_MTHD = NTK_MTHD;
    }

    public String getPRDLST_NM() {
        return PRDLST_NM;
    }

    public void setPRDLST_NM(String PRDLST_NM) {
        this.PRDLST_NM = PRDLST_NM;
    }

    public String getRAWMTRL_NM() {
        return RAWMTRL_NM;
    }

    public void setRAWMTRL_NM(String RAWMTRL_NM) {
        this.RAWMTRL_NM = RAWMTRL_NM;
    }

    public String getPRMS_DT() {
        return PRMS_DT;
    }

    public void setPRMS_DT(String PRMS_DT) {
        this.PRMS_DT = PRMS_DT;
    }

    public String getCRET_DTM() {
        return CRET_DTM;
    }

    public void setCRET_DTM(String CRET_DTM) {
        this.CRET_DTM = CRET_DTM;
    }

    public String getPOG_DAYCNT() {
        return POG_DAYCNT;
    }

    public void setPOG_DAYCNT(String POG_DAYCNT) {
        this.POG_DAYCNT = POG_DAYCNT;
    }

    public String getPRDLST_REPORT_NO() {
        return PRDLST_REPORT_NO;
    }

    public void setPRDLST_REPORT_NO(String PRDLST_REPORT_NO) {
        this.PRDLST_REPORT_NO = PRDLST_REPORT_NO;
    }

    public String getPRIMARY_FNCLTY() {
        return PRIMARY_FNCLTY;
    }

    public void setPRIMARY_FNCLTY(String PRIMARY_FNCLTY) {
        this.PRIMARY_FNCLTY = PRIMARY_FNCLTY;
    }

    public String getCSTDY_MTHD() {
        return CSTDY_MTHD;
    }

    public void setCSTDY_MTHD(String CSTDY_MTHD) {
        this.CSTDY_MTHD = CSTDY_MTHD;
    }

    public String getBSSH_NM() {
        return BSSH_NM;
    }

    public void setBSSH_NM(String BSSH_NM) {
        this.BSSH_NM = BSSH_NM;
    }

    public String getLAST_UPDT_DTM() {
        return LAST_UPDT_DTM;
    }

    public void setLAST_UPDT_DTM(String LAST_UPDT_DTM) {
        this.LAST_UPDT_DTM = LAST_UPDT_DTM;
    }

    public String getLCNS_NO() {
        return LCNS_NO;
    }

    public void setLCNS_NO(String LCNS_NO) {
        this.LCNS_NO = LCNS_NO;
    }

    public String getIFTKN_ATNT_MATR_CN() {
        return IFTKN_ATNT_MATR_CN;
    }

    public void setIFTKN_ATNT_MATR_CN(String IFTKN_ATNT_MATR_CN) {
        this.IFTKN_ATNT_MATR_CN = IFTKN_ATNT_MATR_CN;
    }

    public String getSTDR_STND() {
        return STDR_STND;
    }

    public void setSTDR_STND(String STDR_STND) {
        this.STDR_STND = STDR_STND;
    }

    public String getDISPOS() {
        return DISPOS;
    }

    public void setDISPOS(String DISPOS) {
        this.DISPOS = DISPOS;
    }

    public String getSHAP() {
        return SHAP;
    }

    public void setSHAP(String SHAP) {
        this.SHAP = SHAP;
    }

    public Row(String NTK_MTHD, String PRDLST_NM, String RAWMTRL_NM, String PRMS_DT, String CRET_DTM, String POG_DAYCNT, String PRDLST_REPORT_NO, String PRIMARY_FNCLTY, String CSTDY_MTHD, String BSSH_NM, String LAST_UPDT_DTM, String LCNS_NO, String IFTKN_ATNT_MATR_CN, String STDR_STND, String DISPOS, String SHAP) {
        this.NTK_MTHD = NTK_MTHD;
        this.PRDLST_NM = PRDLST_NM;
        this.RAWMTRL_NM = RAWMTRL_NM;
        this.PRMS_DT = PRMS_DT;
        this.CRET_DTM = CRET_DTM;
        this.POG_DAYCNT = POG_DAYCNT;
        this.PRDLST_REPORT_NO = PRDLST_REPORT_NO;
        this.PRIMARY_FNCLTY = PRIMARY_FNCLTY;
        this.CSTDY_MTHD = CSTDY_MTHD;
        this.BSSH_NM = BSSH_NM;
        this.LAST_UPDT_DTM = LAST_UPDT_DTM;
        this.LCNS_NO = LCNS_NO;
        this.IFTKN_ATNT_MATR_CN = IFTKN_ATNT_MATR_CN;
        this.STDR_STND = STDR_STND;
        this.DISPOS = DISPOS;
        this.SHAP = SHAP;
    }
}