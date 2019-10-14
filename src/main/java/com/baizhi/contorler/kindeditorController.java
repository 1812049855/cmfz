package com.baizhi.contorler;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/kindeditor")
@Controller
public class kindeditorController {
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile img, HttpServletRequest request) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        String realPath = request.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        if(file.exists()){
            file.mkdirs();
        }
        String newFileName=new Date().getTime()+"_"+img.getOriginalFilename();
         img.transferTo(new File(realPath,newFileName));
         //获取当前网站的协议名
        String scheme = request.getScheme();
        // 获取当前主机的ip
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        //获取当前端口号
        int port = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();
        String url = scheme+"://"+hostAddress+":"+port+contextPath+"/img/"+newFileName;
        map.put("error",0);
        map.put("url",url);
        return  map;
    }
    @RequestMapping("allImages")
    @ResponseBody
    public Map<String,Object> all(HttpSession session, HttpServletRequest request) throws Exception {
        Map<String, Object> map1 = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        String realPath = session.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        String[] allimg = file.list();
        for (String s : allimg) {
            Map<String, Object> map = new HashMap<>();
            map.put("is_dir",false);
            map.put("has_file",false);
            //获取文件的大小
            File file1 = new File(realPath, s);
            long length = file1.length();
            map.put("filesize",length);
            map.put("dir_path","");
            map.put("is_photo",true);
            //获取文件的后缀名
            String s1 = s.substring(s.lastIndexOf(".") + 1);
            map.put("filetype",s1);
            map.put("filename",s);
            if(s.contains("_")){
                String s2 = s.split("_")[0];
                Long aLong = Long.valueOf(s2);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format1 = format.format(aLong);
                map.put("datetime",format1);
            }else{
                map.put("datetime",new Date());
            }

            list.add(map);
        }
        map1.put("moveup_dir_path","");
        map1.put("current_dir_path","");
        //获取当前网站的协议名  http
        String scheme = request.getScheme();
        //获取当前本机ip地址
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        //获取当前端口号
        int port = request.getServerPort();
        //获取项目名
        String path = request.getContextPath();
        String url=scheme+"://"+hostAddress+":"+port+path+"/img/";
        map1.put("current_url",url);
        int size = list.size();
        map1.put("total_count",size);
        map1.put("file_list",list);
        return map1;
    }
}
