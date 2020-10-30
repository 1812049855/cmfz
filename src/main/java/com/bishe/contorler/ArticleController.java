package com.bishe.contorler;

import com.bishe.entity.Article;
import com.bishe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping("queryAll")
    @ResponseBody
    public Map<String,Object> queryAll(Integer page,Integer rows){
        Map<String, Object> map = articleService.queryAll(page, rows);
        return map;
    }
    @RequestMapping("/add")
    @ResponseBody
    public void save(Article article){
        String s = UUID.randomUUID().toString();
        article.setId(s);
        if(article.getStatus().equals("展示")){
            article.setStatus("1");
        }else {
            article.setStatus("2");
        }
        article.setCreateDate(new Date());
         articleService.save(article);
    }
    @RequestMapping("/update")
    @ResponseBody
    public  void  update(Article article){
        if(article.getStatus().equals("展示")){
            article.setStatus("1");
        }else {
            article.setStatus("2");
        }
        article.setCreateDate(new Date());
        articleService.update(article);
    }
    @RequestMapping("es")
    @ResponseBody
    public List<Article> queryByes(String esvalue){
        System.out.println(esvalue+"========");
        List<Article> articles = articleService.queryByEs(esvalue);
        for (Article article : articles) {
            System.out.println(article+"===========================================");
        }
        return articles;
    }

}
