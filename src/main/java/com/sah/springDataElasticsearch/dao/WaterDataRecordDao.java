package com.sah.springDataElasticsearch.dao;

import com.sah.springDataElasticsearch.entiry.WaterDataRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author suahe
 * @date 2022/4/6
 * @ApiNote 水质数据dao
 */
public interface WaterDataRecordDao extends ElasticsearchRepository<WaterDataRecord, String> {

    /**
     * 根据设备名称查询水质数据列表
     * @param equipmentName
     * @return
     */
    List<WaterDataRecord> findByEquipmentName(String equipmentName);
}
