package com.example.equipoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.equipoc.Config.config;
import com.example.equipoc.Interface.retrofitInterface;
import com.example.equipoc.Modelos.mascota;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detalle extends AppCompatActivity {
    String id_mascota = "";
    TextView id,nombre,edad,raza,nombrep,apellidop,apellidom,direccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        id=findViewById(R.id.txtCid);
        nombre=findViewById(R.id.txtCapellidoM);
        edad=findViewById(R.id.txtCedad);
        raza=findViewById(R.id.txtCraza);
        nombrep=findViewById(R.id.txtCnombreP);
        apellidop=findViewById(R.id.txtCapellidoP);
        apellidom=findViewById(R.id.txtCapellidoM);
        direccion=findViewById(R.id.txtCDireccion);
        //recuperar los datos del activity enviados desde el anterior
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id_mascota = extras.getString("id_mascota");
            getDetalle(id_mascota);
        }
    }


    private  void getDetalle(String id_mascota){
        retrofitInterface cliente = config.getRetrofit().create(retrofitInterface.class);
        Call<List<mascota>> call = cliente.getByFiltro("apiAnimales/" + id_mascota);
        call.enqueue(new Callback<List<mascota>>() {
            @Override
            public void onResponse(Call<List<mascota>> call, Response<List<mascota>> response) {
                List<mascota> resultado = response.body();
                id.setText(resultado.get(0).getId_animal());
                nombre.setText(resultado.get(0).getNombre());
                edad.setText(resultado.get(0).getEdad());
                raza.setText(resultado.get(0).getRaza());
                nombrep.setText(resultado.get(0).getClientes().getNombre());
                apellidop.setText(resultado.get(0).getClientes().getApellido_p());
                apellidom.setText(resultado.get(0).getClientes().getApellido_m());
                direccion.setText(resultado.get(0).getClientes().getDireccion());

            }

            @Override
            public void onFailure(Call<List<mascota>> call, Throwable t) {

            }
        });

    }

}