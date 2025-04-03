package com.example.exam;

import android.graphics.Canvas;
import android.graphics.Paint;

public class CercleForme extends Forme {
    private float x, y;
    private Paint paint;

    public CercleForme(float x, float y, int couleur) {
        this.x = x;
        this.y = y;

        paint = new Paint();
        paint.setColor(couleur);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    @Override
    public void dessiner(Canvas canvas) {
        canvas.drawCircle(x, y, 100, paint);
    }
}
