package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String title;
    @Column(nullable = false, length = 10000) private String content;
    private String hashtag;

    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;
    @CreatedBy @Column(nullable = false,length = 100) private String createdBy;
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;
    @LastModifiedBy @Column(nullable = false,length = 100) private String modifiedBy;

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
            return Objects.equals(this.getTitle(), that.getTitle()) &&
                    Objects.equals(this.getContent(), that.getContent()) &&
                    Objects.equals(this.getHashtag(), that.getHashtag()) &&
                    Objects.equals(this.getCreatedAt(), that.getCreatedAt()) &&
                    Objects.equals(this.getCreatedBy(), that.getCreatedBy()) &&
                    Objects.equals(this.getModifiedAt(), that.getModifiedAt()) &&
                    Objects.equals(this.getModifiedBy(), that.getModifiedBy());
        }
    }

    @Override
    public int hashCode() {
        if(this.getId() != null) {
            return Objects.hash(getId());
        }else {
            return Objects.hash(getTitle(), getContent(), getHashtag(), getCreatedAt(), getCreatedBy(), getModifiedAt(), getModifiedBy());
        }
    }
}
