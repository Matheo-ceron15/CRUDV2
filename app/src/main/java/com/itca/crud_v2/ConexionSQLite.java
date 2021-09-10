package com.itca.crud_v2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ConexionSQLite extends SQLiteOpenHelper {
 boolean estadoDelete=true;
    //Va representar la informacion que se va representar en el combo
 ArrayList<Dto>listaArticulo;
    //entidad que representa los datos de la tabla, en este caso la tabla articulos
 ArrayList<Dto>articulosList;



    /*
    public ConexionSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/
    public ConexionSQLite(Context context){
        super(context, "administracion.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table articulos(codigo integer not null primary key, descripcion text, precio real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists articulos");
        onCreate(db);
    }

    public SQLiteDatabase bd(){
        SQLiteDatabase bd= this.getWritableDatabase();
        return  bd;
    }

    public  boolean InserTradicional(Dto datos){

        boolean estado =true;
        int resultado;
        //SQLiteDatabase bd = this.getWritableDatabase();
        try {
            int codigo = datos.getCodigo();
            String descripcion = datos.getDescripcion();
            double precio = datos.getPrecio();

            Cursor fila = bd().rawQuery("select codigo from articulos where codigo='"+codigo+"'",null );
            if (fila.moveToFirst()==true){
                estado = false;
            }else {
               String SQL = "INSERT INTO articulos \n" +
               "(codigo,descripcion,precio)\n" +
               "VALUES\n"+
               "('"+ String.valueOf(codigo) +"','" + descripcion + "','" +
           String.valueOf(precio)+ "');";
               bd().execSQL(SQL);
                bd().close();

                estado = true;
            }
        }catch (Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }
        return  estado;

        //ME QUEDE A LA MITAD DE LA PAGINA 24-----------------
    }
}
