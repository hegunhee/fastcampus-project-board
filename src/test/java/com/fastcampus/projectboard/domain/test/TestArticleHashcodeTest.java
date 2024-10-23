package com.fastcampus.projectboard.domain.test;

import com.fastcampus.projectboard.domain.TestArticle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Objects.hash 함수의 중첩 호출여부에 따라 값이 달라짐을 보여주는 테스트")
public class TestArticleHashcodeTest {

    @DisplayName("Objects.hash 함수를 다시 호출하면 hashcode값은 달라진다.")
    @Test
    void givenTestArticleHashcode_whenCalculateHashcodeAgain_thenDifferentHashcode() {
        // Given
        int hashcode = createTestArticle().hashCode();

        // When
        int againCalculateHashcode = Objects.hash(hashcode);

        // Then
        assertThat(againCalculateHashcode).isNotEqualTo(hashcode);
    }

    private TestArticle createTestArticle() {
        LocalDateTime now = LocalDateTime.now();
        return TestArticle.of(now,"gunhee",now,"gunhee","title","content","#hashtag");
    }
}
