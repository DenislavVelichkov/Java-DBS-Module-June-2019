package softuni.jsonexer.service;

import softuni.jsonexer.domain.dto.binding.json.ProductSeedDto;
import softuni.jsonexer.domain.dto.binding.xml.ProductImportRootDto;

import java.math.BigDecimal;

public interface ProductService {

    void seedProductsFromJson(ProductSeedDto[] productSeedDtos);

    Object[] productsInRange(BigDecimal more, BigDecimal less);

    Object[] successfullySoldProducts();

    void seedProductsFromXml(ProductImportRootDto productImportRootDto);
}
