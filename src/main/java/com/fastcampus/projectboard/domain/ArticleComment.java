package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class ArticleComment extends AuditingFields{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) private Article article;
    @ManyToOne(optional = false) private UserAccount userAccount; // 유저 정보 (ID)

    @Column(nullable = false,length = 500) private String content;

    protected ArticleComment() {}

    private ArticleComment(Article article, UserAccount userAccount, String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.content = content;
    }

    public static ArticleComment of(Article article, UserAccount userAccount, String content) {
        return new ArticleComment(article, userAccount, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;

        if (this.getId() != null) { // id가 영속화가 된 경우
            return Objects.equals(this.getId(), that.getId());
        } else { // 영속화하지 않은 데이터도 처리 하기위해서 분기 처리
            return Objects.equals(this.getArticle(), that.getArticle()) &&
                    Objects.equals(this.getContent(), that.getContent()) &&
                    super.equals(that);
        }
    }

    @Override
    public int hashCode() {
        if (this.getId() != null) {
            return Objects.hash(getId());
        } else {
            return Objects.hash(getArticle(), getContent()) + super.hashCode();
        }
    }
}
