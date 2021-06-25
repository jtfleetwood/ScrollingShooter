package com.gamecodeschool.scrollingshooter;

import android.graphics.PointF;

import java.util.Random;

public class AlienDiverComponent implements MovementComponent {

    @Override
    public boolean move(long fps, Transform t, Transform playerTransform) {

        PointF location = t.getLocation();

        float speed = t.getSpeed();

        float slowDownRelativeToPlayer = 1.8f;

        if (!playerTransform.getFacingRight()) {
            location.x += speed * slowDownRelativeToPlayer / fps;
        }

        else {
            location.x -= speed * slowDownRelativeToPlayer / fps;
        }

        location.y += speed / fps;

        if (location.y > t.getmScreenSize().y) {
            Random random = new Random();
            location.y = random.nextInt(300) - t.getObjectHeight();

            location.x = random.nextInt((int)t.getmScreenSize().x);
        }

        t.updateCollider();

        return true;

    }
}
