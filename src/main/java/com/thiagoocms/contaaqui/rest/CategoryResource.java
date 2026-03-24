package com.thiagoocms.contaaqui.rest;

import com.thiagoocms.contaaqui.common.AbstractCrudResource;
import com.thiagoocms.contaaqui.constants.AppConstants;
import com.thiagoocms.contaaqui.contaaqui_core.dto.category.CategoryDTO;
import com.thiagoocms.contaaqui.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstants.API + AppConstants.V1 + "/categories")
public class CategoryResource extends AbstractCrudResource<Long, CategoryDTO, CategoryDTO> {

    @Autowired
    protected CategoryResource(CategoryService service) {
        super(service);
    }
}
