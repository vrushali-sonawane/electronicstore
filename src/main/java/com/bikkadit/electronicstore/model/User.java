package com.bikkadit.electronicstore.model;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
   // @GeneratedValue(strategy =GenerationType.IDENTITY)
    private String userId;

    @Column(name="user_name")
    private String name;

    @Column(name="user_email",unique = true)
    private String email;

    @Column(name="user_password",length=10)
    private String password;

    private String gender;

    private String about;

    private String imageName;

}
