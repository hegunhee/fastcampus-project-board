package com.fastcampus.projectboard.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Objects.hash 함수의 중첩 호출여부에 따라 값이 달라짐을 보여주는 테스트")
public class HashcodeMethodNestTest {

    private LocalDateTime now;

    @BeforeEach
    void initTime() {
        now = LocalDateTime.now();
    }

    @DisplayName("Objects.hash 함수를 다시 호출하면 hashcode값은 달라진다.")
    @Test
    void givenTestArticleHashcode_whenCalculateHashcodeAgain_thenDifferentHashcode() {
        // Given
        Article article = Article.of(null, "title", "content", "hashtag");
        AuditingFieldsReflectionHelper.setAuditingFieldsWithOffset(article, 3, now);
        int hashcode = article.hashCode();

        // When
        int againCalculateHashcode = Objects.hash(hashcode);

        // Then
        assertThat(againCalculateHashcode).isNotEqualTo(hashcode);
    }

}
