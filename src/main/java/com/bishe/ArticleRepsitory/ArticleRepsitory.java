package com.bishe.ArticleRepsitory;

import com.bishe.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface ArticleRepsitory extends ElasticsearchCrudRepository<Article,String> {
}
