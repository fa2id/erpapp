package com.fa2id.erpapp.service;

import com.fa2id.erpapp.model.Category;
import com.fa2id.erpapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Transactional
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
