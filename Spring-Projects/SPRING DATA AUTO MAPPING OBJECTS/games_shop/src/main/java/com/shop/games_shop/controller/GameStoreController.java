package com.shop.games_shop.controller;

import com.shop.games_shop.domain.dtos.GameDto;
import com.shop.games_shop.domain.dtos.UserLoginDto;
import com.shop.games_shop.domain.dtos.UserRegisterDto;
import com.shop.games_shop.services.GameService;
import com.shop.games_shop.services.UserService;
import com.shop.games_shop.services.impl.GameServiceImpl;
import com.shop.games_shop.services.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;

    public GameStoreController(UserServiceImpl userService, GameServiceImpl gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Awaiting input..");
            String[] params = sc.nextLine().split("\\|");

            switch (params[0]) {
                case "RegisterUser":
                    UserRegisterDto userRegisterDto =
                    new UserRegisterDto(params[1],params[2],params[3],params[4]);
                    System.out.println(this.userService.registerUser(userRegisterDto));

                    break;
                case "LoginUser":
                    UserLoginDto userLoginDto =
                        new UserLoginDto(params[1], params[2]);
                    System.out.println(this.userService.userLogin(userLoginDto));
                    this.gameService.setLoggedInUser(userLoginDto.getEmail());

                    break;
                case "LogoutUser":
                    System.out.println(this.userService.logOutUser());
                    this.gameService.setLoggedOutUser();

                    break;
                case "AddGame":
                    GameDto gameDto = new GameDto(
                       params[1],
                       params[4],
                       params[5],
                       Double.parseDouble(params[3]),
                       new BigDecimal(params[2]),
                       params[6],
                       LocalDate.parse(params[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    );
                    System.out.println(this.gameService.addGame(gameDto));

                    break;
                case "DeleteGame":
                    int idToDelete = Integer.parseInt(params[1]);
                    System.out.println(this.gameService.deleteGame(idToDelete));

                    break;
                case "EditGame":
                    System.out.println(this.gameService.editGame(Integer.parseInt(params[1]), params));

                    break;
                case "AllGames":
                    System.out.println(this.gameService.findAllGames());
                    break;

                case "DetailGame":
                    System.out.println(this.gameService.findGameByTitle(params[1]));
                    break;
                case "OwnedGames":
                    // TODO: 7/9/2019  
                    break;
            }
            
            System.out.println("-----------------------------------------");
        }
    }
}
