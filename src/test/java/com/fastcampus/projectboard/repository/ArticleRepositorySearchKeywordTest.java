package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.type.SearchType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("[DB] 키워드별 검색이 정상 작동하는지 확인하는 테스트")
@DataJpaTest
public class ArticleRepositorySearchKeywordTest {

    private final ArticleRepository articleRepository;

    public ArticleRepositorySearchKeywordTest(@Autowired ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @DisplayName("검색 키워드가 없을 때는 findAll()과 동작방식과 결과값이 같음")
    @Test
    void given_whenGetNoSearchKeywordArticles_thenResultSameFindAll() {
        // Given
        Pageable pageable = PageRequest.ofSize(20);
        Page<Article> expectedArticles = articleRepository.findAll(pageable);

        // When
        Page<Article> articles = articleRepository.findBySearchKeyword(null, null, pageable);

        // Then
        assertThat(articles.getSize()).isEqualTo(expectedArticles.getSize());
        assertThat(articles.getContent()).isEqualTo(expectedArticles.getContent());
    }

    @DisplayName("키워드 별 검색의 경우 검색된 결과는 모두 키워드와 일치해야함")
    @MethodSource
    @ParameterizedTest(name = "[{0}] 현재 키워드: {1}")
    void givenSearchTypeAndSearchKeyword_whenGetSearchKeywordArticles_thenResultContainsKeyword(SearchType searchType, String searchKeyword) {
        // Given
        Pageable pageable = PageRequest.ofSize(20);

        // When
        Page<Article> articles = articleRepository.findBySearchKeyword(searchType, searchKeyword, pageable);

        // Then
        switch (searchType) {
            case TITLE -> Assertions.assertTrue(articles.stream().allMatch(article -> article.getTitle().contains(searchKeyword)));
            case CONTENT -> Assertions.assertTrue(articles.stream().allMatch(article -> article.getContent().contains(searchKeyword)));
            case ID -> Assertions.assertTrue(articles.stream().allMatch(article -> article.getUserAccount().getUserId().contains(searchKeyword)));
            case NICKNAME -> Assertions.assertTrue(articles.stream().allMatch(article -> article.getUserAccount().getNickname().contains(searchKeyword)));
            case HASHTAG -> Assertions.assertTrue(articles.stream().allMatch(article -> article.getHashtag().equals(searchKeyword)));
        }
    }

    static Stream<Arguments> givenSearchTypeAndSearchKeyword_whenGetSearchKeywordArticles_thenResultContainsKeyword() {
        return Stream.of(
                arguments(SearchType.TITLE, "Quisque"),
                arguments(SearchType.CONTENT, "Vestibulum"),
                arguments(SearchType.ID, "hegunhee2"),
                arguments(SearchType.NICKNAME, "Hegunhee"),
                arguments(SearchType.HASHTAG, "#green")
        );
    }

}
