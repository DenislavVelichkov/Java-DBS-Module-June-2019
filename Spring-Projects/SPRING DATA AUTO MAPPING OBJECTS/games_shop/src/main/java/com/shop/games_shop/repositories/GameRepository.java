package com.shop.games_shop.repositories;

import com.shop.games_shop.domain.dtos.GameDetailsViewDto;
import com.shop.games_shop.domain.models.Game;
import com.shop.games_shop.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findGameById(Integer id);

    List<Game> findAllByUsers(User user);

    Game findByTitle(String title);
}
