package com.example.demo.service;

import com.example.demo.model.Game;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
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

    private final String LOCALHOST_IPV4 = "127.0.0.1";
    private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    public String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if(StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        if(!StringUtils.isEmpty(ipAddress)
                && ipAddress.length() > 15
                && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }


    public void addToRating(String name, String player, int result) throws IOException {
        Map<String, Integer> rating = new HashMap<>();
        if (Files.exists(Paths.get("src/main/resources/" + name + ".txt"))) {
            List<String> list_rating = Files.readAllLines(Paths.get("src/main/resources/" + name + ".txt"));
            String[] sm;
            for (String s : list_rating) {
                sm = s.split(" ");
                rating.put(sm[0], Integer.parseInt(sm[1]));
            }
        }
        if (!rating.containsKey(player) || rating.get(player) < result) {
            rating.put(player, result);
            List<Map.Entry<String, Integer>> list = new ArrayList<>(rating.entrySet());
            list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

            try (PrintWriter out = new PrintWriter("src/main/resources/" + name + ".txt")) {
                for (Map.Entry<String, Integer> entry : list) {
                    out.println(entry.getKey() + " " + entry.getValue());
                }
            } catch (FileNotFoundException e) {
                System.err.println("Файл не найден: " + e.getMessage());
            }
        }
    }
}
