package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String title;
    @Column(nullable = false, length = 10000) private String content;
    private String hashtag;

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL,orphanRemoval = true)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title,content, hashtag);
    }

    public void updateHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;

        if(this.getId() != null) { // id가 영속화가 된 경우
            return Objects.equals(this.getId(),that.getId());
        } else { // 영속화하지 않은 데이터도 처리 하기위해서 분기 처리
            AuditingFields thisAuditingField = this;
            return Objects.equals(this.getTitle(), that.getTitle()) &&
                    Objects.equals(this.getContent(), that.getContent()) &&
                    Objects.equals(this.getHashtag(), that.getHashtag()) &&
                    thisAuditingField.equals(that);
        }
    }

    @Override
    public int hashCode() {
        if(this.getId() != null) {
            return Objects.hash(getId());
        }else {
            return Objects.hash(getTitle(), getContent(), getHashtag(), super.hashCode());
        }
    }
}
