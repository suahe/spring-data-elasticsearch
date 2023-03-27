package com.sah.springDataElasticsearch.service.impl;

import com.sah.springDataElasticsearch.dao.WaterDataRecordDao;
import com.sah.springDataElasticsearch.entiry.WaterDataRecord;
import com.sah.springDataElasticsearch.service.WaterDataRecordService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author suahe
 * @date 2022/4/6
 * @ApiNote 水质数据service
 */
@Service
public class WaterDataRecordServiceImpl implements WaterDataRecordService {

    @Autowired
    private WaterDataRecordDao waterDataRecordDao;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public List<WaterDataRecord> findAll() {
        Iterable<WaterDataRecord> iterable = waterDataRecordDao.findAll();
        List<WaterDataRecord> waterDataRecordList = new ArrayList<>();
        iterable.forEach(item -> waterDataRecordList.add(item));
        return waterDataRecordList;
    }

    @Override
    public WaterDataRecord findById(String id) {
        Optional<WaterDataRecord> optional = waterDataRecordDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }


    @Override
    public List<WaterDataRecord> findByEquipmentName(String equipmentName) {
        return waterDataRecordDao.findByEquipmentName(equipmentName);
    }

    @Override
    public List<WaterDataRecord> findByIndexKey(String key) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("waterIndexList.key", "EC"));
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(queryBuilder);
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        SearchHits<WaterDataRecord> search = elasticsearchRestTemplate.search(nativeSearchQuery, WaterDataRecord.class);
        List<WaterDataRecord> waterDataRecordList = new ArrayList<>();
        search.get().forEach(item -> waterDataRecordList.add(item.getContent()));
        return waterDataRecordList;
    }

    @Override
    public void findAggregation() {
        TermsAggregationBuilder idTeamAgg = AggregationBuilders.terms("id")
                .field("id.keyword");
        TermsAggregationBuilder equipmentIdTeamAgg = AggregationBuilders.terms("equipmentId").field("equipmentId.keyword");
        AvgAggregationBuilder avgAgg = AggregationBuilders.avg("avg_value").field("waterIndexList.value");
        equipmentIdTeamAgg.order(BucketOrder.aggregation("avg_value", false));
        SumAggregationBuilder sumValue = AggregationBuilders.sum("sum_value").field("waterIndexList.value");
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.addAggregation(idTeamAgg.subAggregation(equipmentIdTeamAgg.subAggregation(avgAgg).subAggregation(sumValue)));
        //nativeSearchQueryBuilder.withAggregations(teamAgg);
        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.build();
        SearchHits<Map> search = this.elasticsearchRestTemplate.search(nativeSearchQuery, Map.class, IndexCoordinates.of("water_data_record"));
        Aggregations aggregations = search.getAggregations();
        Map<String, Aggregation> aggMap = aggregations.asMap();
        Terms terms = (Terms) aggMap.get("id");
        Iterator<Terms.Bucket> teamBucketIt = (Iterator<Terms.Bucket>) terms.getBuckets().iterator();
        while (teamBucketIt.hasNext()) {
            Terms.Bucket buck = teamBucketIt.next();
            //球队名
            String id = buck.getKeyAsString();
            Terms equipmentIdTerms = (Terms) buck.getAggregations().asMap().get("equipmentId");
            Iterator<Terms.Bucket> equipmentIdIt = (Iterator<Terms.Bucket>) equipmentIdTerms.getBuckets().iterator();
            while (equipmentIdIt.hasNext()) {
                Terms.Bucket equipmentIdBuck = equipmentIdIt.next();
                equipmentIdBuck.getKeyAsString();
                //记录数
                long count = equipmentIdBuck.getDocCount();
                //得到所有子聚合
                Map subAggMap = equipmentIdBuck.getAggregations().asMap();
                //avg值获取方法
                double avg_age = ((ParsedAvg) subAggMap.get("avg_value")).getValue();
                //sum值获取方法
                double sum_age = ((ParsedSum) subAggMap.get("sum_value")).getValue();
                //...
                //max/min以此类推
            }
        }
    }

    @Override
    public void save(WaterDataRecord waterDataRecord) {
        waterDataRecordDao.save(waterDataRecord);
    }

    @Override
    public void delete(String id) {
        waterDataRecordDao.deleteById(id);
    }
}
