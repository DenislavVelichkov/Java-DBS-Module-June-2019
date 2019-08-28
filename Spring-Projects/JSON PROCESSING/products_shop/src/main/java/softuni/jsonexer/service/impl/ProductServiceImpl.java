package softuni.jsonexer.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import softuni.jsonexer.domain.dto.binding.json.ProductSeedDto;
import softuni.jsonexer.domain.dto.binding.xml.ProductImportDto;
import softuni.jsonexer.domain.dto.binding.xml.ProductImportRootDto;
import softuni.jsonexer.domain.dto.views.json_views.ProductInRangeDto;
import softuni.jsonexer.domain.dto.views.json_views.UserBuyerDto;
import softuni.jsonexer.domain.dto.views.json_views.UserSellerDto;
import softuni.jsonexer.domain.dto.views.xml_views.ProductInRangeRootDto;
import softuni.jsonexer.domain.dto.views.xml_views.ProductsInRangeXmlViewDto;
import softuni.jsonexer.domain.models.Category;
import softuni.jsonexer.domain.models.Product;
import softuni.jsonexer.domain.models.User;
import softuni.jsonexer.repository.CategoryRepository;
import softuni.jsonexer.repository.ProductRepository;
import softuni.jsonexer.repository.UserRepository;
import softuni.jsonexer.service.ProductService;
import softuni.jsonexer.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository,
                              ValidatorUtil validatorUtil,
                              ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedProductsFromJson(ProductSeedDto[] productSeedDtos) {
        for (ProductSeedDto productSeedDto : productSeedDtos) {
            productSeedDto.setSeller(this.getRandomSeller());
            productSeedDto.setBuyer(this.getRandomBuyer());
            productSeedDto.setCategories(this.getRandomCategories());

            if (!this.validatorUtil.isValid(productSeedDto)) {
                this.validatorUtil.violations(productSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            Product product = this.productRepository
                    .findByName(productSeedDto.getName())
                    .orElse(null);

            if (product == null) {
                product = this.modelMapper.map(productSeedDto, Product.class);
                this.productRepository.saveAndFlush(product);
            }
        }
    }

    @Override
    public void seedProductsFromXml(ProductImportRootDto productImportRootDto) {
        for (ProductImportDto productDto : productImportRootDto.getProductImportDtos()) {
            productDto.setSeller(this.getRandomSeller());
            productDto.setBuyer(this.getRandomBuyer());
            productDto.setCategories(this.getRandomCategories());

            if (!this.validatorUtil.isValid(productDto)) {
                this.validatorUtil.violations(productDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            Product product = this.productRepository
                    .findByName(productDto.getName())
                    .orElse(null);

            if (product == null) {
                product = this.modelMapper.map(productDto, Product.class);
                this.productRepository.saveAndFlush(product);
            }
        }
    }

    private User getRandomSeller() {
        Random random = new Random();
        int id = 0;
        while (this.userRepository.findById(id).orElse(null) == null) {
            id = random.nextInt((int) this.userRepository.count() - 1) + 1;
        }

        return this.userRepository.findById(id).get();
    }

    private User getRandomBuyer() {
        Random random = new Random();

        int id = random.nextInt((int) this.userRepository.count() - 1) + 1;

        if (id % 4 == 0) {
            return null;
        }

        return this.userRepository.findById(id).orElse(null);
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int id = random.nextInt((int) this.categoryRepository.count() - 1) + 1;
        return this.categoryRepository.findById(id).orElse(null);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();

        int size = random.nextInt((int) this.categoryRepository.count() - 1) + 1;

        for (int i = 0; i < size; i++) {
            categories.add(getRandomCategory());
        }

        return categories;
    }

    @Override
    public Object[] productsInRange(BigDecimal more, BigDecimal less) {
        List<Product> products = this.productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(more, less);

        List<ProductInRangeDto> productInRangeDtos = new ArrayList<>();
        List<ProductsInRangeXmlViewDto> productInRangeXmlDtos = new ArrayList<>();

        for (Product product : products) {
            ProductInRangeDto productInRangeDto =
                    this.modelMapper.map(product, ProductInRangeDto.class);
            productInRangeDto.setSeller(String.format("%s %s", product.getSeller().getFirstName(),
                    product.getSeller().getLastName()));
            ProductsInRangeXmlViewDto productsInRangeXmlViewDto =
                    this.modelMapper.map(product, ProductsInRangeXmlViewDto.class);
            productsInRangeXmlViewDto.setSeller(String.format("%s %s", product.getSeller().getFirstName(),
                    product.getSeller().getLastName()));

            if (!validatorUtil.isValid(productInRangeDto) ||
                    !validatorUtil.isValid(productsInRangeXmlViewDto)) {
                System.out.println("Error! Invalid Data input.");

                continue;
            }

            productInRangeXmlDtos.add(productsInRangeXmlViewDto);
            productInRangeDtos.add(productInRangeDto);
        }

        ProductInRangeRootDto productInRangeRootDto = new ProductInRangeRootDto();
        productInRangeRootDto.setProducts(productInRangeXmlDtos);

        Object[] results = new Object[2];
        results[0] = productInRangeDtos;
        results[1] = productInRangeRootDto;
        return results;
    }

    @Override
    public Object[] successfullySoldProducts() {
        List<Product> products =
                this.productRepository.findAllBySellerIsNotNullAndBuyerIsNotNull();

        TypeMap<Product, UserBuyerDto> buyerJsonViewMap =
                this.modelMapper.createTypeMap(Product.class, UserBuyerDto.class);
        buyerJsonViewMap.addMappings(m ->
                m.map(Product::getName, UserBuyerDto::setName));
        buyerJsonViewMap.addMappings(m ->
                m.map(Product::getPrice, UserBuyerDto::setPrice));
        buyerJsonViewMap.addMappings(m ->
                m.map(product -> product.getBuyer().getFirstName(), UserBuyerDto::setFirstName));
        buyerJsonViewMap.addMappings(m ->
                m.map(product -> product.getBuyer().getLastName(), UserBuyerDto::setLastName));;

        TypeMap<Product, UserSellerDto> sellerJsonViewMap =
                this.modelMapper.createTypeMap(Product.class, UserSellerDto.class);
        sellerJsonViewMap.addMappings(m -> m.map(product -> product.getSeller().getFirstName(),
                (dest, value) -> dest.setFirstName((String) value)));
        sellerJsonViewMap.addMappings(m -> m.map(product -> product.getSeller().getLastName(),
                (dest, value) -> dest.setLastName((String) value)));
        sellerJsonViewMap.addMappings(m -> m.skip(UserSellerDto::setBuyers));

        List<UserSellerDto> sellers = new ArrayList<>();

        for (Product product : products) {
            UserSellerDto userSellerDto =
                    sellers
                            .stream()
                            .filter(
                                    user -> user
                                            .getLastName()
                                            .equals(product.getSeller().getLastName()))
                            .findFirst()
                            .orElse(null);
            if (userSellerDto == null) {
                userSellerDto = sellerJsonViewMap.map(product);
            }

            UserBuyerDto buyerDto = buyerJsonViewMap.map(product);
            userSellerDto.getBuyers().add(buyerDto);
            sellers.add(userSellerDto);
        }

        Object[] viewsResult = new Object[1];
        viewsResult[0] = sellers;
        return viewsResult;
    }
}
