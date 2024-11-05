package com.fastcampus.projectboard.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.fastcampus.projectboard.domain.AuditingFieldsReflectionHelper.setAuditingFields;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[AuditingField] AuditingFields equals hashcode 메서드 테스트")
public class AuditingFieldsEqualsHashcodeTest {

    private LocalDateTime now;

    @BeforeEach
    void timeInit() {
        now = LocalDateTime.now();
    }

    @DisplayName("서로다른 5개의 객체 중복 비교")
    @Test
    void givenArticles_whenDistinct_thenNotDuplication() {
        // given & when
        List<Article> articles = createDiffFiveArticles();
        int fiveArticlesSize = articles.size();
        Set<Article> removeDuplicationArticle = new HashSet<>(articles);

        // then
        assertThat(fiveArticlesSize).isEqualTo(removeDuplicationArticle.size());
    }

    @DisplayName("모든 필드값이 같은 2개의 객체 중복 비교")
    @Test
    void givenTwoSameArticle_when_thenSameArticle() {
        // given
        Article article1 = createArticleWithOffset(1);
        Article article2 = createArticleWithOffset(1);

        // when & then
        assertThat(article1).isEqualTo(article2);
        assertThat(article1.hashCode()).isEqualTo(article2.hashCode());
    }

    @DisplayName("AuditingField만 다른 2개의 객체 중복 비교")
    @Test
    void givenTwoSameAuditingFieldArticle_when_thenDifferentArticle() {
        // given
        Article article1 = createArticleWithOffsetAuditingFields(1);
        Article article2 = createArticleWithOffsetAuditingFields(2);
        // when

        // then
        assertThat(article1).isNotEqualTo(article2);
        assertThat(article1.hashCode()).isNotEqualTo(article2.hashCode());
    }

    List<Article> createDiffFiveArticles() {
        List<Article> result = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Article article = createArticleWithOffset(i);
            result.add(article);
        }
        return result;
    }

    private Article createArticleWithOffset(int number) {
        Article result = Article.of(null, "title" + number, "content" + number, "hashcode" + number);
        setAuditingFields(result,number,now);
        return result;
    }

    private Article createArticleWithOffsetAuditingFields(int number) {
        Article result = Article.of(null, "title", "content", "hashcode");
        setAuditingFields(result,number,now);
        return result;
    }
}
