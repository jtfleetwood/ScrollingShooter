package com.gamecodeschool.scrollingshooter;

import android.content.Context;
import android.graphics.PointF;

import java.util.ArrayList;

public class Level {

    public static final int BACKGROUND_INDEX = 0;
    public static final int PLAYER_INDEX = 1;
    public static final int FIRST_PLAYER_LASER = 2;
    public static final int LAST_PLAYER_LASER = 4;
    public static int mNextPlayerLaser;
    public static final int FIRST_ALIEN = 5;
    public static final int SECOND_ALIEN = 6;
    public static final int THIRD_ALIEN = 7;
    public static final int FOURTH_ALIEN = 8;
    public static final int FIFTH_ALIEN = 9;
    public static final int SIXTH_ALIEN = 10;
    public static final int LAST_ALIEN = 10;
    public static final int FIRST_ALIEN_LASER = 11;
    public static final int LAST_ALIEN_LASER = 15;
    public static int mNextAlienLaser;
    // This will hold all the instances of GameObject
    private ArrayList<GameObject> objects;

    public Level(Context context, PointF mScreenSize, GameEngine ge) {
        objects = new ArrayList<>();
        // Passing in context to retrieve .png files from our resources to handle graphics of each object.
        GameObjectFactory factory = new GameObjectFactory(context, mScreenSize, ge);

        buildGameObjects(factory);
    }

    ArrayList<GameObject> buildGameObjects(GameObjectFactory factory) {
        // Clearing after each new level.
        objects.clear();

        objects.add(BACKGROUND_INDEX, factory.create(new BackgroundSpec()));
        objects.add(PLAYER_INDEX, factory.create(new PlayerSpec()));

        for (int i = FIRST_PLAYER_LASER; i != LAST_PLAYER_LASER + 1; i++) {
            objects.add(i, factory.create(new PlayerLaserSpec()));
        }

        mNextPlayerLaser = FIRST_PLAYER_LASER;

        objects.add(FIRST_ALIEN, factory.create(new AlienChaseSpec()));
        objects.add(SECOND_ALIEN, factory.create(new AlienPatrolSpec()));
        objects.add(THIRD_ALIEN, factory.create(new AlienPatrolSpec()));
        objects.add(FOURTH_ALIEN, factory.create(new AlienChaseSpec()));
        objects.add(FIFTH_ALIEN, factory.create(new AlienDiverSpec()));
        objects.add(SIXTH_ALIEN, factory.create(new AlienDiverSpec()));

        for (int i = FIRST_ALIEN_LASER; i != LAST_ALIEN_LASER + 1; i++) {
            objects.add(i, factory.create(new AlienLaserSpec()));
        }

        mNextAlienLaser = FIRST_ALIEN_LASER;

        return objects;

    }

    ArrayList<GameObject> getGameObjects() {
        return objects;
    }


}
