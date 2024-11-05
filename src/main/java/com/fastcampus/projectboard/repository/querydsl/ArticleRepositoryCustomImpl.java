package com.fastcampus.projectboard.repository.querydsl;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.fastcampus.projectboard.domain.type.SearchType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }


    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;

        return from(article)
                .distinct()
                .select(article.hashtag)
                .where(article.hashtag.isNotNull())
                .fetch();
    }

    @Override
    public Page<Article> findBySearchKeyword(SearchType searchType, String searchKeyword, Pageable pageable) {
        QArticle article = QArticle.article;

        Predicate searchTypePredicate = getPredicateBy(searchType, searchKeyword, article);

        List<Article> contents = from(article)
                .select(article)
                .where(searchTypePredicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = from(article)
                .select()
                .where(searchTypePredicate)
                .fetchCount();

        return new PageImpl<>(contents, pageable, count);
    }

    private Predicate getPredicateBy(SearchType searchType, String searchKeyword, QArticle article) {
        if (searchType == null || searchKeyword == null || searchKeyword.isBlank()) {
            return null;
        }

        return switch (searchType) {
            case TITLE		-> article.title.contains(searchKeyword);
            case CONTENT	-> article.content.contains(searchKeyword);
            case ID		-> article.userAccount.userId.contains(searchKeyword);
            case NICKNAME	-> article.userAccount.nickname.contains(searchKeyword);
            case HASHTAG	-> article.hashtag.eq(searchKeyword);
        };
    }
}
