package com.gamecodeschool.scrollingshooter;

import android.graphics.PointF;

public class Particle {
    PointF mVelocity;
    PointF mPosition;

    // Point uses integer coordinates. PointF uses float.
    Particle(PointF direction) {
        // Same
        mVelocity = new PointF();
        mPosition = new PointF();

        mVelocity.x = direction.x;
        mVelocity.y = direction.y;
    }

    void update() {
        mPosition.x += mVelocity.x;
        mPosition.y += mVelocity.y;
    }

    void setPosition(PointF position) {
        mPosition.x = position.x;
        mPosition.y = position.y;
    }

    PointF getPosition() {
        return mPosition;
    }
}
