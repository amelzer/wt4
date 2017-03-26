package com.hsam.game.c4tv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hsam.game.c4tv.domain.GameBlock;
import com.hsam.game.c4tv.domain.GameField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private GameField gameField;

    private final ObjectMapper objectMapper;

    @Autowired
    public GameController() {
        this.gameField = new GameField(1, 7, 1);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @RequestMapping(path = "/new", method = GET)
    public Response reset() throws Exception {
        System.out.println("Request to reset field.");
        gameField = new GameField(1, 7, 7);

        return response();
    }

    @RequestMapping(path = "/field", method = GET)
    @CrossOrigin
    public Response getField() throws Exception {
        System.out.println("Request to get field.");

        return response();
    }

    @RequestMapping(path = "/field/blocks", method = RequestMethod.POST)
    @CrossOrigin
    public Response putBlock(@RequestBody @Validated GameBlock block) throws Exception {
        System.out.println(String.format("Putting block for player %s on position %s:%s", block.getPlayer(), block.getX(), block.getY()));

        gameField.addBlock(block);

        return response();
    }

    @RequestMapping(path = "/field/fours", method = DELETE)
    @CrossOrigin
    public Response deleteFours(@RequestBody @Validated List<GameBlock> fours) throws Exception {
        System.out.println(String.format("Request to delete: %s\n", objectMapper.writeValueAsString(fours)));

        String player = determineScoringPlayer(fours);
        gameField.increaseScore(player);

        fours.forEach(gameField::deleteBlock);

        return response();
    }

    private String determineScoringPlayer(List<GameBlock> fours) {
        GameBlock singleBlock = fours.get(0);
        return gameField.getField()[singleBlock.getX()][singleBlock.getY()][singleBlock.getZ()].getPlayer();
    }

    private Response response() throws Exception {
        Response response = new Response();
        response.setTopView1d(gameField.topView()[0]);
        response.setTopView2d(gameField.topView());

        List<GameBlock> potentialFour = gameField.getFour();
        if (!potentialFour.isEmpty()) {
            response.setFours(potentialFour);
            response.setFrontView(gameField.frontView());
            response.setFullView(gameField.getField());
        }

        response.setScore(gameField.getScores());

        System.out.println("Response: :\n" + objectMapper.writeValueAsString(response));
        return response;
    }

    public static class Response {
        private GameBlock[] topView1d;
        private GameBlock[][] topView2d;

        private GameBlock[][] frontView;
        private GameBlock[][][] fullView;

        private List<GameBlock> fours;

        private Map<String, Integer> score;

        public GameBlock[] getTopView1d() {
            return topView1d;
        }

        public void setTopView1d(GameBlock[] topView1d) {
            this.topView1d = topView1d;
        }

        public GameBlock[][] getTopView2d() {
            return topView2d;
        }

        public void setTopView2d(GameBlock[][] topView2d) {
            this.topView2d = topView2d;
        }

        public GameBlock[][] getFrontView() {
            return frontView;
        }

        public void setFrontView(GameBlock[][] frontView) {
            this.frontView = frontView;
        }

        public GameBlock[][][] getFullView() {
            return fullView;
        }

        public void setFullView(GameBlock[][][] fullView) {
            this.fullView = fullView;
        }

        public List<GameBlock> getFours() {
            return fours;
        }

        public void setFours(List<GameBlock> fours) {
            this.fours = fours;
        }

        public Map<String, Integer> getScore() {
            return score;
        }

        public void setScore(Map<String, Integer> score) {
            this.score = score;
        }
    }
}
