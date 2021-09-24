package com.itca.crud_v2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.itca.crud_v2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private EditText et_codigo, et_descripcion, et_precio;
    private Button btn_guardar, btn_consultar1, btn_consultar2, btn_eliminar, btn_actualizar;
    private TextView tv_resultado;

    boolean inputEt=false;
    boolean inputEd=false;
    boolean input1=false;
    int resultadolnsert=0;

    Modal ventanas = new Modal();
    ConexionSQLite conexion = new ConexionSQLite(this);
    Dto datos = new Dto();
    AlertDialog.Builder dialogo;
//    modal_Toast_Custom modal= new modal_Toast_Custom();
//    Acercade acer = new Acercade();

    public  boolean onKeyDonw(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.close)
                    .setTitle("Warning")
                    .setMessage("¿Realmente desea salir?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            /*lntent intent —— new Intent(DashboardLuces.this, luces control sms.class),
                            startActivity(intent),
                            MainActivity.this.finishAffinity(),
                            finish(),*/
                            finishAffinity();
                        }
                    })
                    .show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento at listener habitual
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.close));
        toolbar.setTitleTextColor(getResources().getColor(R.color.my_color1));
        toolbar.setTitleMargin(0, 0, 0, 0);
        toolbar.setSubtitle("CRUD SQLite-2021");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.my_color));
        toolbar.setTitle("Mateo Ceron");
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacion();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanas.Search(MainActivity.this);
            }
        });

        et_codigo = (EditText) findViewById(R.id.et_codigo);
        et_descripcion = (EditText) findViewById(R.id.et_descripcion);
        et_precio = (EditText) findViewById(R.id.et_precio);


        btn_guardar = (Button) findViewById(R.id.btn_guardar);
        btn_consultar1 = (Button) findViewById(R.id.btn_consultar1);
        btn_consultar2 = (Button) findViewById(R.id.btn_consultar2);
        btn_eliminar = (Button) findViewById(R.id.btn_eliminar);
        btn_actualizar = (Button) findViewById(R.id.btn_actualizar);
//tv resultado —— (TextView) findViewByld(R.id.tv resultado),’

        String senal = "";
        String codigo = "";
        String descripcion = "";
        String precio = "";

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                codigo = bundle.getString("codigo");
                senal = bundle.getString("senal");
                descripcion = bundle.getString("descripcion");
                precio = bundle.getString("precio");
                if (senal.equals("1")){
                    et_codigo.setText(codigo);
                    et_descripcion.setText(descripcion);
                    et_precio.setText(precio);
                    //finish(),

                }
            }
        }catch (Exception e){

        }
    }


    private  void  confirmacion(){
        String mensaje = "Realmente desea salir?";
        dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setIcon(R.drawable.close);
        dialogo.setTitle("Warning");
        dialogo.setMessage(mensaje);
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogo, int id) {
                MainActivity.this.finish();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogo, int id) {

            }
        });
        dialogo.show();

    }

    private void setSupportActionBar(Toolbar toolbar) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            et_codigo.setText(null);
            et_descripcion.setText(null);
            et_precio.setText(null);
            return true;
        }else if(id == R.id.action_listaArticulos){
            Intent listViewActivity = new Intent(MainActivity.this, ConsultaSpinner.class);
            startActivity(listViewActivity);
            return  true;
        }else if(id == R.id.action_listaArticulos1){
            Intent listViewActivity = new Intent(MainActivity.this, ListViewArticulos.class);
            startActivity(listViewActivity);
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void  alta(View v){
        if (et_codigo.getText().toString().length()==0){
            if (et_codigo.getText().toString().length() == 0){
                et_codigo.setError("Campo obligatorio");
                inputEt = false;
            } else {
                inputEt = true;
            }

            if (et_descripcion.getText().toString().length() == 0){
                et_descripcion.setError("Campo obligatorio");
                inputEd = false;
            } else {
                inputEd = true;
            }

            if (et_precio.getText().toString().length() == 0){
                et_precio.setError("Campo obligatorio");
                input1 = false;
            } else {
                input1 = true;
            }

            if (inputEt && inputEd && input1){
                try {
                    datos.setCodigo(Integer.parseInt(et_codigo.getText().toString()));
                    datos.setDescripcion(et_descripcion.getText().toString());
                    datos.setPrecio(Double.parseDouble(et_precio.getText().toString()));

                    if (conexion.InserTradicional(datos)){
                        Toast.makeText(this, "Registro Agregado satisfactoriamente", Toast.LENGTH_SHORT).show();
                        limpiarDatos();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error ya existe un registro con el mismo codigo", Toast.LENGTH_SHORT).show();
                        limpiarDatos();
                    }
                } catch (Exception ex){
                    Toast.makeText(this, "Error, ya existe", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void mensaje(String mensaje){
        Toast.makeText(this, "" + mensaje, Toast.LENGTH_SHORT).show();
    }

    public void limpiarDatos(){
        et_codigo.setText(null);
        et_descripcion.setText(null);
        et_precio.setText(null);
        et_codigo.requestFocus();
    }

    public void consultaporcodigo(View view){
        if (et_codigo.getText().toString().length() == 0){
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }

        if (inputEt){
            String codigo = et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(codigo));

            if (conexion.consultaArticulos(datos)){
                et_descripcion.setText(datos.getDescripcion());
                et_precio.setText(""+datos.getPrecio());
            } else {
                Toast.makeText(this, "No existe articulo con dicho codigo", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        } else {
            Toast.makeText(this, "Ingrese el codigo del articulo a buscar", Toast.LENGTH_SHORT).show();
        }
    }

    public void consultapordescripcion(){

        if (et_descripcion.getText().toString().length() == 0){
            et_descripcion.setError("Campo obligatorio");
            inputEd = false;
        } else {
            inputEd = true;
        }

        if (inputEd){
            String descripcion = et_descripcion.getText().toString();
            datos.setDescripcion(descripcion);
            if (conexion.consultaDescripcion(datos)){
                et_codigo.setText(""+datos.getCodigo());
                et_descripcion.setText(datos.getDescripcion());
                et_precio.setText(""+datos.getPrecio());
            } else {
                Toast.makeText(this, "No existe articulo con dicha descripcion", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        } else {
            Toast.makeText(this, "Ingrese la descripcion del articulo a buscar", Toast.LENGTH_SHORT).show();
        }
    }

    public void bajaporcodigo(View view){
        if (et_codigo.getText().toString().length() == 0){
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }

        if (inputEt){
            String cod = et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(cod));
            if (conexion.bajaCodigo(MainActivity.this, datos)){
                limpiarDatos();
            } else {
                Toast.makeText(this, "No existe articulo con dicho codigo", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }
    }

    public void modificacion(View view){
        if (et_codigo.getText().toString().length() == 0){
            et_codigo.setError("Campo obligatorio");
            inputEt = false;
        } else {
            inputEt = true;
        }

        if (inputEt){
            String cod = et_codigo.getText().toString();
            String descripcion = et_descripcion.getText().toString();
            double precio = Double.parseDouble(et_precio.getText().toString());

            datos.setCodigo(Integer.parseInt(cod));
            datos.setDescripcion(descripcion);
            datos.setPrecio(precio);

            if (conexion.modificar(datos)){
                Toast.makeText(this, "Registro modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se ha encontrado resultado para la busqueda", Toast.LENGTH_SHORT).show();
            }
        }
    }

}