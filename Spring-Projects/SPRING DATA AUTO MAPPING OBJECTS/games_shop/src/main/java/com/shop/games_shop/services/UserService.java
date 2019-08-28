package com.shop.games_shop.services;

import com.shop.games_shop.domain.dtos.UserLoginDto;
import com.shop.games_shop.domain.dtos.UserRegisterDto;
import com.shop.games_shop.domain.models.Game;

import java.util.List;

public interface UserService {
    String registerUser(UserRegisterDto userRegisterDTO);

    String userLogin(UserLoginDto userLoginDto);

    String logOutUser();

    List<Game> findAllGames();
}
