package com.imagenprogramada.mediaplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Medios {

    //Medios cargados desde archivo
    public static List<Medio> ITEMS = new ArrayList<Medio>();
    public static String selectedDate;

    //cargar los medios desde un json
    public static void loadMediosFromJSON(Context c) {

        String json = null;
        try {
            InputStream is =
                    c.getAssets().open("recursosList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray recursosList = jsonObject.getJSONArray("recursos_list");
            for (int i = 0; i < recursosList.length(); i++) {
                JSONObject jsonMedio = recursosList.getJSONObject(i);
                String nombre = jsonMedio.getString("nombre");
                String descripcion = jsonMedio.getString("descripcion");
                String tipo=jsonMedio.getString("tipo");
                String URI=jsonMedio.getString("URI");
                Bitmap imagen=null;
                try {
                    imagen= BitmapFactory.decodeStream(
                            c.getAssets().open("imagenes/"+
                                    jsonMedio.getString("imagen")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ITEMS.add(new Medios.Medio(nombre,descripcion,tipo,URI,imagen));
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Medio> getItems(boolean verAudio, boolean verVideo, boolean verStreaming) {
        return ITEMS.stream().filter(medio -> {
            switch (medio.getTipo()){
                case Tipos.audio:
                    return verAudio;
                case Tipos.video:
                    return verVideo;
                case Tipos.streaming:
                    return verStreaming;
                default:
                    return true;
            }
        }).collect(Collectors.toList());
    }

    //DTO de bicicleta
    public static class Medio {
        private String nombre;
        private String descripcion;
        private String tipo;
        private String URI;
        private Bitmap imagen;


        public Medio(String nombre, String descripcion, String tipo, String URI, Bitmap imagen) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.tipo = tipo;
            this.URI = URI;
            this.imagen = imagen;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getURI() {
            return URI;
        }

        public void setURI(String URI) {
            this.URI = URI;
        }

        public Bitmap getImagen() {
            return imagen;
        }

        public void setImagen(Bitmap imagen) {
            this.imagen = imagen;
        }

        @Override
        public String toString() {
            return "Medio{" +
                    "nombre='" + nombre + '\'' +
                    ", descripcion='" + descripcion + '\'' +
                    ", tipo='" + tipo + '\'' +
                    ", URI='" + URI + '\'' +
                    ", imagen=" + imagen +
                    '}';
        }
    }
}
