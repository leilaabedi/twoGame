package com.maktab.twogame.repository;

import com.maktab.twogame.model.Game;

import java.util.ArrayList;
import java.util.List;


public class GameRepository {

    private static GameRepository sInstance;
    List<Game> mGame = new ArrayList<>();



    private GameRepository() {
        generateUsers();
    }

    public static GameRepository getInstance() {
        if (sInstance == null)
            sInstance = new GameRepository();
        return sInstance;
    }

    private void generateUsers() {
        for (int i = 0; i < 25; i++) {
            mGame.add(new Game(""));
        }
    }

    public  List<Game> getGame() {
        return mGame;
    }


}
