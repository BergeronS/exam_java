package com.example.exam;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Hashtable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayout surfaceDessin;
    SurfaceDessin surf;
    private String formeChoisie = "Rectangle";
    int couleurCourante = Color.RED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        surfaceDessin = findViewById(R.id.surface_dessin);
        surf = new SurfaceDessin(this);
        surf.setLayoutParams(new ConstraintLayout.LayoutParams(-1,-1));
        surfaceDessin.addView(surf);
        Ecouteur ec = new Ecouteur();
        surf.setOnTouchListener(ec);

        Spinner spinner = findViewById(R.id.spinner_forme);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Rectangle");
        arrayList.add("Cercle");
        arrayList.add("Triangle");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        Hashtable<String, String> descriptions = new Hashtable<>();
        descriptions.put("Rectangle", "Forme à 4 côtés droits");
        descriptions.put("Cercle", "Forme ronde et parfaite");
        descriptions.put("Triangle", "Forme à 3 côtés et 3 sommets");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item = adapterView.getItemAtPosition(position).toString();
                String info = descriptions.get(item);
                Toast.makeText(MainActivity.this, "Info : " + info, Toast.LENGTH_SHORT).show();
                formeChoisie = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        LinearLayout LLcouleurs = findViewById(R.id.couleurs);
        EcouteurCouleur ecCouleur = new EcouteurCouleur();
        for (int i = 0; i < LLcouleurs.getChildCount(); i++) {
            View temp = LLcouleurs.getChildAt(i);
            temp.setOnTouchListener(ecCouleur);
        }
    }

    private class Ecouteur implements View.OnTouchListener {
        float startX, startY;
        float tX1, tY1, tX2, tY2;
        int triangleState = 0; // 0: rien, 1: 1 point, 2: 2 points


        @Override
        public boolean onTouch(View source, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (formeChoisie.equals("Triangle")) {
                        if (triangleState == 0) {
                            tX1 = x;
                            tY1 = y;
                            triangleState = 1;
                        } else if (triangleState == 1) {
                            tX2 = x;
                            tY2 = y;
                            triangleState = 2;
                        } else if (triangleState == 2) {
                            float tX3 = x;
                            float tY3 = y;
                            Forme triangle = new TriangleForme(tX1, tY1, tX2, tY2, tX3, tY3, couleurCourante);
                            surf.ajouterForme(triangle);
                            triangleState = 0;
                            surf.setFormeTemporaire(null); // Clear preview
                        }
                    } else {
                        // Rectangle ou Cercle
                        startX = x;
                        startY = y;
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    Forme preview = null;

                    if (formeChoisie.equals("Rectangle")) {
                        preview = new RectangleForme(startX, startY, x, y, couleurCourante);
                    } else if (formeChoisie.equals("Cercle")) {
                        preview = new CercleForme(startX, startY, x, y, couleurCourante);
                    }

                    surf.setFormeTemporaire(preview);
                    break;

                case MotionEvent.ACTION_UP:
                    Forme finalForme = null;

                    if (formeChoisie.equals("Rectangle")) {
                        finalForme = new RectangleForme(startX, startY, x, y, couleurCourante);
                    } else if (formeChoisie.equals("Cercle")) {
                        finalForme = new CercleForme(startX, startY, x, y, couleurCourante);
                    }

                    if (finalForme != null) {
                        surf.ajouterForme(finalForme);
                    }

                    surf.setFormeTemporaire(null);
                    break;
            }

            return true;
        }
    }

    private class EcouteurCouleur implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                String tag = (String) view.getTag();
                couleurCourante = Color.parseColor(tag);
            }
            return true;
        }
    }
}