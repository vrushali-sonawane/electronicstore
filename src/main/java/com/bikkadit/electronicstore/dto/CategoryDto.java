package com.bikkadit.electronicstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String categoryId;

    @NotBlank
    @Min(value = 4,message="Title must of min 4 character !!")
    private String title;

    @NotBlank(message = "Description Required")
    private String description;

    private String coverImage;
}
