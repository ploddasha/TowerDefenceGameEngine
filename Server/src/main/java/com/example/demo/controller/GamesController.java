package com.example.demo.controller;
import com.example.demo.model.Game;
import com.example.demo.service.GamesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GamesController {

    @Autowired
    private GamesService gamesService;

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gamesService.getAllGames();
    }

    @GetMapping("/state")
    public String getState(HttpServletRequest request) {
        return gamesService.getClientIp(request);
    }
}

