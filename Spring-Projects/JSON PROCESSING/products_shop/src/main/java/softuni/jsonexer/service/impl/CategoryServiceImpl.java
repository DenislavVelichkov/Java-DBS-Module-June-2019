package softuni.jsonexer.service.impl;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonexer.domain.dto.binding.json.CategorySeedDto;
import softuni.jsonexer.domain.dto.binding.xml.CategoryImportDto;
import softuni.jsonexer.domain.dto.binding.xml.CategoryImportRootDto;
import softuni.jsonexer.domain.dto.views.json_views.CategoriesDetailedViewDto;
import softuni.jsonexer.domain.models.Category;
import softuni.jsonexer.domain.models.Product;
import softuni.jsonexer.repository.CategoryRepository;
import softuni.jsonexer.service.CategoryService;
import softuni.jsonexer.util.ValidatorUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            if (!validatorUtil.isValid(categorySeedDto)){
                this.validatorUtil.violations(categorySeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            Category category =
                    this.categoryRepository
                            .findByName(categorySeedDto.getName())
                            .orElse(null);
            if (category == null) {
                category = this.modelMapper.map(categorySeedDto, Category.class);
                this.categoryRepository.saveAndFlush(category);
            }
        }
    }

    @Override
    public List<CategoriesDetailedViewDto> findAllCategories() {
        TypeMap<Category, CategoriesDetailedViewDto> mapCategory =
                this.modelMapper.createTypeMap(Category.class, CategoriesDetailedViewDto.class);

        Converter<Set<Product>, Integer> productsCount =
                src -> src.getSource() == null ?
                        null
                        :
                        src.getSource().size();

        Converter<Set<Product>, BigDecimal> productsAvgPrice =
                src -> (src.getSource() == null) ?
                        null
                        :
                        new BigDecimal(src.getSource()
                                .stream()
                                .mapToDouble(value -> Double.valueOf(value.getPrice().toString()))
                                .average()
                        .getAsDouble());

        Converter<Set<Product>, BigDecimal> productsTotalPrice =
                src -> (src.getSource() == null) ?
                        null
                        :
                        new BigDecimal(

                                src.getSource()
                                .stream()
                                .mapToDouble(value -> Double.valueOf(value.getPrice().toString()))
                                .sum());

        mapCategory.addMappings(m -> m.map(
                Category::getName, CategoriesDetailedViewDto::setName));
        mapCategory.addMappings(m -> m.using(productsCount)
                .map(Category::getProducts, CategoriesDetailedViewDto::setProductsCount));
        mapCategory.addMappings(m -> m.using(productsAvgPrice)
                .map(Category::getProducts, CategoriesDetailedViewDto::setAveragePrice));
        mapCategory.addMappings(m -> m.using(productsTotalPrice)
                .map(Category::getProducts, CategoriesDetailedViewDto::setTotalRevenue));

        List<CategoriesDetailedViewDto> ctg = this.categoryRepository
                .findAllByProductsIsNotNull()
                .stream()
                .map(mapCategory::map)
                .collect(Collectors.toList());
        return ctg;
    }

    @Override
    public void seedCategoriesFromXml(CategoryImportRootDto categoryImportRootDto) {
        for (CategoryImportDto categoryDto : categoryImportRootDto.getCategoryImportDtos()) {
            if (!validatorUtil.isValid(categoryDto)){
                this.validatorUtil.violations(categoryDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            Category category =
                    this.categoryRepository
                            .findByName(categoryDto.getName())
                            .orElse(null);
            if (category == null) {
                category = this.modelMapper.map(categoryDto, Category.class);
                this.categoryRepository.saveAndFlush(category);
            }
        }
    }
}
