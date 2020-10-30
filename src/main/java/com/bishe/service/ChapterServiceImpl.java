package com.bishe.service;
import com.bishe.entity.Chapter;
import com.bishe.mapper.AlbumMapper;
import com.bishe.mapper.ChapterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Transactional
@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterMapper chapterMapper;
    @Autowired
    AlbumMapper albumMapper;
    //分页查询
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryPage(Integer page, Integer rows,String albumId) {
        //查询总条数
        Integer count = chapterMapper.count(albumId);
        //起始条数
        Integer start = (page-1)*rows;
        System.out.println(start+"-------------------------------");
        //计算总页数
        Integer pageCount =count%rows==0?count/rows:count/rows+1;
        List<Chapter> banners=chapterMapper.queryPage(start,rows,albumId);
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("rows",banners);  //页面中要展示的内容
        map.put("records",count); //总条数
        map.put("page",page); //当前页
        map.put("total",pageCount);  //总页数
        return map;
    }
    @Override
    public String edit(Chapter chapter, String oper,String albumId) {
        if(oper.equals("add")){
            String s = UUID.randomUUID().toString();
            chapter.setId(s);
            chapter.setCreatData(new Date());
            Integer count = chapterMapper.count(albumId);
            chapterMapper.addChapter(chapter);
            albumMapper.updateCount(count+1,albumId);
            return s;
        }else if(oper.equals("edit")){
            Integer count = chapterMapper.count(albumId);
            chapterMapper.update(chapter);

        }else if(oper.equals("del")){
            String[] split = chapter.getId().split(",");

            chapterMapper.deleteChapter(split);
            Integer count = chapterMapper.count(albumId);
            albumMapper.updateCount(count,albumId);
        }
        return null;
    }
    @Override
    public void updatePath(String id, String path) {
        chapterMapper.updatePath(id, path);
    }
    @Override
    public void update(String time, String size,String id) {
        chapterMapper.updateDate(time, size,id);
    }
}
