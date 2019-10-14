package com.baizhi.service;

import com.baizhi.entity.Banner;
import com.baizhi.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerMapper bannerMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> queryPage(Integer page, Integer rows) {
        //查询总条数
        Integer count = bannerMapper.count();
        //起始条数
        Integer start = (page-1)*rows;
        //计算总页数
        Integer pageCount =count%rows==0?count/rows:count/rows+1;
        List<Banner>banners=bannerMapper.queryPage(start,rows);
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("rows",banners);  //页面中要展示的内容
        map.put("records",count); //总条数
        map.put("page",page); //当前页
        map.put("total",pageCount);  //总页数
        return map;
    }

    @Override
    public String edit(String id, String oper, String title, String status, String desc, String path) {
        Banner banner = new Banner();
        if(status!=null){
            Integer integer = Integer.valueOf(status);
            banner.setStatus(integer);
        }
        //添加数据
        if(oper.equals("add")){
            String replace = UUID.randomUUID().toString().replace("-", "");
            banner.setId(replace);
            banner.setCreateDate(new Date());
            banner.setDesc(desc);
            banner.setTitle(title);
            banner.setPath(path);
            System.out.println(banner+"************************");
            bannerMapper.addBanner(banner);
            return replace;
            //修改
        }else if(oper.equals("edit")){
            banner.setId(id);
            banner.setTitle(title);
            banner.setDesc(desc);
            System.out.println(banner+"==========================");
            bannerMapper.update(banner);
            //删除
        }else if(oper.equals("del")){
            String[] split = id.split(",");
            bannerMapper.deleteBanner(split);
        }
        return null;
    }

    @Override
    public void updatePath(String id, String path) {
        bannerMapper.updatePath(id,path);
    }

    @Override
    public List<Banner> queryAll() {
        return bannerMapper.queryAll();
    }

}
