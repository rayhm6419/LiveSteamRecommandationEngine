package com.aoshine.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.aoshine.demo.service.GameService;
import com.aoshine.demo.service.TwitchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller //告诉这是一个controller
public class GameController {

    private GameService gameService;


    @Autowired
    public GameController(GameService gameService){this.gameService = gameService;}

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public void getGame(@RequestParam(value = "game_name", required = false) String gameName, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        try {
            // Return the dedicated game information if gameName is provided in the request URL, otherwise return the top x games.
            if (gameName != null) {
                response.getWriter().print(new ObjectMapper().writeValueAsString(gameService.searchGame(gameName)));
            } else {
                response.getWriter().print(new ObjectMapper().writeValueAsString(gameService.topGames(0)));
            }
        } catch (TwitchException e) {
            throw new ServletException(e);
        }
    }
}

//game?game_name=whatever
//game
//    @RequestMapping(value = "/game", method = RequestMethod.GET) //告诉controller具体处理哪个
//    public void getGame(@RequestParam(value = "game_name", required = false)String gameName, HttpServletResponse response) throws IOException{
//    }
//    @RequestMapping(value = "/restaurant/{id}/menu", method = RequestMethod.GET)
//    public void searchMenu(@PathVariable("id") int id) {}
//
//    //以上两种都是可以的
