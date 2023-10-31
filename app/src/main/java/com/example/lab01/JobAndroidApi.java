package com.example.lab01;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.lab01.modelos.BDSesiones.BDSesion;
import com.example.lab01.modelos.ConsumoApi.ConstantesConsumo;
import com.example.lab01.modelos.ConsumoApi.IPlaceHolderConsumer;
import com.example.lab01.modelos.Entidades.ApiServidores;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobAndroidApi extends JobService{

    public boolean jobCancelled = false;
    private boolean encontrada = false;
    private int contador = 0;
    JobAndroidApi jobAndroidApi;
    private final static int NOTIFICACION_ID = 1;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private JobParameters jobParameters = null;
    private LinkedList<ApiServidores> servidores = new LinkedList<>();

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("TAG", "OnStartJob");
        jobParameters = params;
        doBackWork(params);
        return false;
    }

    private void doBackWork(JobParameters params) {
        Log.i("TAG", "doBackWord");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getServidores();
                Log.i("TAG", "RUN: " + servidores.size());
                for(int i=0; i< servidores.size(); i++){
                    if(jobCancelled){
                        return;
                    }
                    Log.i("RAG", "Vamos por: " + i);
                    if(servidores.get(i).getDependencia().toString().equals("SISTEMAS")){
                        encontrada = true;
                        createNotificacionChanel();
                        createNotificacion();
                        encontrada = false;
                        BDSesion almacenSesiones = new BDSesion(getApplicationContext());
                        if(servidores.get(i).getN_serie() == null ||
                                servidores.get(i).getModelo() == null){
                            almacenSesiones.guardarInformacionApi(
                                    servidores.get(i).getDependencia().toString(),
                                    "No Contiene",
                                    "No Contiene",
                                    "No Contiene",
                                    servidores.get(i).getSo().toString(),
                                    servidores.get(i).getLic().toString()
                            );
                        }else{
                            almacenSesiones.guardarInformacionApi(
                                    servidores.get(i).getDependencia().toString(),
                                    servidores.get(i).getMarca().toString(),
                                    servidores.get(i).getModelo().toString(),
                                    servidores.get(i).getN_serie().toString(),
                                    servidores.get(i).getSo().toString(),
                                    servidores.get(i).getLic().toString());
                        }
                    }
                }

                Log.i("TAG", "Job Finished");
                jobFinished(params, false);
                if(contador == 0){
                    onStartJob(params);
                }else{
                    try{
                        Thread.sleep(5 * 60 * 1000);
                        onStartJob(params);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                contador++;
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("TAG", "OnStopJob");

        jobCancelled = true;
        return false;
    }


    private void createNotificacionChanel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotificacion() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_api);
        builder.setContentTitle("Notificacion INTEGRA SERVICES");
        builder.setContentText("No se encontro dependencia");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        if(encontrada = true){
            builder.setContentText("Dependencia ENCONTRADA");
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }

    private void getServidores(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesConsumo.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPlaceHolderConsumer iPlaceHolderConsumer = retrofit.create(IPlaceHolderConsumer.class);
        Call<LinkedList<ApiServidores>> call = iPlaceHolderConsumer.getServidores();

        call.enqueue(new Callback<LinkedList<ApiServidores>>() {
            @Override
            public void onResponse(Call<LinkedList<ApiServidores>> call,
                                   Response<LinkedList<ApiServidores>> response) {

                if (!response.isSuccessful()){
                    Log.i("Error: " , "No respuesta");
                    return;
                }
                servidores = response.body();
            }
            @Override
            public void onFailure(Call<LinkedList<ApiServidores>> call, Throwable t) {
                Log.i("Error: " , "No respuesta\n" + t + "\n" + call);

            }
        });
    }
}



