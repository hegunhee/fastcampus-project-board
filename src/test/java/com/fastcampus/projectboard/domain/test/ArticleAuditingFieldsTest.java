package com.fastcampus.projectboard.domain.test;

import com.fastcampus.projectboard.domain.TestArticle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[AuditingField] AuditingFields equals hashcode 메서드 테스트")
public class ArticleAuditingFieldsTest {

    private LocalDateTime now;

    @BeforeEach
    void timeInit() {
        now = LocalDateTime.now();
    }

    @DisplayName("서로다른 5개의 객체 중복 비교")
    @Test
    void givenTestArticles_whenDistinct_thenNotDuplication() {
        // given & when
        List<TestArticle> articles = createDiffFiveArticles();
        int fiveArticlesSize = articles.size();
        Set<TestArticle> testArticles = new HashSet<>(articles);

        // then
        assertThat(fiveArticlesSize).isEqualTo(testArticles.size());
    }

    @DisplayName("모든 필드값이 같은 2개의 객체 중복 비교")
    @Test
    void givenTwoSameTestArticle_when_thenSameTestArticle() {
        // given
        TestArticle testArticle1 = createTestArticleWithOffset(1);
        TestArticle testArticle2 = createTestArticleWithOffset(1);

        // when & then
        assertThat(testArticle1).isEqualTo(testArticle2);
        assertThat(testArticle1.hashCode()).isEqualTo(testArticle2.hashCode());
    }

    @DisplayName("AuditingField만 다른 2개의 객체 중복 비교")
    @Test
    void givenTwoSameAuditingFieldTestArticle_when_thenDiffTestArticle() {
        // given
        TestArticle testArticle1 = createTestArticleWithOffsetAuditingFields(1);
        TestArticle testArticle2 = createTestArticleWithOffsetAuditingFields(2);
        // when

        // then
        assertThat(testArticle1).isNotEqualTo(testArticle2);
        assertThat(testArticle1.hashCode()).isNotEqualTo(testArticle2.hashCode());
    }

    private List<TestArticle> createDiffFiveArticles() {
        List<TestArticle> result = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            TestArticle testArticle = createTestArticleWithOffset(i);
            result.add(testArticle);
        }
        return result;
    }

    private TestArticle createTestArticleWithOffset(int number) {
        return TestArticle.of(now.plusMinutes(number), "createdName" + number, now.plusMinutes(modifiedTime + number), "modifiedName" + number, "title" + number, "content" + number, "hashtag" + number);
    }

    private TestArticle createTestArticleWithOffsetAuditingFields(int number) {
        return TestArticle.of(now.plusMinutes(number), "createdName" + number, now.plusMinutes(modifiedTime + number), "modifiedName" + number, "title", "content", "hashtag");
    }

    private final int modifiedTime = 3;
}
