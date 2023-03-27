package com.sah.springDataElasticsearch.entiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author suahe
 * @date 2022/4/6
 * @ApiNote 水质数据
 */
@Data
@Document(indexName = "water_data_record", shards = 1, replicas = 0)
public class WaterDataRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(type = FieldType.Keyword, fielddata = true)
    private String equipmentId;

    @Field(type = FieldType.Keyword, fielddata = true)
    private String equipmentName;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date saveTime;

    @Field(type = FieldType.Nested)
    private List<WaterIndex> waterIndexList;
}
