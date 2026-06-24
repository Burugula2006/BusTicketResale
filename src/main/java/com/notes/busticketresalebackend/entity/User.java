package com.notes.busticketresalebackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String name;

    @Column(
            unique = true,
            nullable = false
    )
    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    private Integer warningCount = 0;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AccountStatus accountStatus =
            AccountStatus.ACTIVE;

    @OneToMany(
            mappedBy = "seller"
    )
    private List<Ticket> tickets;
}