package com.bikkadit.electronicstore.dto;

import com.bikkadit.electronicstore.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min=3,max=20,message = "Invalid name")
    private String name;

  //  @Email(message = "Invalid User Email")
    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$",message = "Invalid User Email")
    @NotBlank(message = "email is required")
    private String email;

    @Pattern(regexp ="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}")
    private String password;

    @Size(min=4,max=6,message = "Invalid gender")
    private String gender;

    @NotBlank(message = "write something about yourself !!")
    private String about;

    @ImageNameValid
    private String imageName;
}
