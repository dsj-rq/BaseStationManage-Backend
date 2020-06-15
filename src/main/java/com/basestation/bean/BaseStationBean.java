package com.basestation.bean;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 基站基本信息对象
 * Created by me on 2020/4/30.
 */
public class BaseStationBean implements RowMapper, Serializable {

    private String mcc;

    private String mnc;

    private String lac;

    private String cell;

    private double lng;

    private double lat;

    private double olng;

    private double olat;

    private int precision;

    private String address;

    private String province;

    private String type;

    private String updateDate;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getOlng() {
        return olng;
    }

    public void setOlng(double olng) {
        this.olng = olng;
    }

    public double getOlat() {
        return olat;
    }

    public void setOlat(double olat) {
        this.olat = olat;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        BaseStationBean bs = new BaseStationBean();
        bs.setMcc(resultSet.getString("MCC"));
        bs.setMnc(resultSet.getString("MNC"));
        bs.setLac(resultSet.getString("CELL"));
        bs.setCell(resultSet.getString("CELL"));
        bs.setLng(resultSet.getDouble("LNG"));
        bs.setLat(resultSet.getDouble("LAT"));
        bs.setOlng(resultSet.getDouble("O_LNG"));
        bs.setOlat(resultSet.getDouble("O_LAT"));
        bs.setPrecision(resultSet.getInt("PRECISION"));
        bs.setAddress(resultSet.getString("ADDR"));
        bs.setProvince(resultSet.getString("PROVINCE"));
        bs.setType(resultSet.getString("TYPE"));
        bs.setUpdateDate(resultSet.getString("UPDATE_DATE"));
        return bs;
    }
}
