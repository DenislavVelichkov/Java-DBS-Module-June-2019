package softuni.jsonexer.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import softuni.jsonexer.domain.dto.binding.json.CategorySeedDto;
import softuni.jsonexer.domain.dto.binding.json.ProductSeedDto;
import softuni.jsonexer.domain.dto.binding.json.UserSeedDto;
import softuni.jsonexer.domain.dto.binding.xml.CategoryImportRootDto;
import softuni.jsonexer.domain.dto.binding.xml.ProductImportRootDto;
import softuni.jsonexer.domain.dto.binding.xml.UserImportRootDto;
import softuni.jsonexer.service.CategoryService;
import softuni.jsonexer.service.ProductService;
import softuni.jsonexer.service.UserService;
import softuni.jsonexer.util.FileUtil;
import softuni.jsonexer.util.GsonParser;
import softuni.jsonexer.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
public class ImportController {
    private final static String USER_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/json/users.json";

    private final static String CATEGORY_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/json/categories.json";

    private final static String PRODUCT_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/json/products.json";

    private final static String USER_XML_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/xml/users.xml";

    private final static String CATEGORY_XML_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/xml/categories.xml";

    private final static String PRODUCT_XML_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/xml/products.xml";


    private final GsonParser gsonParser;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ImportController(GsonParser gsonParser,
                            XmlParser xmlParser,
                            FileUtil fileUtil,
                            UserService userService,
                            CategoryService categoryService,
                            ProductService productService) {
        this.gsonParser = gsonParser;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    public void seedUsersFromJson() throws IOException {
        String content = this.fileUtil.fileContent(USER_JSON_FILE_PATH);

        UserSeedDto[] userSeedDtos =
                this.gsonParser.parseDataFromJson(content, UserSeedDto[].class);

        this.userService.seedUsersFromJson(userSeedDtos);
    }

    public void seedUsersFromXml() throws IOException, JAXBException {
        UserImportRootDto userImportRootDto =
                this.xmlParser.parseXml(UserImportRootDto.class, USER_XML_FILE_PATH);
        this.userService.seedUsersFromXml(userImportRootDto);
    }

    public void seedCategoriesFromJson() throws IOException {
        String content = this.fileUtil.fileContent(CATEGORY_JSON_FILE_PATH);

        CategorySeedDto[] categorySeedDtos =
                this.gsonParser.parseDataFromJson(content, CategorySeedDto[].class);

        this.categoryService.seedCategories(categorySeedDtos);
    }

    public void seedCategoriesFromXml() throws IOException, JAXBException {
        CategoryImportRootDto categoryImportRootDto =
                this.xmlParser.parseXml(CategoryImportRootDto.class, CATEGORY_XML_FILE_PATH);
        this.categoryService.seedCategoriesFromXml(categoryImportRootDto);
    }

    public void seedProductsFromJson() throws IOException {
        String content = this.fileUtil.fileContent(PRODUCT_JSON_FILE_PATH);

        ProductSeedDto[] productSeedDtos =
                this.gsonParser.parseDataFromJson(content, ProductSeedDto[].class);

        this.productService.seedProductsFromJson(productSeedDtos);
    }

    public void seedProductsFromXml() throws IOException, JAXBException {
        ProductImportRootDto productImportRootDto =
                this.xmlParser.parseXml(ProductImportRootDto.class, PRODUCT_XML_FILE_PATH);
        this.productService.seedProductsFromXml(productImportRootDto);
    }
}
