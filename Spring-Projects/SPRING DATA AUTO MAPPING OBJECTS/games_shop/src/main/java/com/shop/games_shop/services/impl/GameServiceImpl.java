package com.shop.games_shop.services.impl;

import com.shop.games_shop.domain.dtos.GameDetailsViewDto;
import com.shop.games_shop.domain.dtos.GameDto;
import com.shop.games_shop.domain.models.Game;
import com.shop.games_shop.domain.models.Role;
import com.shop.games_shop.domain.models.User;
import com.shop.games_shop.repositories.GameRepository;
import com.shop.games_shop.repositories.UserRepository;
import com.shop.games_shop.services.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    private String loggedInUser;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
        this.loggedInUser = "";
    }

    @Override
    public String addGame(GameDto gameDto) {
        StringBuilder sb = new StringBuilder();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Game game = this.modelMapper.map(gameDto, Game.class);

        Set<ConstraintViolation<Game>> violations = validator.validate(game);

        if (violations.size() > 0) {
            for (ConstraintViolation<Game> violation : violations) {
                sb.append(violation.getInvalidValue()).append(" ")
                    .append(violation.getMessage()).append(System.lineSeparator());
            }
            return sb.toString();
        }

        User user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        if (user == null) {
            return sb.append("No Logged in User!").toString();
        }

        if (!user.getRole().equals(Role.ADMIN)) {
            return sb.append(String.format("%s is not admin !", user.getFullName())).toString();
        }

        this.gameRepository.saveAndFlush(game);
        Set<Game> games = user.getGames();
        games.add(game);
        user.setGames(games);
        this.userRepository.saveAndFlush(user);

        sb.append(String.format("Added %s", game.getTitle()));

        return sb.toString();
    }

    @Override
    public void setLoggedInUser(String email) {
        this.loggedInUser = email;
    }

    @Override
    public void setLoggedOutUser() {
        this.loggedInUser = "";
    }

    @Override
    public String editGame(Integer id, String... args) {
        Game game = this.gameRepository.findGameById(id).orElse(null);

        if (game == null) {
            return "No such title present in the database!";
        }

        for (int i = 2; i < args.length; i++) {
            String[] pair = args[i].split("=");
            String attribute = pair[0];
            String value = pair[1];

            switch (attribute) {
                case "price":
                    game.setPrice(BigDecimal.valueOf(Double.parseDouble(value)));
                    break;
                case "size":
                    game.setSize(Double.parseDouble(value));
                    break;
                case "title":
                    game.setTitle(value);
                    break;
                case "trailer":
                    game.setTrailer(value.substring(value.length() - 11));
                    break;
                case "thumbnailURL":
                    game.setImageThumbnail(value);
                    break;
                case "description":
                    game.setDescription(value);
                    break;
                case "releaseDate":
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    game.setReleaseDate(LocalDate.parse(value, format));
                    break;
            }
        }

        this.gameRepository.saveAndFlush(game);

        return "Edited " + game.getTitle();
    }


    @Override
    public String deleteGame(Integer id) {
        Game game = this.gameRepository.findGameById(id).orElse(null);

        if (game != null) {
            this.gameRepository.delete(this.gameRepository.getOne(id));
            return "Deleted " + game.getTitle();
        }

        return "No such game title in the database!";
    }

    @Override
    public String findAllGames() {
        StringBuilder sb = new StringBuilder();
        this.gameRepository.findAll()
            .forEach(game ->
                         sb.append(String.format(
                             "%s %s", game.getTitle(), game.getPrice()
                         )).append(System.lineSeparator()));
        return sb.toString();
    }

    @Override
    public String findGameByTitle(String title) {
        StringBuilder sb = new StringBuilder();
        GameDetailsViewDto gameDto = modelMapper.map(this.gameRepository.findByTitle(title), GameDetailsViewDto.class);

        sb.append("Title: ").append(gameDto.getTitle()).append(System.lineSeparator())
          .append("Price: ").append(gameDto.getPrice()).append(System.lineSeparator())
          .append("Description: ").append(gameDto.getDescription()).append(System.lineSeparator())
          .append("ReleaseDate: ").append(gameDto.getReleaseDate());

        return sb.toString().trim();
    }
}
