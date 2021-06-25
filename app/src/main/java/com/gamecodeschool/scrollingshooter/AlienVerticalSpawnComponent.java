package com.gamecodeschool.scrollingshooter;

import java.util.Random;

public class AlienVerticalSpawnComponent implements SpawnComponent {
    public void spawn(Transform playerLTransform, Transform t) {
        Random random = new Random();

        float xPosition = random.nextInt((int)t.getmScreenSize().x);

        float spawnHeight = random.nextInt(300) - t.getObjectHeight();

        t.setLocation(xPosition, spawnHeight);

        t.headDown();
    }
}
