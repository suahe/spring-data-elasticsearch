package com.sah.springDataElasticsearch.entiry;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author suahe
 * @date 2022/4/6
 * @ApiNote 水质指标
 */
@Data
//@Document(indexName = "water_index")
public class WaterIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field( type = FieldType.Text)
    private String key;

    @Field( type = FieldType.Text)
    private String name;

    @Field( type = FieldType.Double)
    private Double value;
}
