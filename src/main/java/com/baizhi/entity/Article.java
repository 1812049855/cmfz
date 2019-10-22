package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors
@Document(indexName = "cmfz",type = "article")
public class Article {
    @Id
    private  String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private  String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private  String content;
    @Field(type = FieldType.Date)
    private Date  createDate;
    @Field(type = FieldType.Keyword)
    private String  author;
    @Field(type = FieldType.Keyword)
    private  String status;
}
