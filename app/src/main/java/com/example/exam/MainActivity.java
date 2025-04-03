package com.example.exam;

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
    }

    private class Ecouteur implements View.OnTouchListener {
        float startX, startY;

        @Override
        public boolean onTouch(View source, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = x;
                    startY = y;
                    break;

                case MotionEvent.ACTION_MOVE:
                    Forme preview = null;

                    if (formeChoisie.equals("Rectangle")) {
                        preview = new RectangleForme(startX, startY, x, y, 0xFF0000FF);
                    }

                    surf.setFormeTemporaire(preview);
                    break;

                case MotionEvent.ACTION_UP:
                    Forme finalForme = null;

                    if (formeChoisie.equals("Rectangle")) {
                        finalForme = new RectangleForme(startX, startY, x, y, 0xFF0000FF);
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
}