package com.thiagoocms.contaaqui.service;

import com.thiagoocms.contaaqui.common.AbstractCrudService;
import com.thiagoocms.contaaqui.common.MessageService;
import com.thiagoocms.contaaqui.contaaqui_core.domain.category.Category;
import com.thiagoocms.contaaqui.contaaqui_core.dto.category.CategoryDTO;
import com.thiagoocms.contaaqui.mapper.CategoryMapper;
import com.thiagoocms.contaaqui.contaaqui_core.repository.CategoryRepository;
import com.thiagoocms.contaaqui.validation.CategoryValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractCrudService<Category, Long, CategoryDTO> {

    @Autowired
    protected CategoryService(CategoryRepository repository, CategoryMapper mapper, CategoryValidation validation, MessageService messageService) {
        super(repository, mapper, validation, messageService);
        setMessageNotFound("error.category.not.found");
    }
}
