package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.event.EventListener;
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
        @Index(columnList = "userLogin"),
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false) private String userLogin;
    @Setter @Column(nullable = false) private String userPassword;

    @Setter private String email;
    @Setter private String nickname;
    @Setter private String memo;

    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;
    @CreatedBy @Column(nullable = false) private String createdBy;
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;
    @LastModifiedBy @Column(nullable = false) private String modifiedBy;


    protected UserAccount() {}

    private UserAccount(String userLogin, String userPassword, String email, String nickname, String memo) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }
//
//    private UserAccount(String userLogin, String userPassword, String email, String nickname, String memo,LocalDateTime createdAt) {
//        this.userLogin = userLogin;
//        this.userPassword = userPassword;
//        this.email = email;
//        this.nickname = nickname;
//        this.memo = memo;
//        this.createdAt = createdAt;
//    }

    public static UserAccount of(String userLogin, String userPassword, String email, String nickname, String memo) {
        return new UserAccount(userLogin, userPassword, email, nickname, memo);
    }
//
//    public static UserAccount createUserAccountWithCreatedAt(String userLogin, String userPassword, String email, String nickname, String memo,LocalDateTime createdAt) {
//        return new UserAccount(userLogin, userPassword, email, nickname, memo,createdAt);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;

        if (this.getId() == null) {
            return Objects.equals(this.getUserLogin(), that.getUserLogin()) &&
                    Objects.equals(this.getUserPassword(), that.getUserPassword()) &&
                    Objects.equals(this.getEmail(), that.getEmail()) &&
                    Objects.equals(this.getNickname(), that.getNickname()) &&
                    Objects.equals(this.getMemo(), that.getMemo()) &&
                    Objects.equals(this.getCreatedAt(), that.getCreatedAt()) &&
                    Objects.equals(this.getCreatedBy(), that.getCreatedBy()) &&
                    Objects.equals(this.getModifiedAt(), that.getModifiedAt()) &&
                    Objects.equals(this.getModifiedBy(), that.getModifiedBy());
        }

        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return Objects.hash(getUserLogin(), getUserPassword(), getEmail(), getNickname(), getMemo(), getCreatedAt(), getCreatedBy(), getModifiedAt(), getModifiedBy());
        }

        return Objects.hash(getId());
    }
}
