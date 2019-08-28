package softuni.jsonexer.service;

import softuni.jsonexer.domain.dto.binding.json.CategorySeedDto;
import softuni.jsonexer.domain.dto.binding.xml.CategoryImportRootDto;
import softuni.jsonexer.domain.dto.views.json_views.CategoriesDetailedViewDto;

import java.util.List;

public interface CategoryService {

    void seedCategories(CategorySeedDto[] categorySeedDtos);

    List<CategoriesDetailedViewDto> findAllCategories();

    void seedCategoriesFromXml(CategoryImportRootDto categoryImportRootDto);

}
