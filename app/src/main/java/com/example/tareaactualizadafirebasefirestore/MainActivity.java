package com.example.tareaactualizadafirebasefirestore;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editTextNombre, editTextEdad, editTextCiudad;
    Button btnGuardar, btnVerDatos;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextCiudad = findViewById(R.id.editTextCiudad);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVerDatos = findViewById(R.id.btnVerDatos);

        db = FirebaseFirestore.getInstance();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });

        btnVerDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Verdatos.class));
            }
        });
    }

    private void guardarDatos() {
        String nombre = editTextNombre.getText().toString();
        String edad = editTextEdad.getText().toString();
        String ciudad = editTextCiudad.getText().toString();


        Map<String, Object> datos = new HashMap<>();
        datos.put("nombre", nombre);
        datos.put("edad", edad);
        datos.put("ciudad", ciudad);


        db.collection("usuarios")
                .add(datos)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
