package com.bishe.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors
public class Banner {
    @Excel(name = "编号")
    private  String id;
    @Excel(name = "图片路径",type = 2,width = 30,height = 20,needMerge = true)
    private  String path;
    @Excel(name = "标题")
    private String title;
    @Excel(name = "状态")
    private  Integer status;
    @Excel(name = "简介")
    private  String desc;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建日期")
    private Date createDate;

}
