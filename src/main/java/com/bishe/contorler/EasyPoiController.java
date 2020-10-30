package com.bishe.contorler;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.bishe.entity.Banner;
import com.bishe.service.BannerService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/easypoi")
public class EasyPoiController {
    @Autowired
    BannerService bannerService;

    @RequestMapping("/download")
    public void  download(HttpServletResponse response) throws IOException {
        List<Banner> banners = bannerService.queryAll();
        for (Banner banner : banners) {
            System.out.println(banner.getPath());
            banner.setPath("D:\\idea_work\\框架阶段代码\\after_cmfz\\src\\main\\webapp\\img\\"+banner.getPath());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("持明法洲轮播图","表1"),
                Banner .class, banners);

        String encode = URLEncoder.encode("banner.xls", "UTF-8");
        response.setHeader("content-disposition","attachment;filename="+encode);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
    }
}
