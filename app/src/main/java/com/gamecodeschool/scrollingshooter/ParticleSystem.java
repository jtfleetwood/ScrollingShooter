package com.gamecodeschool.scrollingshooter;

import android.graphics.Canvas;

import android.graphics.Paint;

import android.graphics.PointF;

import java.sql.Array;
import java.util.ArrayList;

import java.util.Random;

public class ParticleSystem {
    float mDuration;
    ArrayList<Particle> mParticles;

    Random random = new Random();

    boolean mIsRunning = false;

    void init(int numParticles) {
        mParticles = new ArrayList<>();

        /*
        Below initializes the ArrayList of particles with randomly assigning angle in which particle comes out (radians), a random
        speed, and then assigning x/y velocity based off the x/y vector components of the overall magnitude of velocity.
         */
        for (int i = 0; i < numParticles; i++) {

            // Generating random angle
            float angle = (random.nextInt(360));

            // Converting degrees -> radians.
            // Radiant is measurement of arc of circle. Arc of circle measured to same length of that circle's radius.
            // The math class needs radian measurements.
            // There are pi radians in 180 degrees, and 2 pi radians in a circle.
            // Degrees are actually arbitrary and cannot be calculated by a computer in reference to an object.
            angle = angle * 3.14f / 180.f;

            // Is this pixels per second?
            float speed = (random.nextInt(20) + 1);

            PointF direction;

            // Using our pointF to store x/y component vectors of the velocity.
            direction = new PointF((float)Math.cos(angle) * speed, (float)Math.sin(angle) * speed);

            mParticles.add(new Particle(direction));

        }
    }

    void update(long fps) {
        // Gives us amount of ms per frame that have passed.
        // Hence, 1 sec / (FPS) -> 1 Sec x (1 sec / Frames) -> Sec / Frames. (ms)
        mDuration -= (1f/fps);

        for (Particle p : mParticles) {
            p.update();
        }

        if (mDuration < 0) {
            mIsRunning = false;
        }

    }

    void emitParticles(PointF startPosition) {
        mIsRunning = true;
        mDuration = 1f;

        for (Particle p : mParticles) {
            p.setPosition(startPosition);
        }
    }

    void draw(Canvas canvas, Paint paint) {
        for (Particle p : mParticles) {
            paint.setARGB(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            canvas.drawRect(p.getPosition().x, p.getPosition().y, p.getPosition().x + 25, p.getPosition().y + 25, paint);
        }
    }
}
