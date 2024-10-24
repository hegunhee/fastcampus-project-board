package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Entity;

import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "userLogin"),
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity

public class UserAccount extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false) private String userLogin;
    @Setter @Column(nullable = false) private String userPassword;

    @Setter private String email;
    @Setter private String nickname;
    @Setter private String memo;

  
    protected UserAccount() {}

    private UserAccount(String userLogin, String userPassword, String email, String nickname, String memo) {
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    public static UserAccount of(String userLogin, String userPassword, String email, String nickname, String memo) {
        return new UserAccount(userLogin, userPassword, email, nickname, memo);
    }

  
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
                    super.equals(that);
        }

        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return Objects.hash(getUserLogin(), getUserPassword(), getEmail(), getNickname(), getMemo()) + super.hashCode();
        }

        return Objects.hash(getId());
    }
}
