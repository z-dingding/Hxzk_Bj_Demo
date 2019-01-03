package com.hxzk_bj_demo.javabean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by ${赵江涛} on 2018-1-18.
 * 作用:
 */


public class InversBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements MultiItemEntity, Parcelable {
        /**
         * bdwmScore : 暂无
         * bsCode1 : JY31108280609317
         * bsCode2 : 110108
         * dataState :
         * dataType :
         * description :
         * distance :
         * draw :
         * entAddress : 北京市海淀区丰贤东路1号院
         * entName : 中国船舶工业系统工程研究院
         * entType : 5
         * id : 4028817d5fe177a6015fe1d214b27bfd
         * imageState :
         * installType :
         * isYgcy : 1
         * lastdate : null
         * lat : 0
         * lhLevel : B2
         * licenceNo : JY31108280609317
         * linkTel :
         * lng : 0
         * managCla :
         * offset : 0
         * order :
         * page : 0
         * pageSize : 10
         * pjDate :
         * pn : 1
         * ptlx : 1
         * reason :
         * rowIndex : 1
         * rows : 0
         * scDate :
         * score : 暂无
         * showForm : 1
         * sjName : 中国船舶工业系统工程研究院
         * sjZb :
         * sorts :
         * status : 1
         * urlPath :
         * ygEntInfo : null
         * yysj :
         * zone : 海淀区
         * zoneCode : 110800000000
         * zonename :
         */

        private String bdwmScore;
        private String bsCode1;
        private String bsCode2;
        private String dataState;
        private String dataType;
        private String description;
        private String distance;
        private String draw;
        private String entAddress;
        private String entName;
        private String entType;
        private String id;
        private String imageState;
        private String installType;
        private String isYgcy;
        private Object lastdate;
        private int lat;
        private String lhLevel;
        private String licenceNo;
        private String linkTel;
           private int lng;
        private String managCla;
           private int offset;
        private String order;
           private int page;
           private int pageSize;
        private String pjDate;
           private int pn;
        private String ptlx;
        private String reason;
        private String rowIndex;
           private int rows;
        private String scDate;
        private String score;
        private String showForm;
        private String sjName;
        private String sjZb;
        private String sorts;
        private String status;
        private String urlPath;
        private Object ygEntInfo;
        private String yysj;
        private String zone;
        private String zoneCode;
        private String zonename;

        public String getBdwmScore() {
            return bdwmScore;
        }

        public void setBdwmScore(String bdwmScore) {
            this.bdwmScore = bdwmScore;
        }

        public String getBsCode1() {
            return bsCode1;
        }

        public void setBsCode1(String bsCode1) {
            this.bsCode1 = bsCode1;
        }

        public String getBsCode2() {
            return bsCode2;
        }

        public void setBsCode2(String bsCode2) {
            this.bsCode2 = bsCode2;
        }

        public String getDataState() {
            return dataState;
        }

