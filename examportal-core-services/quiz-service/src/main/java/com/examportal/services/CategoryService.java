package com.examportal.services;

import com.examportal.dtos.CategoryDTO;
import com.examportal.entities.Category;
import com.examportal.feignclient.UserFeignClientService;
import com.examportal.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;


    private final UserFeignClientService userFeignClientService;

    private final HttpServletRequest servletContext;

    public CategoryService(CategoryRepository categoryRepository,
                           ModelMapper modelMapper,
                           UserFeignClientService userFeignClientService,
                           HttpServletRequest servletContext) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.userFeignClientService = userFeignClientService;
        this.servletContext = servletContext;
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


}
