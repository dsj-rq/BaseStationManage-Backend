package com.basestation.contoller;

import com.basestation.bean.BaseStationBean;
import com.basestation.bean.CommonResponse;
import com.basestation.bean.MapBounds;
import com.basestation.bean.Page;
import com.basestation.service.BaseStationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by me on 2020/4/29.
 */
@RestController
@RequestMapping(value = "/api/v1/baseStation" ,method={RequestMethod.GET,RequestMethod.POST})
@Api(value = "/rest", tags = "BaseStationInfoController", description = "基站信息管理接口")
public class BaseStationInfoController {
    @Autowired
    private BaseStationService baseStationService;

    /**
     * 分页获取基站列表
     * @param keyWord
     * @param page
     * @return
     */
    @ApiOperation(value = "分页获取基站列表")
    @RequestMapping("/list")
    public CommonResponse listBaseStaionInfoByPage(String keyWord, Page page){
        return baseStationService.listBaseStaionInfoByPage(keyWord,page);
    }
    @ApiOperation(value = "根据地图视野获取基站数据")
    @RequestMapping("/getListByBounds")
    public CommonResponse getListByBounds(@ModelAttribute MapBounds bounds) {
        return baseStationService.getListByBounds(bounds);
    }

    /**
     * 统计基站数据总量
     * @return
     */
    @ApiOperation(value = "统计基站数据总量")
    @RequestMapping("/countAllBaseStation")
    public CommonResponse countAllBaseStation(){
        return baseStationService.countAllBaseStation();
    }

    /**
     * 根据省分组统计基站
     * @return
     */
    @ApiOperation(value = "根据省分组统计基站")
    @RequestMapping("/statisticByProvince")
    public CommonResponse statisticByProvince(){
        return baseStationService.statisticByProvince();
    }

    /**
     * 根据基站类型统计基站
     * @return
     */
    @ApiOperation(value = "根据基站类型统计基站")
    @RequestMapping("/statisticByType")
    public CommonResponse statisticByType(){
        return baseStationService.statisticByType();
    }

    /**
     * 根据基站作用半径统计基站
     * @return
     */
    @ApiOperation(value = "根据基站作用半径统计基站")
    @RequestMapping("/statisticByPrecision")
    public CommonResponse statisticByPrecision(){
        return baseStationService.statisticByPrecision();
    }

    /**
     * 根据mnc统计基站
     * @return
     */
    @ApiOperation(value = "根据mnc统计基站")
    @RequestMapping("/statisticByMnc")
    public CommonResponse statisticByMnc(){
        return baseStationService.statisticByMnc();
    }

    /**
     * 根据日期统计基站
     * @return
     */
    @ApiOperation(value = "根据日期统计基站")
    @RequestMapping("/statisticByDate")
    public CommonResponse statisticByDate(){
        return baseStationService.statisticByDate();
    }

    /**
     * 更新基站信息
     * @param id
     * @param key
     * @param val
     * @return
     */
    @ApiOperation(value = "更新基站信息")
    @RequestMapping("/updateBaseStationInfo")
    public CommonResponse updateBaseStationInfo(String id,String key,String val){
        return baseStationService.updateBaseStationInfo(id,key,val);
    }

    /**
     * 插入基站信息
     * @param baseStation
     * @return
     */

    @ApiOperation(value = "插入基站信息")
    @RequestMapping("/insertBaseStation")
    public CommonResponse insertBaseStation(@RequestBody BaseStationBean baseStation){
        return baseStationService.insertBaseStation(baseStation);
    }

    /**
     * 删除基站信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除基站信息")
    @RequestMapping("/deleteBaseStation")
    public CommonResponse deleteBaseStation(String id){
        return baseStationService.deleteBaseStation(id);
    }
}
