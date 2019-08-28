package alararestaurant.service;

import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        StringBuilder sb = new StringBuilder();

        this.categoryRepository.findAll()
                .stream()
                .sorted((o1, o2) -> {
                    int result = Integer.compare(o2.getItems().size(), o1.getItems().size());
                    int sum1 = (int) o1.getItems()
                            .stream()
                            .mapToDouble(item ->
                                    Double.parseDouble(item.getPrice().toString()))
                            .sum();
                    int sum2 = (int) o2.getItems()
                            .stream()
                            .mapToDouble(item ->
                                    Double.parseDouble(item.getPrice().toString()))
                            .sum();

                    return result != 0 ?
                            result
                            :
                            Integer.compare(sum2, sum1);

                })
                .forEach(category -> {
                            sb.append(String.format("Category: %s", category.getName()))
                            .append(System.lineSeparator());
                    category
                            .getItems()
                            .forEach(item -> {
                                        sb.append(String.format("--Item Name: %s", item.getName()))
                                        .append(System.lineSeparator())
                                        .append(String.format("--Item Price: %s", item.getPrice()))
                                        .append(System.lineSeparator())
                                        .append(System.lineSeparator());
                            });
                });

        return sb.toString().trim();
    }
}
