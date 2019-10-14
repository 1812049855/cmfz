package com.baizhi.contorler;

import com.baizhi.service.AlbumService;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/album")
public class AlbumContorller {
    @Autowired
    AlbumService albumService;
    //分页查询
    @RequestMapping("queryByPage")
    @ResponseBody
    public Map<String,Object> queryPage(Integer page,Integer rows){
        return  albumService.queryPage(page, rows);
    }
    //增删改
    @RequestMapping("edit")
    @ResponseBody  //增删改
    public String edit(String id, String title,String score, String cover, String author,String beam,Integer count, String content,String oper){
        String edit = albumService.edit(id, title, score, cover, author, beam, count, content, oper);
        return edit;
    }

//图片
    @RequestMapping("upload")
    @ResponseBody  //单独修改图片的路径
    public void upload(MultipartFile cover, HttpSession session, String banner) {
        String realPath = session.getServletContext().getRealPath("/img"); //获取存放文件的文件夹
        File file = new File(realPath);
        if(!file.exists()){  //判断此文件夹是否存在
            file.mkdirs();
        }
        String filename = cover.getOriginalFilename();//获取上传的文件名
        String newFileName = new Date().getTime()+"_"+filename; //添加时间戳
        //文件的上传工作
        try {
            cover.transferTo(new File(realPath,newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        albumService.updatePath(banner,newFileName);//修改图片的路径
    }
}
