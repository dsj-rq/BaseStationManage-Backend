package com.basestation.service;

import com.basestation.bean.BaseStationBean;
import com.basestation.bean.CommonResponse;
import com.basestation.bean.MapBounds;
import com.basestation.bean.Page;
import com.basestation.dao.BaseStationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by me on 2020/4/30.
 */
@Service
public class BaseStationService {
    private Logger logger = LoggerFactory.getLogger(BaseStationService.class);

    @Autowired
    private BaseStationDao baseStationDao;

    private static final String ERROR_INFO_STR = "基站服务异常！";

    public CommonResponse listBaseStaionInfoByPage(String keyWord,Page page){

        try {
            return new CommonResponse().success(baseStationDao.listStationByPage(keyWord,page),page);
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    public CommonResponse countAllBaseStation(){
        try {
            return new CommonResponse().success(baseStationDao.countAllBaseStation());
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    public CommonResponse statisticByProvince(){
        try {
            return new CommonResponse().success(baseStationDao.statisticByProvince());
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    public CommonResponse statisticByType(){
        try {
            return new CommonResponse().success(baseStationDao.statisticByType());
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    public CommonResponse statisticByPrecision(){
        try {
            return new CommonResponse().success(baseStationDao.statisticByPrecision());
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    public CommonResponse statisticByMnc(){
        try {
            return new CommonResponse().success(baseStationDao.statisticByMnc());
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    public CommonResponse statisticByDate(){
        try {
            return new CommonResponse().success(baseStationDao.statisticByDate());
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    public CommonResponse updateBaseStationInfo(String id,String key,String val){
        try {
            return new CommonResponse().success(baseStationDao.updateBaseStationInfo(id,key,val));
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    public CommonResponse insertBaseStation(BaseStationBean baseStation){
        try {
            return new CommonResponse().success(baseStationDao.insertBaseStation(baseStation));
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }

    public CommonResponse deleteBaseStation(String id){
        try {
            return new CommonResponse().success(baseStationDao.deleteBaseStation(id));
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().error(ERROR_INFO_STR);
    }
    public CommonResponse getListByBounds(MapBounds bounds){
        try {
            return new CommonResponse().success(baseStationDao.listStationByBounds(bounds));
        } catch (Exception e) {
            logger.error(ERROR_INFO_STR,e);
        }
        return new CommonResponse().success(new ArrayList());
    }
}
