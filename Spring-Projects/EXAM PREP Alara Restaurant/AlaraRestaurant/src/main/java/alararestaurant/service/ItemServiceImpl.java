package alararestaurant.service;

import alararestaurant.models.dto.binding.CategoryImportDto;
import alararestaurant.models.dto.binding.ItemImportDto;
import alararestaurant.models.entities.Category;
import alararestaurant.models.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {
    private static final String ITEMS_PATH =
        System.getProperty("user.dir") + "/src/main/resources/files/items.json";
    
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    
    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository,
                           CategoryRepository categoryRepository,
                           Gson gson,
                           FileUtil fileUtil,
                           ValidationUtil validationUtil,
                           ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() != 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile(ITEMS_PATH);
    }

    @Override
    public String importItems(String items) throws IOException {
        StringBuilder sb = new StringBuilder();
        ItemImportDto[] itemImportDtos =
            this.gson.fromJson(this.readItemsJsonFile(), ItemImportDto[].class);
    
        for (ItemImportDto itemImportDto : itemImportDtos) {
            CategoryImportDto categoryImportDto = new CategoryImportDto();
            categoryImportDto.setName(itemImportDto.getCategory());
            if (!this.validationUtil.isValid(itemImportDto) ||
                    !this.validationUtil.isValid(categoryImportDto) ||
                    categoryImportDto.getName().equals("Invalid")) {
                System.out.println("Invalid data format.");
                continue;
            }
            
            Item item = this.itemRepository
                            .findAllByName(itemImportDto.getName())
                            .orElse(null);
    
            if (item == null) {
                Category category = this.categoryRepository
                                        .findAllByName(categoryImportDto.getName())
                                        .orElse(null);
                if (category == null) {
                    category = this.modelMapper.map(categoryImportDto, Category.class);
                    this.categoryRepository.saveAndFlush(category);
                }
                
                item = this.modelMapper.map(itemImportDto, Item.class);
                item.setCategory(category);
                this.itemRepository.saveAndFlush(item);
                sb.append(String.format("Record %s successfully imported.",
                    item.getName()))
                    .append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }
}
