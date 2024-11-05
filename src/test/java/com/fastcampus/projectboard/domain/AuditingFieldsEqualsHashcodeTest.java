package com.fastcampus.projectboard.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.fastcampus.projectboard.domain.AuditingFieldsReflectionHelper.setAuditingFields;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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
        Set<Article> removeDuplicationArticle = new HashSet<>(articles);

        // then
        assertThat(articles.size()).isEqualTo(removeDuplicationArticle.size());
    }

    @DisplayName("리스트에 모든 객체가 중복일 때 객체 중복 비교")
    @Test
    void givenSameOffsetArticles_whenDistinct_thenRemoveDuplicate() {
        // Given & When
        int offset = 5;
        List<Article> articles = List.of(createArticleWithOffset(offset), createArticleWithOffset(offset), createArticleWithOffset(offset));
        Set<Article> removeDuplicationArticles = new HashSet<>(articles);

        // Then
        assertThat(articles.size()).isNotEqualTo(removeDuplicationArticles.size());
        assertThat(1).isEqualTo(removeDuplicationArticles.size());
    }

    @DisplayName("모든 필드값이 같은 2개의 객체 중복 비교")
    @MethodSource
    @ParameterizedTest(name = "현재 offset {0}")
    void givenTwoSameArticle_when_thenSameArticle(int offset) {
        // given
        Article article1 = createArticleWithOffset(offset);
        Article article2 = createArticleWithOffset(offset);

        // when & then
        assertThat(article1).isEqualTo(article2);
        assertThat(article1.hashCode()).isEqualTo(article2.hashCode());
    }

    static Stream<Arguments> givenTwoSameArticle_when_thenSameArticle() {
        return Stream.of(
                arguments(1),
                arguments(2),
                arguments(3),
                arguments(4),
                arguments(5)
        );
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
        Article result = Article.of(null, "title", "content", "hashcode");
        setAuditingFields(result, number, now);
        return result;
    }
}
