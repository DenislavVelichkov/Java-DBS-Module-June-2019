package com.shop.games_shop.services;

import com.shop.games_shop.domain.dtos.GameDto;
import com.shop.games_shop.domain.models.Game;

import java.util.List;

public interface GameService {

    String addGame(GameDto gameDto);

    void setLoggedInUser(String email);

    void setLoggedOutUser();

    String editGame(Integer id, String...args);

    String deleteGame(Integer id);

    String findAllGames();

    String findGameByTitle(String title);
}
