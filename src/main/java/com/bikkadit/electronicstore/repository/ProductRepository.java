package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.model.Category;
import com.bikkadit.electronicstore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    //search


    Page<Product> findByTitleContaining(String subTitle,Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByCategory(Category category,Pageable pageable);
    //other method
    //custom finder method
    //query method

}
