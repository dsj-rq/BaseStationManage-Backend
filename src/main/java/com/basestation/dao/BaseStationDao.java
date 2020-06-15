package com.basestation.dao;

import com.basestation.bean.BaseStationBean;
import com.basestation.bean.MapBounds;
import com.basestation.bean.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by me on 2020/4/30.
 */
@Repository
public class BaseStationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 分页查询基站信息
     * @param keyWord 查询关键词
     * @param page
     * @return
     */
    public List<BaseStationBean> listStationByPage(String keyWord,Page page){
        StringBuilder sqlBuilder = new StringBuilder("select * from base_station_info where 1=1 ");
        if(StringUtils.isNotBlank(keyWord)) {
            sqlBuilder.append(" and addr like '%").append(keyWord).append("%' ");
            page.setTotalRows(jdbcTemplate.
                    queryForObject(String.format("select count(*) from(%s) a",sqlBuilder.toString()),Integer.class));
        } else {
            page.setTotalRows(jdbcTemplate.queryForObject("select count(*) from base_station_info",Integer.class));
        }
        sqlBuilder.append(" limit ?,?");
        return jdbcTemplate.query(sqlBuilder.toString(),new Object[]{(page.getPageNo()-1)*page.getPageSize(),page.getPageSize()},new BaseStationBean());
    }

    /**
     * 统计基站总数
     * @return
     */
    public Integer countAllBaseStation(){
        return jdbcTemplate.queryForObject("select count(*) from base_station_info)",Integer.class);
    }

    /**
     * 根据省统计基站数据
     * @return
     */
    public List<Map<String,Object>> statisticByProvince(){
        String sql = "select count(*) cnt,province from base_station_info group by province";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 根据类型统计基站数据
     * @return
     */
    public List<Map<String,Object>> statisticByType(){
        String sql = "select count(*) cnt,type from base_station_info group by type";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 根据覆盖范围统计基站数据
     * @return
     */
    public List<Map<String,Object>> statisticByPrecision(){
        String sql = "select count(*) cnt,`precision` from base_station_info group by `precision`";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 根据运营商类型统计基站数据
     * @return
     */
    public List<Map<String,Object>> statisticByMnc(){
        String sql = "select count(*) cnt,mnc from base_station_info group by mnc";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 根据更新日期统计统计基站数据
     * @return
     */
    public List<Map<String,Object>> statisticByDate(){
        String sql = "select count(*) cnt,update_date from base_station_info group by update_date";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 更新基站指定属性
     * @return
     */
    public boolean updateBaseStationInfo(String id,String key,String val) {
        String sql = "update base_station_info set ?=? where id=?";
        return jdbcTemplate.update(sql,key,val,id) > 0;
    }

    /**
     * 插入一条基站数据
     * @param baseStation
     * @return
     */
    public boolean insertBaseStation(BaseStationBean baseStation) {
        String insertSql = "insert into base_station_info(MCC,MNC,LAC,CELL,LNG,LAT,O_LNG,O_LAT,`PRECISION`,ADDR,PROVINCE,TYPE,UPDATE_DATE)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(insertSql,
                baseStation.getMcc(),
                baseStation.getMnc(),
                baseStation.getLac(),
                baseStation.getCell(),
                baseStation.getLng(),
                baseStation.getLat(),
                baseStation.getOlng(),
                baseStation.getOlat(),
                baseStation.getPrecision(),
                baseStation.getAddress(),
                baseStation.getProvince(),
                baseStation.getType(),
                baseStation.getUpdateDate());

        return false;
    }

    /**
     * 批量插入基站数据
     * @param list
     * @return
     */
    public void batchInsertBaseStation(List<BaseStationBean> list) {
        String insertSql = "insert into base_station_info(MCC,MNC,LAC,CELL,LNG,LAT,O_LNG,O_LAT,`PRECISION`,ADDR,PROVINCE,TYPE,UPDATE_DATE)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        List<Object[]> paramList = new ArrayList<>(list.size());
        list.forEach(baseStation->{
            Object[] params = new Object[]{
                    baseStation.getMcc(),
                    baseStation.getMnc(),
                    baseStation.getLac(),
                    baseStation.getCell(),
                    baseStation.getLng(),
                    baseStation.getLat(),
                    baseStation.getOlng(),
                    baseStation.getOlat(),
                    baseStation.getPrecision(),
                    baseStation.getAddress(),
                    baseStation.getProvince(),
                    baseStation.getType(),
                    baseStation.getUpdateDate()
            };
            paramList.add(params);
        });
        jdbcTemplate.batchUpdate(insertSql, paramList);
    }

    /**
     * 删除指定id的基站数据
     * @param id
     * @return
     */
    public boolean deleteBaseStation(String id){
        String sql = "delete from base_station_info where id = ?";
        return jdbcTemplate.update(sql,id)>0;
    }

    public List<BaseStationBean> listStationByBounds(MapBounds bounds){
        String sql = "select * from base_station_info where O_LAT between ? and ? and O_LNG between ? and ? limit 1000";
        return jdbcTemplate.query(sql,new Object[]{bounds.getMinLat(),bounds.getMaxLat(),bounds.getMinLon(),bounds.getMaxLon()},new BaseStationBean());
    }
}
