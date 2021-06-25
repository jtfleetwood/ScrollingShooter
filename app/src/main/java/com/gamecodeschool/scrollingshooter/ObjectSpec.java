package com.gamecodeschool.scrollingshooter;

import android.graphics.PointF;

// Gives specifications regarding the object that needs to be created.
abstract class ObjectSpec {
    // Passed into finished game object instances so that physics engine can make decisions about collisions.
    private String mTag;

    // Corresponds to one of graphics files added in drawable folder.
    private String mBitmapName;
    private float mSpeed;
    private PointF mSizeScale;
    // Will hold list of all components needed to construct object.
    private String[] mComponents;

    ObjectSpec(String tag, String bitmapName, float speed, PointF relativeScale, String[] components) {
        mTag = tag;
        mBitmapName = bitmapName;
        mSpeed = speed;
        mSizeScale = relativeScale;
        mComponents = components;

    }

    String getTag() {
        return mTag;
    }

    String getBitmapName() {
        return mBitmapName;
    }

    float getSpeed() {
        return mSpeed;
    }

    PointF getScale() {
        return mSizeScale;
    }

    String[] getComponents() {
        return mComponents;
    }



}
