package ch.supsi.cardgames.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    int id;
    private String username;

    private String firstName;

    private String lastName;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String password;

    public User(String lastName, String firstName, String username,UserRole role,String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.role=role;
        this.password=password;
    }
}
