package com.example.demo.controller;
import com.example.demo.model.Game;
import com.example.demo.service.GamesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class GamesController {

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
        return ip_state.get(ip_ip.get(ip));
    }

    @PostMapping("/updateState")
    public void updateState(HttpServletRequest request, @RequestBody String state) {
        String ip = gamesService.getClientIp(request);
        ip_state.put(ip, state);
    }

    //Функция, проверяющая что именно два клиента подключилось к серверу
    @GetMapping("/check")
    public boolean getConnectionCheck(HttpServletRequest request) {
        if (!ip_state.containsKey(gamesService.getClientIp(request))) {
            ip_state.put(gamesService.getClientIp(request), null);
            System.out.println(gamesService.getClientIp(request));
        }
        if (ip_state.size() == 2) {
            List<String> list = new ArrayList<>(ip_state.keySet());
            ip_ip.put(list.get(0), list.get(1));
            ip_ip.put(list.get(1), list.get(0));
        }
        return ip_state.size() >= 2;
    }
}

