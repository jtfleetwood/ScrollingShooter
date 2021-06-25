package com.gamecodeschool.scrollingshooter;

import android.content.Context;

import android.graphics.Point;

import android.graphics.PointF;
import android.util.Log;

import android.view.MotionEvent;

import android.view.SurfaceView;

import java.util.ArrayList;


// Quick note: Primitive vs. Class object declaration. New vs. no use of new.

public class GameEngine extends SurfaceView implements GameEngineBroadcaster, GameStarter, Runnable, PlayerLaserSpawner, AlienLaserSpawner {
    private Thread mThread = null;
    private long mFPS;
    private GameState mGameState;
    private SoundEngine mSoundEngine;
    private ArrayList<InputObserver> inputObservers = new ArrayList();
    ParticleSystem mParticleSystem;
    UIController mUIController;
    HUD mHud;
    Renderer mRenderer;
    PhysicsEngine mPhysicsEngine;
    Level mLevel;

    /*
    Note: Context = interactions with your overall operating environment (files, databases, etc.)
          Activity = interactions with the Android OS. The activity itself is what the user is seeing and interacting with.
     */
    // Passing in reference back to our main activity.
    public GameEngine(Context context, Point size) {
        super(context);
        mUIController = new UIController(this);
        mGameState = new GameState(this, context);
        mSoundEngine = new SoundEngine(context);
        mHud = new HUD(size);
        mRenderer = new Renderer(this);
        mPhysicsEngine = new PhysicsEngine();
        mParticleSystem = new ParticleSystem();
        mParticleSystem.init(250);
        mLevel = new Level(context, new PointF(size.x, size.y), this);
    }

    public void addObserver(InputObserver o) {
        inputObservers.add(o);
    }

    @Override
    public void run() {

        while (mGameState.getThreadRunning()) {
            long frameStartTime = System.currentTimeMillis();
            ArrayList<GameObject> objects = mLevel.getGameObjects();

            if (!mGameState.getPaused()) {
                if (mPhysicsEngine.update(mFPS, objects, mGameState, mSoundEngine, mParticleSystem)) {
                    deSpawnReSpawn();
                }
            }

            mRenderer.draw(objects, mGameState, mHud, mParticleSystem);

            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame >= 1) {
                final int MILLIS_IN_SECOND = 1000;

                mFPS = MILLIS_IN_SECOND / timeThisFrame;

            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        for (InputObserver o : inputObservers) {
            o.handleInput(motionEvent, mGameState, mHud.getControls());
        }

        mSoundEngine.playShoot();

        return true;
    }

    public void stopThread() {

        mGameState.stopEverything();

        try {
            mThread.join();
        } catch(InterruptedException e) {
            Log.e("Exception", "stopThread()" + e.getMessage());
        }
    }

    public void startThread() {
        // Our class is a runnable object - implements Runnable interface.
        mGameState.startThread();
        mThread = new Thread(this);

        mThread.start();
    }

    @Override
    public void deSpawnReSpawn() {
        ArrayList<GameObject> objects = mLevel.getGameObjects();

        for (GameObject o : objects) {
            o.setInactive();
        }

        // Spawning just the player and background, needing transform information for that (location/speed,etc)
        objects.get(Level.PLAYER_INDEX).spawn(objects.get(Level.PLAYER_INDEX).getTransform());

        // Each different object will call their different implementation of spawn component (hence spawn component aggregated in gameObject).
        objects.get(Level.BACKGROUND_INDEX).spawn(objects.get(Level.PLAYER_INDEX).getTransform());


        for (int i = Level.FIRST_ALIEN; i != Level.LAST_ALIEN + 1; i++) {
            objects.get(i).spawn(objects.get(Level.PLAYER_INDEX).getTransform());
        }

        /* ECS Consists of:
         * An abstract object spec consisting of different components that can be extended and filled in by concrete
         * classes that inherit from the object spec class. Ex: BackgroundSpec (contains backgroundGraphicsComponent,etc).
         * These discussed components are interfaces that are implemented by concrete classes, for which their names
         * are included within the object spec. Ex: A Graphics component (interface) would be implemented by a concrete
         * BackgroundGraphicsComponent class and added as a component within the background object spec's concrete class
         * within the array of strings that details needed components for the object spec.
         *
         * These component lists are used by the GameObjectFactory create method that will iterate through a passed in object
         * spec's component list and create a new game object and sets behavior accordingly for each component iterated over.
         * These concrete class components that are passed into the object component setter's are passed in as interfaces.
         *
         * Levels then creates a game object array and uses the object factory to create new game objects based off the passed
         * in spec objects, and these objects are indexed.
         *
         * GameObject in an abstract manner calls all of the methods relating to different components of behavior for each
         * object within it's own methods in the class. GameObject calls draw --> calls draw method from player graphics component.
         *
         */

    }

    @Override
    public boolean spawnPlayerLaser(Transform transform) {
        ArrayList<GameObject> objects = mLevel.getGameObjects();

        if (objects.get(Level.mNextPlayerLaser).spawn(transform)) {
            Level.mNextPlayerLaser++;
            mSoundEngine.playShoot();
            if (Level.mNextPlayerLaser == Level.LAST_PLAYER_LASER + 1) {
                Level.mNextPlayerLaser = Level.FIRST_PLAYER_LASER;
            }
        }
        return true;
    }

    public void spawnAlienLaser(Transform transform) {
        ArrayList<GameObject> objects = mLevel.getGameObjects();

        if (objects.get(Level.mNextAlienLaser).spawn(transform)) {
            Level.mNextAlienLaser++;
            mSoundEngine.playShoot();
        }

        if (Level.mNextAlienLaser == Level.LAST_ALIEN_LASER + 1) {
            Level.mNextAlienLaser = Level.FIRST_ALIEN_LASER;
        }
    }
}
