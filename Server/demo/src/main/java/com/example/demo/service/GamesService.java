package com.example.demo.service;

import com.example.demo.model.Game;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GamesService {


    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/games/games.txt"))))) {
            games = reader.lines()
                    .map(line -> {
                        String[] parts = line.split(",");
                        Game game = new Game();
                        game.setId(Integer.parseInt(parts[0].trim()));
                        game.setGameName(parts[1].trim());
                        return game;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return games;
    }


}
