package com.palebluedot.mypotion.data.repository.detail.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailVo {
    public DetailVo(C003 c003) {
        C003 = c003;
    }

    @Expose
    @SerializedName("C003")
    private C003 C003;

    public C003 getC003() {
        return C003;
    }

    public void setC003(C003 C003) {
        this.C003 = C003;
    }

    public static class C003 {
        public C003(List<Row> row, String total_count, RESULT RESULT) {
            this.row = row;
            this.total_count = total_count;
            this.RESULT = RESULT;
        }

        @Expose
        @SerializedName("row")
        private List<Row> row;
        @Expose
        @SerializedName("total_count")
        private String total_count;
        @Expose
        @SerializedName("RESULT")
        private RESULT RESULT;

        public List<Row> getRow() {
            return row;
        }

        public void setRow(List<Row> row) {
            this.row = row;
        }

        public String getTotal_count() {
            return total_count;
        }

        public void setTotal_count(String total_count) {
            this.total_count = total_count;
        }

        public RESULT getRESULT() {
            return RESULT;
        }

        public void setRESULT(RESULT RESULT) {
            this.RESULT = RESULT;
        }

        @NonNull
        @Override
        public String toString() {
            return "C003{" +
                    "row=" + row +
                    ", total_count='" + total_count + '\'' +
                    ", RESULT=" + RESULT +
                    '}';
        }
    }

    public static class Row {
        public Row(String SHAP, String DISPOS, String STDR_STND, String IFTKN_ATNT_MATR_CN, String LCNS_NO, String LAST_UPDT_DTM, String BSSH_NM, String CSTDY_MTHD, String PRIMARY_FNCLTY, String PRDLST_REPORT_NO, String POG_DAYCNT, String CRET_DTM, String PRMS_DT, String RAWMTRL_NM, String PRDLST_NM, String NTK_MTHD) {
            this.SHAP = SHAP;
            this.DISPOS = DISPOS;
            this.STDR_STND = STDR_STND;
            this.IFTKN_ATNT_MATR_CN = IFTKN_ATNT_MATR_CN;
            this.LCNS_NO = LCNS_NO;
            this.LAST_UPDT_DTM = LAST_UPDT_DTM;
            this.BSSH_NM = BSSH_NM;
            this.CSTDY_MTHD = CSTDY_MTHD;
            this.PRIMARY_FNCLTY = PRIMARY_FNCLTY;
            this.PRDLST_REPORT_NO = PRDLST_REPORT_NO;
            this.POG_DAYCNT = POG_DAYCNT;
            this.CRET_DTM = CRET_DTM;
            this.PRMS_DT = PRMS_DT;
            this.RAWMTRL_NM = RAWMTRL_NM;
            this.PRDLST_NM = PRDLST_NM;
            this.NTK_MTHD = NTK_MTHD;
        }

        @Expose
        @SerializedName("SHAP")
        private String SHAP;
        @Expose
        @SerializedName("DISPOS")
        private String DISPOS;
        @Expose
        @SerializedName("STDR_STND")
        private String STDR_STND;
        @Expose
        @SerializedName("IFTKN_ATNT_MATR_CN")
        private String IFTKN_ATNT_MATR_CN;
        @Expose
        @SerializedName("LCNS_NO")
        private String LCNS_NO;
        @Expose
        @SerializedName("LAST_UPDT_DTM")
        private String LAST_UPDT_DTM;
        @Expose
        @SerializedName("BSSH_NM")
        private String BSSH_NM;
        @Expose
        @SerializedName("CSTDY_MTHD")
        private String CSTDY_MTHD;
        @Expose
        @SerializedName("PRIMARY_FNCLTY")
        private String PRIMARY_FNCLTY;
        @Expose
        @SerializedName("PRDLST_REPORT_NO")
        private String PRDLST_REPORT_NO;
        @Expose
        @SerializedName("POG_DAYCNT")
        private String POG_DAYCNT;
        @Expose
        @SerializedName("CRET_DTM")
        private String CRET_DTM;
        @Expose
        @SerializedName("PRMS_DT")
        private String PRMS_DT;
        @Expose
        @SerializedName("RAWMTRL_NM")
        private String RAWMTRL_NM;
        @Expose
        @SerializedName("PRDLST_NM")
        private String PRDLST_NM;
        @Expose
        @SerializedName("NTK_MTHD")
        private String NTK_MTHD;

        public String getSHAP() {
            return SHAP;
        }

        public void setSHAP(String SHAP) {
            this.SHAP = SHAP;
        }

        public String getDISPOS() {
            return DISPOS;
        }

        public void setDISPOS(String DISPOS) {
            this.DISPOS = DISPOS;
        }

        public String getSTDR_STND() {
            return STDR_STND;
        }

        public void setSTDR_STND(String STDR_STND) {
            this.STDR_STND = STDR_STND;
        }

        public String getIFTKN_ATNT_MATR_CN() {
            return IFTKN_ATNT_MATR_CN;
        }

        public void setIFTKN_ATNT_MATR_CN(String IFTKN_ATNT_MATR_CN) {
            this.IFTKN_ATNT_MATR_CN = IFTKN_ATNT_MATR_CN;
        }

        public String getLCNS_NO() {
            return LCNS_NO;
        }

        public void setLCNS_NO(String LCNS_NO) {
            this.LCNS_NO = LCNS_NO;
        }

        public String getLAST_UPDT_DTM() {
            return LAST_UPDT_DTM;
        }

        public void setLAST_UPDT_DTM(String LAST_UPDT_DTM) {
            this.LAST_UPDT_DTM = LAST_UPDT_DTM;
        }

        public String getBSSH_NM() {
            return BSSH_NM;
        }

        public void setBSSH_NM(String BSSH_NM) {
            this.BSSH_NM = BSSH_NM;
        }

        public String getCSTDY_MTHD() {
            return CSTDY_MTHD;
        }

        public void setCSTDY_MTHD(String CSTDY_MTHD) {
            this.CSTDY_MTHD = CSTDY_MTHD;
        }

        public String getPRIMARY_FNCLTY() {
            return PRIMARY_FNCLTY;
        }

        public void setPRIMARY_FNCLTY(String PRIMARY_FNCLTY) {
            this.PRIMARY_FNCLTY = PRIMARY_FNCLTY;
        }

        public String getPRDLST_REPORT_NO() {
            return PRDLST_REPORT_NO;
        }

        public void setPRDLST_REPORT_NO(String PRDLST_REPORT_NO) {
            this.PRDLST_REPORT_NO = PRDLST_REPORT_NO;
        }

        public String getPOG_DAYCNT() {
            return POG_DAYCNT;
        }

        public void setPOG_DAYCNT(String POG_DAYCNT) {
            this.POG_DAYCNT = POG_DAYCNT;
        }

        public String getCRET_DTM() {
            return CRET_DTM;
        }

        public void setCRET_DTM(String CRET_DTM) {
            this.CRET_DTM = CRET_DTM;
        }

        public String getPRMS_DT() {
            return PRMS_DT;
        }

        public void setPRMS_DT(String PRMS_DT) {
            this.PRMS_DT = PRMS_DT;
        }

        public String getRAWMTRL_NM() {
            return RAWMTRL_NM;
        }

        public void setRAWMTRL_NM(String RAWMTRL_NM) {
            this.RAWMTRL_NM = RAWMTRL_NM;
        }

        public String getPRDLST_NM() {
            return PRDLST_NM;
        }

        public void setPRDLST_NM(String PRDLST_NM) {
            this.PRDLST_NM = PRDLST_NM;
        }

        public String getNTK_MTHD() {
            return NTK_MTHD;
        }

        public void setNTK_MTHD(String NTK_MTHD) {
            this.NTK_MTHD = NTK_MTHD;
        }

        @Override
        public String toString() {
            return "Row{" +
                    "SHAP='" + SHAP + '\'' +
                    ", DISPOS='" + DISPOS + '\'' +
                    ", STDR_STND='" + STDR_STND + '\'' +
                    ", IFTKN_ATNT_MATR_CN='" + IFTKN_ATNT_MATR_CN + '\'' +
                    ", LCNS_NO='" + LCNS_NO + '\'' +
                    ", LAST_UPDT_DTM='" + LAST_UPDT_DTM + '\'' +
                    ", BSSH_NM='" + BSSH_NM + '\'' +
                    ", CSTDY_MTHD='" + CSTDY_MTHD + '\'' +
                    ", PRIMARY_FNCLTY='" + PRIMARY_FNCLTY + '\'' +
                    ", PRDLST_REPORT_NO='" + PRDLST_REPORT_NO + '\'' +
                    ", POG_DAYCNT='" + POG_DAYCNT + '\'' +
                    ", CRET_DTM='" + CRET_DTM + '\'' +
                    ", PRMS_DT='" + PRMS_DT + '\'' +
                    ", RAWMTRL_NM='" + RAWMTRL_NM + '\'' +
                    ", PRDLST_NM='" + PRDLST_NM + '\'' +
                    ", NTK_MTHD='" + NTK_MTHD + '\'' +
                    '}';
        }
    }

    public static class RESULT {
        public RESULT(String CODE, String MSG) {
            this.CODE = CODE;
            this.MSG = MSG;
        }

        @Override
        public String toString() {
            return "RESULT{" +
                    "CODE='" + CODE + '\'' +
                    ", MSG='" + MSG + '\'' +
                    '}';
        }

        @Expose
        @SerializedName("CODE")
        private String CODE;
        @Expose
        @SerializedName("MSG")
        private String MSG;

        public String getCODE() {
            return CODE;
        }

        public void setCODE(String CODE) {
            this.CODE = CODE;
        }

        public String getMSG() {
            return MSG;
        }

        public void setMSG(String MSG) {
            this.MSG = MSG;
        }
    }
}
