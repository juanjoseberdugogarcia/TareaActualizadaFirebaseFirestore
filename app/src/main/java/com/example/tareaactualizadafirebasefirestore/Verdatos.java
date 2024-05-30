package com.example.tareaactualizadafirebasefirestore;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Verdatos extends AppCompatActivity {

    EditText editTextBuscar;
    ListView listViewDatos;
    ArrayAdapter<String> adapter;
    List<String> datosList;
    List<String> datosFiltrados;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdatos);

        editTextBuscar = findViewById(R.id.editTextBuscar);
        listViewDatos = findViewById(R.id.listViewDatos);

        datosList = new ArrayList<>();
        datosFiltrados = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datosFiltrados);
        listViewDatos.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        cargarDatos();


        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarDatos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        listViewDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreSeleccionado = datosFiltrados.get(position);
                mostrarDatosCompletos(nombreSeleccionado);
            }
        });
    }

    private void cargarDatos() {
        db.collection("usuarios")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String nombre = documentSnapshot.getString("nombre");
                            String edad = documentSnapshot.getString("edad");
                            String ciudad = documentSnapshot.getString("ciudad");
                            String datos = "Nombre: " + nombre + "\nEdad: " + edad + "\nCiudad: " + ciudad;
                            datosList.add(datos);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void filtrarDatos(String texto) {
        datosFiltrados.clear();
        for (String datos : datosList) {
            if (datos.toLowerCase().contains(texto.toLowerCase())) {
                datosFiltrados.add(datos);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void mostrarDatosCompletos(String nombreSeleccionado) {

    }
}
