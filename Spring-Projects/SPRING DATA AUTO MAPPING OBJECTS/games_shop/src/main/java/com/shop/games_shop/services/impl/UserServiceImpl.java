package com.shop.games_shop.services.impl;

import com.shop.games_shop.domain.dtos.UserLoginDto;
import com.shop.games_shop.domain.dtos.UserRegisterDto;
import com.shop.games_shop.domain.models.Game;
import com.shop.games_shop.domain.models.Role;
import com.shop.games_shop.domain.models.User;
import com.shop.games_shop.repositories.GameRepository;
import com.shop.games_shop.repositories.UserRepository;
import com.shop.games_shop.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private ModelMapper modelMapper;
    private String loggedInUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.modelMapper = new ModelMapper();
        this.loggedInUser = "";
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {
        StringBuilder sb = new StringBuilder();

        User user = this.modelMapper.map(userRegisterDto, User.class);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        User inDb = this.userRepository.findByEmail(user.getEmail()).orElse(null);

        if (inDb != null) {
            return sb.append("User is already registered!").toString();
        }

        if (violations.size() > 0) {

            for (ConstraintViolation<User> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }

        } else {

            if (this.userRepository.count() == 0) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.USER);
            }

            sb.append(user.getFullName()).append(" was registered");
            this.userRepository.saveAndFlush(user);
        }

        return sb.toString();
    }

    @Override
    public String userLogin(UserLoginDto userLoginDto) {
        StringBuilder sb = new StringBuilder();

        if (!this.loggedInUser.isEmpty()) {
            return sb.append("User is already logged in").toString();
        }

        User user = this.userRepository
                        .findByEmail(userLoginDto.getEmail())
                        .orElse(null);


        if (user == null) {
            return sb.append("Incorrect email").toString();
        } else {
            if (!user.getPassword().equals(userLoginDto.getPassword())) {
                return sb.append("Incorrect password").toString();
            }

            this.loggedInUser = user.getEmail();
            sb.append(String.format("Successfully logged in %s", user.getFullName()));
        }

        return sb.toString();
    }

    @Override
    public String logOutUser() {
        StringBuilder sb = new StringBuilder();

        if (this.loggedInUser.isEmpty()) {
            return sb.append("Cannot log out. No user was logged in.").toString();
        } else {
            User user = this.userRepository.findByEmail(this.loggedInUser).orElse(null);

            assert user != null;
            sb.append(String.format("User %s successfully logged out", user.getFullName()));

            this.loggedInUser = "";
        }


        return sb.toString();
    }

    @Override
    public List<Game> findAllGames() {

        return null;
    }
}
