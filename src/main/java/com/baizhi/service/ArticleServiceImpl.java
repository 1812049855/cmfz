package com.baizhi.service;

import com.baizhi.entity.Article;
import com.baizhi.mapper.ArticleMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //分页
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //开始条数
        Integer start = (page-1)*rows;
        //总条数
        Integer count = articleMapper.count();
        //总页数
        Integer total = count%rows==0?count/rows:count/rows+1;
        List<Article> articles = articleMapper.queryAll(start, rows);
        map.put("total",total);//总页数
        map.put("rows",articles);//页面中展示的数据
        map.put("page",page);//当前页数
        map.put("records",count);//总条数
        return map;
    }

    //修改
    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    //添加
    @Override
    public void save(Article article) {
        articleMapper.save(article);
    }

    @Override
    public List<Article> queryByEs(String val) {
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style = 'color:red'>");
        field.postTags("/<span>");
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withIndices("cmfz")
                .withTypes("article")
                .withQuery(QueryBuilders.queryStringQuery(val).analyzer("ik_max_word"))
                .withHighlightFields(field)
                .build();
        AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(build, Article.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Article> list = new ArrayList<Article>();

                SearchHit[] hits = searchResponse.getHits().getHits();
                for (SearchHit hit : hits) {
                    Article article = new Article();
                    article.setAuthor(hit.getSourceAsMap().get("author").toString());
                    article.setStatus(hit.getSourceAsMap().get("status").toString());
                    article.setCreateDate(new Date());
                    article.setContent(hit.getSourceAsMap().get("content").toString());
                    article.setTitle(hit.getSourceAsMap().get("title").toString());
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if (highlightFields.get("title") != null) {
                        String title = highlightFields.get("title").getFragments()[0].toString();
                        article.setTitle(title);
                    }
                    if (highlightFields.get("content") != null) {
                        String content = highlightFields.get("content").getFragments()[0].toString();
                        article.setContent(content);
                    }
                    list.add(article);
                }

                return new AggregatedPageImpl<T>((List<T>) list);
            }
        });


        return  articles.getContent();
    }
}
