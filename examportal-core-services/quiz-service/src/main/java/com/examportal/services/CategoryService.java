package com.examportal.services;

import com.examportal.constant.MessageConstant;
import com.examportal.dtos.CategoryDTO;
import com.examportal.dtos.UpdateCategoryDTO;
import com.examportal.entities.Category;
import com.examportal.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;


    public CategoryService(CategoryRepository categoryRepository,
                           ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public Category addCategory(CategoryDTO categoryDTO) {
        Category category = this.modelMapper.map(categoryDTO, Category.class);
        Category savedCat = categoryRepository.save(category);
        log.info("Category saved successfully : {}", category);
        return savedCat;
    }

    public Category getCategory(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(UpdateCategoryDTO updateCategoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(updateCategoryDTO.getId());
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setTitle(updateCategoryDTO.getTitle() != null ? updateCategoryDTO.getTitle() : category.getTitle());
            category.setDescription(updateCategoryDTO.getDescription() != null ? updateCategoryDTO.getDescription() : category.getDescription());
            return categoryRepository.save(category);
        }
        return null;
    }

    public String deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.delete(optionalCategory.get());
            return MessageConstant.DELETE_CATEGORY_SUCCESS;
        }
        return MessageConstant.CATEGORY_NOT_FOUND;
    }
}
