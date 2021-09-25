package com.itca.crud_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class detallesarticulos extends AppCompatActivity {


    private TextView tv_codigo, tv_descripcion, tv_precio;
    private TextView tv_codigo1, tv_descripcion1, tv_precio1, tv_fecha;
    private Button btnAcuali;

    Dto datos = new Dto();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_articulos);

            /*
        tv_codigo = findViewById(R.id.tvCodigo);
        tv_descripcion = findViewById(R.id.tvDescripcion);
        tv_precio = findViewById(R.id.tvPrecio);*/

        tv_codigo1 = findViewById(R.id.tv_codigo1);
        tv_descripcion1 = findViewById(R.id.tv_descripcion1);
        tv_precio1 = findViewById(R.id.tv_precio1);
        tv_fecha = findViewById(R.id.tv_fecha);
        btnAcuali = findViewById(R.id.btn_actualizar);


        Bundle objeto = getIntent().getExtras();
        Dto dto = null;
        if (objeto != null){
            dto = (Dto) objeto.getSerializable("articulo");
            /*
            tv_codigo.setText(""+ dto.getCodigo());
            tv_descripcion.setText(dto.getDescripcion());
            tv_precio.setText(String.valueOf(dto.getPrecio()));*/

            tv_codigo1.setText(""+ dto.getCodigo());
            tv_descripcion1.setText(dto.getDescripcion());
            tv_precio1.setText(String.valueOf(dto.getPrecio()));
            tv_fecha.setText("Fecha de creacion: " + getDateTime());
        }
    }


    private String getDateTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.getDefault());
        Dto date = new Dto();
        return format.format(date);
    }
}