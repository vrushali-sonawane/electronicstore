package com.bikkadit.electronicstore.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="categories")
public class Category {
    @Id
    @Column(name="id")
    private String categoryId;

    @Column(name="category_title",length=60,nullable = false)
    private String title;

    @Column(name="category_desc",length=500)
    private String description;

    private String coverImage;

    @OneToMany(cascade = CascadeType.ALL,fetch =FetchType.LAZY,mappedBy = "category")

    private List<Product> products=new ArrayList<>();
}
