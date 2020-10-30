package com.bishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class Album {
    private String id;
    private String title;//标题
    private  String cover;//封面路径
    private String score;//评分
    private String author;//作者
    private  String beam;//播音
    private  Integer count;//集数
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;//发布时间
    private String content;//简介
}
