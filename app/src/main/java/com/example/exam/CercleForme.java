package com.example.exam;

import android.graphics.Canvas;
import android.graphics.Paint;

public class CercleForme extends Forme {
    float centerX, centerY, radius;
    Paint paint;

    public CercleForme(float x1, float y1, float x2, float y2, int couleur) {
        centerX = x1;
        centerY = y1;
        radius = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        paint = new Paint();
        paint.setColor(couleur);
//        paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
    }

    @Override
    public void dessiner(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, radius, paint);
    }
}