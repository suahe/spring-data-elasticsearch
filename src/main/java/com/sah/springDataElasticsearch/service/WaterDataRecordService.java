package com.sah.springDataElasticsearch.service;

import com.sah.springDataElasticsearch.entiry.WaterDataRecord;

import java.util.List;

/**
 * @author suahe
 * @date 2022/4/6
 * @ApiNote 水质数据service
 */
public interface WaterDataRecordService {
    /**
     * 查询所有
     * @return
     */
    List<WaterDataRecord> findAll();

    /**
     * 根据id查询
     * @param id
     */
    WaterDataRecord findById(String id);

    /**
     * 根据名称搜索
     * @param equipmentName
     * @return
     */
    List<WaterDataRecord> findByEquipmentName(String equipmentName);

    /**
     * 根据水质指标查询
     * @param key
     * @return
     */
    List<WaterDataRecord> findByIndexKey(String key);

    /**
     * 聚合查询
     */
    void findAggregation();

    /**
     * 保存
     * @param waterDataRecord
     */
    void save(WaterDataRecord waterDataRecord);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);
}
