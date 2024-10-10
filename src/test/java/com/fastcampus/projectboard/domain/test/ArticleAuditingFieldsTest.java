package com.fastcampus.projectboard.domain.test;

import com.fastcampus.projectboard.domain.TestArticle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        // given
        List<TestArticle> testArticles = getDiffFiveArticles();

        // when
        List<TestArticle> removeDuplicateData = testArticles.stream().distinct().toList();

        // then
        assertThat(removeDuplicateData.size()).isEqualTo(testArticles.size());
    }

    @DisplayName("모든 필드값이 같은 2개의 객체 중복 비교")
    @Test
    void givenTwoSameTestArticle_when_thenSameTestArticle() {
        // given
        TestArticle testArticle1 = getTestArticleDiff(1);
        TestArticle testArticle2 = getTestArticleDiff(1);

        // when & then
        assertThat(testArticle1).isEqualTo(testArticle2);
        assertThat(testArticle1.hashCode()).isEqualTo(testArticle2.hashCode());
    }

    @DisplayName("AuditingField만 다른 2개의 객체 중복 비교")
    @Test
    void givenTwoSameAuditingFieldTestArticle_when_thenDiffTestArticle() {
        // given
        TestArticle testArticle1 = getTestArticleAuditingFieldDiff(1);
        TestArticle testArticle2 = getTestArticleAuditingFieldDiff(2);
        // when

        // then
        assertThat(testArticle1).isNotEqualTo(testArticle2);
        assertThat(testArticle1.hashCode()).isNotEqualTo(testArticle2.hashCode());
    }

    @DisplayName("내부적으로 hashcode를 두번 호출하는 메서드 테스트")
    @Test
    void givenTestArticle_whenCalculateHashcode_thenDiffHashcode() {
        // given
        TestArticle testArticle1 = getTestArticleDiff(1);

        // when
        int hashcode = testArticle1.hashCode();
        int nestedHashcode = testArticle1.nestedHashcode();

        // then
        System.out.println("hashcode = " + hashcode);
        System.out.println("nestedHashcode = " + nestedHashcode);
        assertThat(hashcode).isNotEqualTo(nestedHashcode);
    }

    private List<TestArticle> getDiffFiveArticles() {
        List<TestArticle> result = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            TestArticle testArticle = getTestArticleDiff(i);
            result.add(testArticle);
        }
        return result;
    }

    private TestArticle getTestArticleDiff(int number) {
        return TestArticle.of(now.plusMinutes(number), "createdName" + number, now.plusMinutes(modifiedTime + number), "modifiedName" + number, "title" + number, "content" + number, "hashtag" + number);
    }

    private TestArticle getTestArticleAuditingFieldDiff(int number) {
        return TestArticle.of(now.plusMinutes(number), "createdName" + number, now.plusMinutes(modifiedTime + number), "modifiedName" + number, "title", "content", "hashtag");
    }

    private final int modifiedTime = 3;
}
