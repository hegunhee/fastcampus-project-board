package com.fastcampus.projectboard.repository.querydsl;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.fastcampus.projectboard.domain.type.SearchType;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.Assert;

import java.util.List;

import static org.springframework.data.support.PageableExecutionUtils.getPage;

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
        Assert.notNull(getQuerydsl(),"getQuerydsl must not be null");

        QArticle article = QArticle.article;

        List<Article> contents = getQuerydsl().applyPagination(pageable, from(article).where(getPredicateBy(searchType, searchKeyword, article))).fetch();

        JPQLQuery<Long> count = from(article)
                .select(article.count())
                .where(getPredicateBy(searchType, searchKeyword, article));

        return getPage(contents,pageable,count::fetchOne);
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
