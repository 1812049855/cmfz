package com.baizhi.mapper;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterMapper {
    //分页查询
    public List<Chapter> queryPage(@Param("start") Integer start, @Param("rows") Integer rows,@Param("albumId") String albumId);
    //查询总条数
    public  Integer count(String albumid);
    //添加数据
    public void addChapter(Chapter chapter);
    //修改路径
    public void updatePath(@Param("id") String id,@Param("filepath") String path);
    //修改数据
    void update(Chapter chapter);
    //修改时间大小
    public void  updateDate(@Param("time") String time,@Param("size") String size,@Param("id") String id);
    //批量删除
    void deleteChapter(String[] ids);


}
