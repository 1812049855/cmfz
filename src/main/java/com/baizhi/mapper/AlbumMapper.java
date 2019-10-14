package com.baizhi.mapper;
import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper {
    //分页查询
    public List<Album> queryPage(@Param("start") Integer start, @Param("rows") Integer rows);
    //查询总条数
    public  Integer count();
    //添加数据
    public void addAlbum(Album album);  //添加数据
    //修改路径
    public void updatePath(@Param("id") String id,@Param("path") String path);
    //修改数据
    void update(Album album);
    //批量删除
    void deleteAlbum(String[] ids);
    //修改数量
    public  void updateCount(@Param("count") Integer count,@Param("id") String id);
}
