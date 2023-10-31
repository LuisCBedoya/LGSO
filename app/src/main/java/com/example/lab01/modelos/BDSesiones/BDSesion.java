package com.example.lab01.modelos.BDSesiones;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.lab01.modelos.Entidades.ApiServidores;
import com.example.lab01.modelos.Entidades.Eventos;
import com.example.lab01.modelos.Entidades.Sesiones;

import java.util.LinkedList;

public class BDSesion extends SQLiteOpenHelper implements AlmacenSesiones {
    public BDSesion(@Nullable Context context) {
        super(context,"sessions", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sesiones(" +
                "id INTEGER PRIMARY KEY," +
                "fecha TEXT," +
                "ip TEXT," +
                "estado TEXT)");

        db.execSQL("CREATE TABLE servidores(" +
                "dependencia TEXT," +
                "marca TEXT," +
                "modelo TEXT," +
                "n_serie TEXT," +
                "so TEXT," +
                "lic TEXT )");

        db.execSQL("CREATE TABLE servidor_evento(" +
                "n_serie TEXT," +
                "id TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void guardarSesion(int id, String fecha, String ip, String estado) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO sesiones VALUES (" +
                id +", '" +
                fecha +"', '" +
                ip + "','"+
                estado +"')");
    }

    @Override
    public void mostrarSesion(LinkedList<Sesiones> lista_sesiones) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursorLista;
        cursorLista = db.rawQuery("SELECT * FROM sesiones", null);
        if(cursorLista.moveToFirst()){
            do{
                lista_sesiones.add(new Sesiones(cursorLista.getInt(0),
                        cursorLista.getString(1),
                        cursorLista.getString(2),
                        cursorLista.getString(3)));
            }while (cursorLista.moveToNext());
        }
    }

    @Override
    public void modificarSesion(int id, String fecha, String ip, String estado) {
        SQLiteDatabase db = getWritableDatabase();

        try{
            db.execSQL("UPDATE sesiones SET id = '"+ id + "', fecha = '"+ fecha +"' , " +
                    "ip = '"+ ip +"', estado = '"+ estado + "' WHERE id = '" +
                    id +"'");

        }catch (Exception e){
            e.toString();
        } finally {
            db.close();
        }
    }

    @Override
    public void eliminarSesion(int id) {
        SQLiteDatabase db = getWritableDatabase();

        try{
            db.execSQL("DELETE FROM sesiones WHERE id = '" +
                    id +"'");

        }catch (Exception e){
            e.toString();
        } finally {
            db.close();
        }
    }

    @Override
    public void guardarInformacionApi(String dependencia, String marca, String modelo,
                                      String n_serie, String so, String lic) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO servidores VALUES ('" +
                dependencia +"', '" +
                marca +"', '" +
                modelo + "','"+
                n_serie +"', '" +
                so +"', '"+
                lic +"')");
    }

    @Override
    public void mostrarInformacionApi(LinkedList<ApiServidores> servidores) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursorLista;
        cursorLista = db.rawQuery("SELECT * FROM servidores", null);

        if(cursorLista.moveToFirst()){
            do{
                servidores.add(new ApiServidores(
                        cursorLista.getString(0),
                        cursorLista.getString(1),
                        cursorLista.getString(2),
                        cursorLista.getString(3),
                        cursorLista.getString(4),
                        cursorLista.getString(5)));

            }while (cursorLista.moveToNext());
        }
    }

    @Override
    public void guardarEvento(String n_serie, String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO servidor_evento VALUES ('" +
                n_serie +"', '" +
                id +"')");
    }

    @Override
    public void mostrarEvento(LinkedList<Eventos> eventos) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursorLista;
        cursorLista = db.rawQuery("SELECT * FROM servidor_evento", null);

        if(cursorLista.moveToFirst()){
            do{
                eventos.add(new Eventos(
                        cursorLista.getString(0),
                        cursorLista.getString(1)));
            }while (cursorLista.moveToNext());
        }
    }
}


