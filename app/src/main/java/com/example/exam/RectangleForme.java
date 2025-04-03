package com.example.exam;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class RectangleForme extends Forme {
    float x1, y1, x2, y2;
    Paint paint;

    public RectangleForme(float x1, float y1, float x2, float y2, int couleur) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        paint = new Paint();
        paint.setColor(couleur);
        paint.setStyle(Paint.Style.STROKE);
//        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
    }

    @Override
    public void dessiner(Canvas canvas) {
        canvas.drawRect(Math.min(x1, x2), Math.min(y1, y2),
                Math.max(x1, x2), Math.max(y1, y2), paint);
    }
}
