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
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity

public class UserAccount extends AuditingFields {


    @Id
    @Column(length = 50)
    private String userId;

    @Setter @Column(nullable = false) private String userPassword;

    @Setter private String email;
    @Setter private String nickname;
    @Setter private String memo;

  
    protected UserAccount() {}

    private UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    public static UserAccount of(String userId, String userPassword, String email, String nickname, String memo) {
        return new UserAccount(userId, userPassword, email, nickname, memo);
    }

  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;

        if (this.getUserId() == null) {
            return Objects.equals(this.getUserPassword(), that.getUserPassword()) &&
                    Objects.equals(this.getEmail(), that.getEmail()) &&
                    Objects.equals(this.getNickname(), that.getNickname()) &&
                    Objects.equals(this.getMemo(), that.getMemo()) &&
                    super.equals(that);
        }

        return Objects.equals(this.getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        if (this.getUserId() == null) {
            return Objects.hash(getUserPassword(), getEmail(), getNickname(), getMemo()) + super.hashCode();
        }

        return Objects.hash(getUserId());
    }
}
