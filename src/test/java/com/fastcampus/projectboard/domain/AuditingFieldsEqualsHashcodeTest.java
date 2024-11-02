package com.fastcampus.projectboard.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DisplayName("[AuditingField] AuditingFields equals hashcode 메서드 테스트")
public class AuditingFieldsEqualsHashcodeTest {

    private LocalDateTime now;

    private final UserAccount dummyUserAccount = null;

    @BeforeEach
    void timeInit() {
        now = LocalDateTime.now();
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
        Article result = Article.of(dummyUserAccount, "title" + number, "content" + number, "hashcode" + number);
        initEntityAuditingFieldsWithOffset(result, number);
        return result;
    }

    private Article createArticleWithOffsetAuditingFields(int number) {
        Article result = Article.of(dummyUserAccount, "title", "content", "hashcode");
        initEntityAuditingFieldsWithOffset(result, number);
        return result;
    }

    void initEntityAuditingFieldsWithOffset(Article entity, int number) {
        ReflectionTestUtils.setField(entity, "createdAt", now.plusMinutes(number));
        ReflectionTestUtils.setField(entity, "createdBy", "createdBy" + number);
        int modifiedAddMinute = 3;
        ReflectionTestUtils.setField(entity, "modifiedAt", now.plusMinutes(modifiedAddMinute + number));
        ReflectionTestUtils.setField(entity, "modifiedBy", "modifiedBy" + number);
    }
}
