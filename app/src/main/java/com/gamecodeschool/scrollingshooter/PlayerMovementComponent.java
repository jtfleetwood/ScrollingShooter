package com.gamecodeschool.scrollingshooter;

import android.graphics.PointF;

public class PlayerMovementComponent implements MovementComponent {

    @Override
    public boolean move(long fps, Transform t, Transform playerTransform) {

        float screenHeight = t.getmScreenSize().y;

        PointF location = t.getLocation();

        float speed = t.getSpeed();

        float height = t.getObjectHeight();

        if (t.headingDown()) {
            location.y += speed / fps;
        }

        else if (t.headingUp()) {
            location.y -= speed / fps;
        }

        // Ensures that no portion of the object is going off screen, hence usage of height in calculations.
        if (location.y > screenHeight - height) {
            location.y = screenHeight - height;
        }

        else if (location.y < 0) {
            location.y = 0;
        }

        t.updateCollider();

        return true;
    }


}
