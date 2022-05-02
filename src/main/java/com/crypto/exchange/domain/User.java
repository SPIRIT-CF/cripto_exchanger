package com.crypto.exchange.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "chat_id", name = "users_unique_chatid_idx")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractBaseEntity {
    @Column(name = "chat_id", unique = true, nullable = false)
    @NotNull
    private int chatId;

    @Column(name = "name", unique = true, nullable = false)
    @NotBlank
    private String name;

/*    @Enumerated(STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;*/

/*    @OneToMany(fetch = LAZY, mappedBy = "user")
    @OrderBy("hour DESC")
    private List<Schedule> scheduleList;*/

    public User(int chatId) {
        this.chatId = chatId;
        this.name = String.valueOf(chatId);
        //this.roles = Set.of(Role.UNAUTHORIZED);
    }

    public User(Integer id, @NotNull int chatId, @NotBlank String name) {
        super(id);
        this.chatId = chatId;
        this.name = name;
/*        this.roles = roles;
        this.scheduleList = scheduleList;*/
    }

    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return chatId == user.chatId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chatId);
    }
}
