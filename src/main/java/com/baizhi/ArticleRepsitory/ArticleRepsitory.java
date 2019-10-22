package com.baizhi.ArticleRepsitory;

import com.baizhi.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface ArticleRepsitory extends ElasticsearchCrudRepository<Article,String> {
}
