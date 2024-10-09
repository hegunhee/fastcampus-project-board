package com.fastcampus.projectboard.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class TestArticle extends AuditingFields {

    private final String title;
    private final String content;
    private final String hashtag;


    private TestArticle(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, String title, String content, String hashtag) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;

        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static TestArticle of(LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy, String title, String content, String hashtag) {
        return new TestArticle(createdAt, createdBy, modifiedAt, modifiedBy, title, content, hashtag);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestArticle that)) return false;

        return Objects.equals(this.getTitle(), that.getTitle()) &&
                Objects.equals(this.getContent(), that.getContent()) &&
                Objects.equals(this.getHashtag(), that.getHashtag()) &&
                super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getContent(), getHashtag()) + super.hashCode();
    }

    public int nestedHashcode() {
        return Objects.hash(getTitle(), getContent(), getHashtag(), super.hashCode());
    }

    @Override
    public String toString() {
        return "TestArticle{" +
                "auditingField{" +
                "createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedAt=" + modifiedAt +
                ", modifiedBy='" + modifiedBy + '\'' +
                "}" +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", hashcode='" + hashtag + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getHashtag() {
        return hashtag;
    }
}
