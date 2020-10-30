package com.bishe.contorler;
import com.bishe.entity.Chapter;
import com.bishe.mapper.AlbumMapper;
import com.bishe.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Map;
@Controller
@RequestMapping("/chapter")
public class ChapterContorller {
    @Autowired
    ChapterService chapterService;
    @Autowired
    AlbumMapper albumMapper;
    @RequestMapping("queryByPage")
    @ResponseBody
    public Map<String,Object> queryPage(Integer page,Integer rows,String albumId){
        Map<String, Object> stringObjectMap = chapterService.queryPage(page, rows, albumId);
        return  stringObjectMap;
    }

    //增删改
    @RequestMapping("edit")
    @ResponseBody  //增删改
    public String edit(Chapter chapter, String oper,String albumId){
        chapter.setAlbumId(albumId);
        String edit = chapterService.edit(chapter,oper,albumId);
        return edit;
    }
    //图片
    @RequestMapping("upload")
    @ResponseBody  //单独修改图片的路径
    public void upload(MultipartFile filepath, HttpSession session,String banner){
        String realPath = session.getServletContext().getRealPath("/music"); //获取存放文件的文件夹
        File file = new File(realPath);
        if(!file.exists()){  //判断此文件夹是否存在
            file.mkdirs();
        }
        String filename = filepath.getOriginalFilename();//获取上传的文件名
        //文件的上传工作
        try {
            filepath.transferTo(new File(realPath,filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        chapterService.updatePath(banner,filename);//修改图片的路径
        //获取文件位置
        String realPath1 = session.getServletContext().getRealPath("/music/"+filename);
        File file1 = new File(realPath1);
        //获取文件大小  单位是字节 byte
        long length = file1.length();
        //获取音频时长 单位是秒      AudioFile类需要引入额外依赖 jaudiotagger
        AudioFile read = null;
        try {
            read = AudioFileIO.read(file1);
        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        AudioHeader header = read.      getAudioHeader();
        int trackLength = header.getTrackLength();
        //获取分钟数
        Integer m=trackLength/60;
        //获取秒秒数
        Integer s=trackLength%60;
        String time=m+"分"+s+"秒";
        //将文件大小强转成double类型
        double size=(double) length;
        //获取文件大小 单位是MB
        double ll=size/1024/1024;
        //取double小数点后两位  四舍五入
        BigDecimal bg = new BigDecimal(ll).setScale(2, RoundingMode.UP);
        String size1 = bg.toString()+"MB";
        chapterService.update(time,size1,banner);

    }
    //文件下载
    @ResponseBody
    @RequestMapping("/download")
    public  void  download(HttpSession session, String filename, HttpServletResponse response){
        try{
            String realPath = session.getServletContext().getRealPath("/music");
            File file = new File(realPath, filename);
            String encode = URLEncoder.encode(filename, "UTF-8");
            String replace = encode.replace("+", "%20");
            response.setHeader("content-disposition","attachment;filename="+replace);
            FileUtils.copyFile(file,response.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*
    *  String realPath = session.getServletContext().getRealPath("/audio");
        File file = new File(realPath, audioName);
        String s = audioName.split("_")[1];

        String encode = URLEncoder.encode(s,"UTF-8");
        String replace = encode.replace("+", "%20");
        System.out.println(replace);
        System.out.println(encode);
        response.setHeader("content-disposition","attachment;filename="+replace);
        FileUtils.copyFile(file,response.getOutputStream());
    * */
}
