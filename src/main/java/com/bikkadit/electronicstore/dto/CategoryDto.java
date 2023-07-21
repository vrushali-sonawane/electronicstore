package com.bikkadit.electronicstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String categoryId;

    @NotBlank(message = "title is required !!")
    @Size(min=4,message = "title have minimum 4 character !!")
    private String title;

    @NotBlank(message = "Description Required")
    private String description;

    private String coverImage;
}
