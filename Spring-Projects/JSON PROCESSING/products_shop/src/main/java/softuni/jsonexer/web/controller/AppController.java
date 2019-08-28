package softuni.jsonexer.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.jsonexer.service.CategoryService;
import softuni.jsonexer.service.ProductService;
import softuni.jsonexer.service.UserService;
import softuni.jsonexer.util.GsonParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;

@Controller
public class AppController implements CommandLineRunner {

    private final ExportController exportController;
    private final ImportController importController;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final GsonParser gson;

    @Autowired
    public AppController(ExportController exportController,
                         ImportController importController,
                         UserService userService,
                         CategoryService categoryService,
                         ProductService productService,
                         GsonParser gson) {
        this.exportController = exportController;
        this.importController = importController;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
       /* this.importController.seedUsersFromJson();
        this.importController.seedCategoriesFromJson();
        this.importController.seedProductsFromJson();*/
       /* this.importController.seedUsersFromXml();
        this.importController.seedCategoriesFromXml();
        this.importController.seedProductsFromXml();*/
        System.out.println(this.productsInRange());
        System.out.println(this.successfullySoldProducts());
       /* System.out.println("----------------- Query 2 START -----------------");
        successfullySoldProducts();
        System.out.println("----------------- END -----------------");
        System.out.println("----------------- Query 3 START -----------------");
        findAllCategories();
        System.out.println("----------------- END -----------------");
        System.out.println("----------------- Query 4 START -----------------");
        findAllUsersAndProducts();
        System.out.println("----------------- END -----------------");*/
    }


    private String productsInRange() throws JAXBException, IOException {
        this.exportController
                .exportProductsInRange(
                        this.productService.productsInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000)));
        return "Data successfully exported for Query 1 in src\\main\\resources\\";
    }

    private String successfullySoldProducts() throws JAXBException, IOException {
        this.exportController
                .exportSuccessfullySoldProducts(
                        this.productService.successfullySoldProducts());
        return "Data successfully exported in Json File for Query 2 in src\\main\\resources\\";
    }

   /* private void findAllCategories() {
        List<CategoriesDetailedViewDto> categories =
                this.categoryService.findAllCategories();

        System.out.println(this.gson.exportDataToJson(categories));
    }

    private void findAllUsersAndProducts() {
        String usersAndProducts =
                this.gson.exportDataToJson(this.userService.findAllSoldProductsByUser());
        System.out.println(usersAndProducts);
    }*/
}
