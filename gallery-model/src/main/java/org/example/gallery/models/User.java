package org.example.gallery.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated
    private UserType userType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();

}
