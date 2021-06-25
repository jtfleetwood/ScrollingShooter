package com.gamecodeschool.scrollingshooter;

import android.graphics.PointF;

public class AlienChaseSpec extends ObjectSpec {
    private static final String tag = "Alien";
    private static final String bitmapName = "alien_ship1";
    private static final float speed = 4f;

    // Will be scaled to one-fifteenth of screen size. (pixels)
    private static final PointF relativeScale = new PointF(15f, 15f);

    // The below components will implement interfaces.
    private static final String[] components = new String [] {
            "StdGraphicsComponent", "AlienChaseMovementComponent", "AlienHorizontalSpawnComponent"};


    AlienChaseSpec() {
        super(tag, bitmapName, speed, relativeScale, components);

    }

}
