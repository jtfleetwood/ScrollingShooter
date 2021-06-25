package com.gamecodeschool.scrollingshooter;

import android.graphics.Point;

import android.graphics.Rect;

import android.view.MotionEvent;

import java.util.ArrayList;

public class UIController implements InputObserver {
    public UIController(GameEngineBroadcaster b) {
        b.addObserver(this);
    }

    // event reference contains multiple coordinates of multiple event types.
    @Override
    public void handleInput(MotionEvent event, GameState gameState, ArrayList<Rect> buttons) {
        // Below, getActionIndex() returns position in array of events that performed the action (event).
        // Ex: Person could be moving finger, and this is an event but not a touch.
        int i = event.getActionIndex();

        // Allows us to get coordinates on the specific action that we want to locate.
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);
        int eventType = event.getAction() & MotionEvent.ACTION_MASK;

        // Action pointer up is for multi-touch functionality.
        if (eventType == MotionEvent.ACTION_UP || eventType == MotionEvent.ACTION_POINTER_UP) {
            if (buttons.get(HUD.PAUSE).contains(x, y)) {
                if (!gameState.getPaused()) {
                    gameState.pause();
                }

                else if (gameState.getGameOver()) {
                    gameState.startNewGame();
                }

                else if (gameState.getPaused() && !gameState.getGameOver()) {
                    gameState.resume();
                }
            }
        }

    }
}
