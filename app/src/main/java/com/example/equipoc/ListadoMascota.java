package com.example.equipoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.equipoc.Config.config;
import com.example.equipoc.Interface.retrofitInterface;
import com.example.equipoc.Modelos.mascota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoMascota extends AppCompatActivity {
    EditText editCtext;
    Button btnCconsultar;
    ListView LvCmascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mascota);
        editCtext = findViewById(R.id.editCtextb);
        btnCconsultar = findViewById(R.id.btnCconsultar);
        LvCmascotas = findViewById(R.id.LvCmascotas);
        //Este es el metodo para traer los datos de las mascotas cuando se inicie el layout
        getMascotas();


        //funcion del bonton consultar
        btnCconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editCtext.getText().toString().equals("")) {
                    getByFiltro(editCtext.getText().toString());
                }
            }
        });

        LvCmascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView id_mascota =(TextView) view.findViewById(R.id.txtCIdMascota);
                Intent detalles = new Intent(getApplicationContext(),Detalle.class);
                detalles.putExtra("id_mascota",id_mascota.getText().toString());
                startActivity(detalles);
            }
        });
    }

    //para poder obtener los datos de la mascota
    private void getMascotas(){
        retrofitInterface cliente = config.getRetrofit().create(retrofitInterface.class);
        Call<List<mascota>> call =cliente.getMascotas("apiAnimales");//acompletar la url que usaremos
        call.enqueue(new Callback<List<mascota>>() {
            @Override
            public void onResponse(Call<List<mascota>> call, Response<List<mascota>> response) {
                HashMap<String, String> diccionario = new HashMap<>();
                //ArrayList<String> data = new ArrayList<String>();
                for (mascota datamascota :response.body()){//para recorrer todos los datos que tengamos guardados
                    diccionario.put(datamascota.getId_animal(),datamascota.getNombre());
                }
                List<HashMap<String,String>> listDiccionario = new ArrayList<>();
                SimpleAdapter adaptador= new SimpleAdapter(getApplicationContext(),listDiccionario,R.layout.lista_personalisada,
                                                                new String[]{"id_mascota","nombre"},
                                                                new int[]{R.id.txtCIdMascota,R.id.txtCNombreMascota});

                Iterator veces = diccionario.entrySet().iterator();
                while (veces.hasNext()){
                    HashMap<String,String> resultado = new HashMap<>();
                    Map.Entry par =(Map.Entry)veces.next();
                    resultado.put("id_mascota",par.getKey().toString());
                    resultado.put("nombre",par.getValue().toString());
                    listDiccionario.add(resultado);
                }
                LvCmascotas.setAdapter(adaptador);
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,data);
                //LvCmascotas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<mascota>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"algo esta mal",Toast.LENGTH_SHORT).show();
            }
        });
    }//fin de GET mascota


    //Funcion para poder hacer el filtro de la mascota
    private void getByFiltro(String nombre){
        retrofitInterface buscarAnimal = config.getRetrofit().create(retrofitInterface.class);
        Call<List<mascota>> call =  buscarAnimal.getMascotas("buscar/" + nombre);//acompretar la url que usaremos para buscar
        call.enqueue(new Callback<List<mascota>>() {
            @Override
            public void onResponse(Call<List<mascota>> call, Response<List<mascota>> response) {
                ArrayList<String> data = new ArrayList<String>();
                for (mascota datamascota :response.body()){ //recorrer los datos
                    data.add(datamascota.getNombre());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_activated_1,data);
                LvCmascotas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<mascota>> call, Throwable t) {

            }
        });
    }
}