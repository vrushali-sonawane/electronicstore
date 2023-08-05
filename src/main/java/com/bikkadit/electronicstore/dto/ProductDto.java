package com.bikkadit.electronicstore.dto;

import com.bikkadit.electronicstore.model.Category;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDto {
    private String productId;
    @Size(min=4,message = "title have minimum 4 character !!")
    private String title;
    @NotBlank(message = "Description Required")
    private String description;
    private Double price;
    private Double discountedPrice;
    private Integer quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImage;
    private CategoryDto category;
}
