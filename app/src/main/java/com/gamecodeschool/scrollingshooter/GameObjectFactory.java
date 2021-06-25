package com.gamecodeschool.scrollingshooter;

import android.content.Context;
import android.graphics.PointF;

public class GameObjectFactory {
    private Context mContext;
    private PointF mScreenSize;
    private GameEngine mGameEngineReference;

    GameObjectFactory(Context c, PointF screenSize, GameEngine gameEngine) {
        this.mContext = c;
        this.mScreenSize = screenSize;
        mGameEngineReference = gameEngine;
    }
    // Receiving a class that extends the ObjectSpec abstract class.
    GameObject create(ObjectSpec spec) {
        GameObject object = new GameObject();

        // Tells us how many components are in the object we want to create. Not all contain the same (alien vs. background).
        int numComponents = spec.getComponents().length;
        // Hiding newly created object off screen.
        final float HIDDEN = -2000f;

        object.setMTag(spec.getTag());

        // Creating speed relative to screen size;
        float speed = mScreenSize.x / spec.getSpeed();

        PointF objectSize = new PointF(mScreenSize.x / spec.getScale().x, mScreenSize.y / spec.getScale().y);

        PointF location = new PointF(HIDDEN, HIDDEN);
        object.setTransform(new Transform(speed, objectSize.x, objectSize.y, location, mScreenSize));

        // Basically, what's happening below - we are iterating through our spec components and adding them to the object.
        // (component by component)

        for (int i = 0; i < numComponents; i++) {
            switch(spec.getComponents()[i]) {
                case "PlayerInputComponent":
                    object.setInput(new PlayerInputComponent(mGameEngineReference));


                    break;

                case "StdGraphicsComponent":
                    object.setGraphics(new StdGraphicsComponent(), mContext, spec, objectSize);

                    break;

                case "PlayerMovementComponent":
                    object.setMovement(new PlayerMovementComponent());

                    break;

                case "LaserMovementComponent":
                    object.setMovement(new LaserMovementComponent());

                    break;

                case "PlayerSpawnComponent":

                    object.setSpawner(new PlayerSpawnComponent());

                    break;

                case "LaserSpawnComponent":
                    object.setSpawner(new LaserSpawnComponent());

                    break;

                case "BackgroundGraphicsComponent":
                    object.setGraphics(new BackgroundGraphicsComponent(), mContext, spec, objectSize);

                    break;

                case "BackgroundMovementComponent":
                    object.setMovement(new BackgroundMovementComponent());

                    break;

                case "BackgroundSpawnComponent":
                    object.setSpawner(new BackgroundSpawnComponent());

                    break;

                case "AlienChaseMovementComponent":
                    object.setMovement(new AlienChaseMovementComponent(mGameEngineReference));

                    break;

                case "AlienPatrolMovementComponent":
                    object.setMovement(new AlienPatrolMovementComponent(mGameEngineReference));

                    break;

                case "AlienDiverComponent":
                    object.setMovement(new AlienDiverComponent());

                    break;

                case "AlienHorizontalSpawnComponent":
                    object.setSpawner(new AlienHorizontalSpawnComponent());

                    break;

                case "AlienVerticalSpawnComponent":
                    object.setSpawner(new AlienVerticalSpawnComponent());

                    break;

                default:

                    System.out.println(spec.getComponents()[i] + "Was not loaded properly in the object.\n");

                    break;
            }
        }

        return object;
    }


}
