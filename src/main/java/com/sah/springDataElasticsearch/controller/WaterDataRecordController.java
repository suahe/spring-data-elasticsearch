package com.sah.springDataElasticsearch.controller;

import com.sah.springDataElasticsearch.entiry.WaterDataRecord;
import com.sah.springDataElasticsearch.service.WaterDataRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author suahe
 * @date 2022/4/6
 * @ApiNote 水质数据controller
 */
@RestController
@RequestMapping("/waterDataRecord")
public class WaterDataRecordController {

    @Autowired
    private WaterDataRecordService waterDataRecordService;

    @GetMapping("findAll")
    public List<WaterDataRecord> findAll() {
        return waterDataRecordService.findAll();
    }

    @GetMapping("findById")
    public WaterDataRecord findById(@RequestParam("id") String id) {
        return waterDataRecordService.findById(id);
    }

    @GetMapping("findByEquipmentName")
    public List<WaterDataRecord> findByEquipmentName(@RequestParam("equipmentName") String equipmentName) {
        return waterDataRecordService.findByEquipmentName(equipmentName);
    }

    @GetMapping("findByIndexKey")
    public List<WaterDataRecord> findByIndexKey(@RequestParam("key") String key) {
        return waterDataRecordService.findByIndexKey(key);
    }

    @GetMapping("stateEquipmentIndex")
    public List<WaterDataRecord> stateEquipmentIndex(@RequestParam("equipmentId") String equipmentId,
                                                     @RequestParam("key") String key) {
        return waterDataRecordService.stateEquipmentIndex(equipmentId, key);
    }

    @GetMapping("findAggregation")
    public void findAggregation() {
        waterDataRecordService.findAggregation();
    }

    @PostMapping("save")
    public void save(@RequestBody WaterDataRecord waterDataRecord) {
        waterDataRecordService.save(waterDataRecord);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam("id") String id) {
        waterDataRecordService.delete(id);
    }
}
