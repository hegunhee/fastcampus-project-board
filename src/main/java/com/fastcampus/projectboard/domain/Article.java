package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
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


    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @Setter @Column(nullable = false) private String title;
    @Setter @Column(nullable = false, length = 10000) private String content;
    @Setter private String hashtag;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL,orphanRemoval = true)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    protected Article() {}

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

  
    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title,content, hashtag);
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
                    super.equals(that);
        }
    }

    @Override
    public int hashCode() {
        if (this.getId() != null) {
            return Objects.hash(getId());
        } else {
            return Objects.hash(getTitle(), getContent(), getHashtag()) + super.hashCode();
        }
    }
}
