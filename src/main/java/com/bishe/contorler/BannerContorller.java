package com.bishe.contorler;

import com.bishe.service.BannerService;
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
@RequestMapping("/banner")
public class BannerContorller {
    @Autowired
    BannerService bannerService;

    @RequestMapping("queryByPage")
    @ResponseBody
    public Map<String,Object> queryPage(Integer page,Integer rows){
        return  bannerService.queryPage(page, rows);
    }
    @RequestMapping("edit")
    @ResponseBody  //增删改
    public String edit(String id,String oper,String title,String status,String desc,String path){
        String edit = bannerService.edit(id,oper, title, status, desc, path);
        return edit;
    }


    @RequestMapping("upload")
    @ResponseBody  //单独修改图片的路径
    public void upload(MultipartFile path, HttpSession session, String banner) {
        String realPath = session.getServletContext().getRealPath("/img"); //获取存放文件的文件夹
        File file = new File(realPath);
        if(!file.exists()){  //判断此文件夹是否存在
            file.mkdirs();
        }
        String filename = path.getOriginalFilename();//获取上传的文件名
        String newFileName = new Date().getTime()+"_"+filename; //添加时间戳
        //文件的上传工作
        try {
            path.transferTo(new File(realPath,newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bannerService.updatePath(banner,newFileName);//修改图片的路径
    }
}
