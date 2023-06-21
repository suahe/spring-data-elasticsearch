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
public class WaterIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    private String name;

    private Double value;
}
