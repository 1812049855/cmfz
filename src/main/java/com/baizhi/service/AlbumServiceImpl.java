package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.mapper.AlbumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumMapper albumMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryPage(Integer page, Integer rows) {
        //查询总条数
        Integer count = albumMapper.count();
        //起始条数
        Integer start = (page-1)*rows;
        //计算总页数
        Integer pageCount =count%rows==0?count/rows:count/rows+1;
        List<Album> banners=albumMapper.queryPage(start,rows);
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("rows",banners);  //页面中要展示的内容
        map.put("records",count); //总条数
        map.put("page",page); //当前页
        map.put("total",pageCount);  //总页数
        return map;
    }
    //增删改
    @Override
    public String edit(String id, String title,String score, String cover, String author,String beam,Integer count, String content,String oper) {
        Album album = new Album();
        if(oper.equals("add")){
            String s = UUID.randomUUID().toString();
            album.setId(s);
            album.setPublishDate(new Date());
            album.setAuthor(author);
            album.setBeam(beam);
            album.setContent(content);
            album.setCover(cover);
            album.setCount(count);
            album.setScore(score);
            album.setTitle(title);
            System.out.println(album+"====================================");
            albumMapper.addAlbum(album);
            return s;
        }else if(oper.equals("edit")){
            album.setTitle(title);
            album.setId(id);
            album.setCount(count);
            album.setContent(content);
            album.setBeam(beam);
            album.setAuthor(author);
            albumMapper.update(album);
        }else if(oper.equals("del")){
            String[] split = id.split(",");
            albumMapper.deleteAlbum(split);
        }
        return null;
    }
    @Override
    public void updatePath(String id, String path) {
        albumMapper.updatePath(id, path);
    }
}
