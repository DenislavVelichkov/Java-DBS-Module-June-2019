package softuni.jsonexer.web.controller;

import org.springframework.stereotype.Controller;
import softuni.jsonexer.domain.dto.views.json_views.ProductInRangeDto;
import softuni.jsonexer.domain.dto.views.json_views.UserSellerDto;
import softuni.jsonexer.domain.dto.views.xml_views.ProductInRangeRootDto;
import softuni.jsonexer.service.CategoryService;
import softuni.jsonexer.service.ProductService;
import softuni.jsonexer.service.UserService;
import softuni.jsonexer.util.FileUtil;
import softuni.jsonexer.util.GsonParser;
import softuni.jsonexer.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Controller
public class ExportController {
    private final static String EXPORT_PRODUCTS_IN_RANGE_XML_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/xml/export/products-in-range.xml";
    private final static String EXPORT_PRODUCTS_IN_RANGE_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/json/export/products-in-range.json";
    private final static String EXPORT_SOLD_PRODUCTS_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/json/export/successfully-sold-products.json";
    private final GsonParser gsonParser;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public ExportController(GsonParser gsonParser,
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

   public void exportSuccessfullySoldProducts(Object[] views) throws IOException {
        List<UserSellerDto> productInRangeDto =
                (List<UserSellerDto>) views[0];
        this.gsonParser.exportDataToJson(
                productInRangeDto, EXPORT_SOLD_PRODUCTS_JSON_FILE_PATH);
    }

    public void exportProductsInRange(Object[] views) throws IOException, JAXBException {
        List<ProductInRangeDto> productInRangeDto =
                (List<ProductInRangeDto>) views[0];

        ProductInRangeRootDto productInRangeRootDto =
                (ProductInRangeRootDto) views[1];

        this.gsonParser.exportDataToJson(
                productInRangeDto, EXPORT_PRODUCTS_IN_RANGE_JSON_FILE_PATH);

        this.xmlParser.exportToXml(
                productInRangeRootDto,
                ProductInRangeRootDto.class,
                EXPORT_PRODUCTS_IN_RANGE_XML_FILE_PATH);
    }
}
