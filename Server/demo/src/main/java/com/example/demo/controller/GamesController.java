package com.example.demo.controller;
import com.example.demo.model.Game;
import com.example.demo.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesController {

    @Autowired
    private GamesService gamesService;

    @GetMapping
    public List<Game> getAllGames() {
        return gamesService.getAllGames();
    }

}