        public void setDataState(String dataState) {
            this.dataState = dataState;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDraw() {
            return draw;
        }

        public void setDraw(String draw) {
            this.draw = draw;
        }

        public String getEntAddress() {
            return entAddress;
        }

        public void setEntAddress(String entAddress) {
            this.entAddress = entAddress;
        }

        public String getEntName() {
            return entName;
        }

        public void setEntName(String entName) {
            this.entName = entName;
        }

        public String getEntType() {
            return entType;
        }

        public void setEntType(String entType) {
            this.entType = entType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageState() {
            return imageState;
        }

        public void setImageState(String imageState) {
            this.imageState = imageState;
        }

        public String getInstallType() {
            return installType;
        }

        public void setInstallType(String installType) {
            this.installType = installType;
        }

        public String getIsYgcy() {
            return isYgcy;
        }

        public void setIsYgcy(String isYgcy) {
            this.isYgcy = isYgcy;
        }

        public Object getLastdate() {
            return lastdate;
        }

        public void setLastdate(Object lastdate) {
            this.lastdate = lastdate;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public String getLhLevel() {
            return lhLevel;
        }

        public void setLhLevel(String lhLevel) {
            this.lhLevel = lhLevel;
        }

        public String getLicenceNo() {
            return licenceNo;
        }

        public void setLicenceNo(String licenceNo) {
            this.licenceNo = licenceNo;
        }

        public String getLinkTel() {
            return linkTel;
        }

        public void setLinkTel(String linkTel) {
            this.linkTel = linkTel;
        }

        public int getLng() {
            return lng;
        }

        public void setLng(int lng) {
            this.lng = lng;
        }

        public String getManagCla() {
            return managCla;
        }

        public void setManagCla(String managCla) {
            this.managCla = managCla;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public String getPjDate() {
            return pjDate;
        }

        public void setPjDate(String pjDate) {
            this.pjDate = pjDate;
        }

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public String getPtlx() {
            return ptlx;
        }

        public void setPtlx(String ptlx) {
            this.ptlx = ptlx;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getRowIndex() {
            return rowIndex;
        }

        public void setRowIndex(String rowIndex) {
            this.rowIndex = rowIndex;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public String getScDate() {
            return scDate;
        }

        public void setScDate(String scDate) {
            this.scDate = scDate;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getShowForm() {
            return showForm;
        }

        public void setShowForm(String showForm) {
            this.showForm = showForm;
        }

        public String getSjName() {
            return sjName;
        }

        public void setSjName(String sjName) {
            this.sjName = sjName;
        }

        public String getSjZb() {
            return sjZb;
        }

        public void setSjZb(String sjZb) {
            this.sjZb = sjZb;
        }

        public String getSorts() {
            return sorts;
        }

        public void setSorts(String sorts) {
            this.sorts = sorts;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUrlPath() {
            return urlPath;
        }

        public void setUrlPath(String urlPath) {
            this.urlPath = urlPath;
        }

        public Object getYgEntInfo() {
            return ygEntInfo;
        }

        public void setYgEntInfo(Object ygEntInfo) {
            this.ygEntInfo = ygEntInfo;
        }

        public String getYysj() {
            return yysj;
        }

        public void setYysj(String yysj) {
            this.yysj = yysj;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getZoneCode() {
            return zoneCode;
        }

        public void setZoneCode(String zoneCode) {
            this.zoneCode = zoneCode;
        }

        public String getZonename() {
            return zonename;
        }

        public void setZonename(String zonename) {
            this.zonename = zonename;
        }


        private int itemType = 0;
        private int spansize = 1;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.bdwmScore);
            dest.writeString(this.bsCode1);
            dest.writeString(this.bsCode2);
            dest.writeString(this.dataState);
            dest.writeString(this.dataType);
            dest.writeString(this.description);
            dest.writeString(this.distance);
            dest.writeString(this.draw);
            dest.writeString(this.entAddress);
            dest.writeString(this.entName);
            dest.writeString(this.entType);
            dest.writeString(this.id);
            dest.writeString(this.imageState);
            dest.writeString(this.installType);
            dest.writeString(this.isYgcy);
          //  dest.writeInt(this.lastdate);
            dest.writeInt(this.lat);
            dest.writeString(lhLevel);
            dest.writeString(licenceNo);



            dest.writeString(this.linkTel);
            dest.writeInt(this.lng);
            dest.writeString(this.managCla);
            dest.writeInt(this.offset);
            dest.writeString(this.order);
            dest.writeInt(this.page);
            dest.writeInt(this.pageSize);
            dest.writeString(this.pjDate);
            dest.writeInt(this.pn);
            dest.writeString(this.ptlx);
            dest.writeString(this.reason);
            dest.writeString(this.rowIndex);
            dest.writeInt(this.rows);
            dest.writeString(this.scDate);
            dest.writeString(this.score);
            dest.writeString(this.showForm);
            dest.writeString(this.sjName);
            dest.writeString(this.sjZb);
            dest.writeString(this.sorts);
            dest.writeString(this.status);
            dest.writeString(this.urlPath);
           // dest.writeString(this.ygEntInfo);
            dest.writeString(this.yysj);
            dest.writeString(this.zone);
            dest.writeString(this.zoneCode);
            dest.writeString(this.zonename);


        }

        @Override
        public int getItemType() {
            return itemType;
        }


        public void setItemType(int itemType) {
            if (itemType == 1 || itemType == 0) {
                this.itemType = itemType;
            }else {
                this.itemType = 0;
            }
        }


    }
}