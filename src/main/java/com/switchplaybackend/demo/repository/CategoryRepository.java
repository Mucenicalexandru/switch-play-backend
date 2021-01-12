package com.switchplaybackend.demo.repository;

import com.switchplaybackend.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
