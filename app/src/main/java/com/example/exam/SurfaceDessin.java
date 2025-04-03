package com.example.exam;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class SurfaceDessin extends View {
    private ArrayList<Forme> formes = new ArrayList<>();


    private Forme formeEnCours = null;
    private boolean enCoursDeDessin = false;

    public SurfaceDessin(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        for (Forme f : formes) {
            f.dessiner(canvas);
        }
        if (enCoursDeDessin && formeEnCours != null) {
            formeEnCours.dessiner(canvas);
        }
    }

    public void ajouterForme(Forme nouvelleForme) {
        formes.add(nouvelleForme);
        invalidate();
    }

    public void setFormeTemporaire(Forme f) {
        this.formeEnCours = f;
        enCoursDeDessin = true;
        invalidate();
    }
}
