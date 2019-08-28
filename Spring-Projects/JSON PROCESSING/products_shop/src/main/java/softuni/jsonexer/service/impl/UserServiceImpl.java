package softuni.jsonexer.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonexer.domain.dto.binding.json.UserSeedDto;
import softuni.jsonexer.domain.dto.binding.xml.UserImportDto;
import softuni.jsonexer.domain.dto.binding.xml.UserImportRootDto;
import softuni.jsonexer.domain.dto.views.json_views.ProductSimpleViewDto;
import softuni.jsonexer.domain.dto.views.json_views.ProductsSoldWrapperDto;
import softuni.jsonexer.domain.dto.views.json_views.SellerAndSoldProductsDto;
import softuni.jsonexer.domain.dto.views.json_views.UsersCountDto;
import softuni.jsonexer.domain.models.User;
import softuni.jsonexer.repository.ProductRepository;
import softuni.jsonexer.repository.UserRepository;
import softuni.jsonexer.service.UserService;
import softuni.jsonexer.util.ValidatorUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public UserServiceImpl(ValidatorUtil validatorUtil,
                           ModelMapper modelMapper,
                           UserRepository userRepository,
                           ProductRepository productRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUsersFromJson(UserSeedDto[] userSeedDtos) {
        for (UserSeedDto userSeedDto : userSeedDtos) {

            if (!validatorUtil.isValid(userSeedDto)) {
                this.validatorUtil.violations(userSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }
            User user = this.userRepository
                    .findByFirstNameAndLastName(
                            userSeedDto.getFirstName(), userSeedDto.getLastName())
                    .orElse(null);

            if (user == null) {
                List<User> users = this.userRepository.findAll();
               /* if (users.size() > 0) {
                    userSeedDto.setFriends(this.getRandomFriends(users));
                }*/
                user = this.modelMapper.map(userSeedDto, User.class);
                this.userRepository.saveAndFlush(user);
            }
        }
    }

    private Set<User> getRandomFriends(List<User> users) {
        Random random = new Random();
        int size = random.nextInt(1) + 1;
        Set<User> friends = new HashSet<>();

        for (int i = 0; i < size; i++) {
            friends.add(users.get(random.nextInt((users.size() - 1) + 1)));
        }

        return friends;
    }

    @Override
    public UsersCountDto findAllSoldProductsByUser() {
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true);
        TypeMap<User, SellerAndSoldProductsDto> sellerMapping =
                modelMapper.createTypeMap(User.class, SellerAndSoldProductsDto.class);


        sellerMapping.addMappings(m -> m.map(
                User::getFirstName, SellerAndSoldProductsDto::setFirstName));
        sellerMapping.addMappings(m -> m.map(
                User::getLastName, SellerAndSoldProductsDto::setLastName));
        sellerMapping.addMappings(m -> m.map(
                User::getAge, SellerAndSoldProductsDto::setAge));
        sellerMapping.addMappings(m -> m.skip(SellerAndSoldProductsDto::setSoldProducts));

        List<SellerAndSoldProductsDto> users =
                this.userRepository.findAllByProductsSoldIsNotNullOrderByProductsSoldDescLastName()
                        .stream()
                        .map(sellerMapping::map)
                        .collect(Collectors.toList());

        UsersCountDto usersCount = new UsersCountDto();
        usersCount.setUser(users);
        usersCount.setUsersCount(users.size());

        for (SellerAndSoldProductsDto user : usersCount.getUser()) {
            ProductsSoldWrapperDto productWrapper = new ProductsSoldWrapperDto();

            List<ProductSimpleViewDto> products =
                    this.productRepository.findAll()
                            .stream()
                            .filter(product -> product.getSeller().getLastName().equals(user.getLastName()))
                            .map(product -> this.modelMapper.map(product, ProductSimpleViewDto.class))
                            .collect(Collectors.toList());

            productWrapper.setProducts(products);
            productWrapper.setCount(productWrapper.getProducts().size());
            user.setSoldProducts(productWrapper);
        }

        return usersCount;
    }

    @Override
    public void seedUsersFromXml(UserImportRootDto userImportRootDto) {
        for (UserImportDto userDto : userImportRootDto.getUserImportDtos()) {

            if (!validatorUtil.isValid(userDto)) {
                this.validatorUtil.violations(userDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }
            User user = this.userRepository
                    .findByFirstNameAndLastName(
                            userDto.getFirstName(), userDto.getLastName())
                    .orElse(null);

            if (user == null) {
                user = this.modelMapper.map(userDto, User.class);
                this.userRepository.saveAndFlush(user);
            }
        }
    }
}
