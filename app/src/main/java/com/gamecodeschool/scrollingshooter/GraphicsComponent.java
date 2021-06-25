package com.gamecodeschool.scrollingshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import androidx.constraintlayout.widget.ConstraintSet;

public interface GraphicsComponent {
    void initialize(Context c, ObjectSpec s, PointF screensize);

    // Transform holds data about where object is, size, and direction of travel.
    void draw(Canvas canvas, Paint paint, Transform t);
}
