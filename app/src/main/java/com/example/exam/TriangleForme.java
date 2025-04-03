package com.example.exam;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class TriangleForme extends Forme {
    private float x1, y1, x2, y2, x3, y3;
    private Paint paint;

    public TriangleForme(float x1, float y1, float x2, float y2, float x3, float y3, int couleur) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;

        paint = new Paint();
        paint.setColor(couleur);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    @Override
    public void dessiner(Canvas canvas) {
        Path path = new Path();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.close(); // ferme le triangle

        canvas.drawPath(path, paint);
    }
}
