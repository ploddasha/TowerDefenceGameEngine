package com.example.demo.controller;
import com.example.demo.model.Game;
import com.example.demo.service.GamesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@RestController
public class GamesController {
    private Map<String, String> name_ip = new HashMap<>();
    private Map<String, Instant> ip_time = new HashMap<>();

    private Map<String, String> ip_state = new HashMap<>();
    private Map<String, String> ip_ip = new HashMap<>();
    @Autowired
    private GamesService gamesService;

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gamesService.getAllGames();
    }

    @GetMapping("/state")
    public String getState(HttpServletRequest request) {
        String ip = gamesService.getClientIp(request);
        Instant now = Instant.now();
        ip_time.put(ip, now);
        return ip_state.get(ip_ip.get(ip));
    }

    @PostMapping("/updateState")
    public void updateState(HttpServletRequest request, @RequestBody String state) {
        String ip = gamesService.getClientIp(request);
        ip_state.put(ip, state);
        System.out.println(ip_state.get(ip));
    }

    //Функция, проверяющая что именно два клиента подключилось к серверу
    @GetMapping("/connect")
    public boolean getConnection(HttpServletRequest request, @RequestParam(name = "name", required = false) String name) {
        if (!name_ip.containsKey(name)) {
            name_ip.put(name,gamesService.getClientIp(request));
        } else if (!name_ip.get(name).equals(gamesService.getClientIp(request))) {
            ip_ip.put(name_ip.get(name), gamesService.getClientIp(request));
            ip_ip.put(gamesService.getClientIp(request), name_ip.get(name));
        }
        ip_time.put(gamesService.getClientIp(request), Instant.now());
        return ip_state.size() >= 2;
    }

    @GetMapping("/check")
    public boolean checkConnection(HttpServletRequest request) {
        String ip = gamesService.getClientIp(request);
        String ip_op = ip_ip.get(ip);
        Duration duration = Duration.between(ip_time.get(ip_op), Instant.now());
        long seconds = duration.getSeconds();
        return seconds <= 10;
    }
}