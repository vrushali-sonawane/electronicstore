package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
}
