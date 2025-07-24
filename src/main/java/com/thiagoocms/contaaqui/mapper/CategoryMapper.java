package com.thiagoocms.contaaqui.mapper;

import com.thiagoocms.contaaqui.common.AbstractMapper;
import com.thiagoocms.contaaqui.contaaqui_core.domain.category.Category;
import com.thiagoocms.contaaqui.contaaqui_core.domain.user.User;
import com.thiagoocms.contaaqui.contaaqui_core.dto.category.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper extends AbstractMapper<Category, CategoryDTO> {

    @Override
    protected Category toEntity(CategoryDTO source) {
        Category target = new Category();
        target.setId(source.id());
        target.setName(source.name());
        target.setDescription(source.description());
        target.setDeleted(source.deleted());
        target.setUser(new User(source.userId()));
        return target;
    }

    @Override
    protected CategoryDTO toModel(Category source) {
        return new CategoryDTO(
                source.getId(),
                source.getUser() != null ?  source.getUser().getId() : null,
                source.getName(),
                source.getDescription(),
                source.isDeleted()
        );
    }

    @Override
    public void merge(Category source, Category target) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setDeleted(source.isDeleted());
        target.setUser(source.getUser());
    }
}
