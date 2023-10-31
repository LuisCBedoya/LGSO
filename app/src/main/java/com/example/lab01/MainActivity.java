package com.example.lab01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText userName, password;
    private Button btnLogin;
    private ProgressBar progressBar;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    private final static int ID_SERVICIO = 99;
    private String name, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        userName = (EditText) findViewById(R.id.editTextUserName);
        password = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        progressBar = (ProgressBar) findViewById(R.id.progress_Bar);

        userName.setText("user");
        password.setText("user");
        //createNotificacionChanel();
        //createNotificacion();

        ComponentName componentName = new ComponentName(getApplicationContext(), JobAndroidApi.class);
        JobInfo info;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            info = new JobInfo.Builder(ID_SERVICIO, componentName)
                    .setPersisted(true)
                    .setMinimumLatency(1000)
                    .build();
        }else{
            info = new JobInfo.Builder(ID_SERVICIO, componentName)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .setPeriodic(5 * 60 * 1000)
                    .build();
        }

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultado = scheduler.schedule(info);
        if(resultado == JobScheduler.RESULT_SUCCESS){
            Log.i("TAG", "Job Acabado");
        }else{
            Log.i("TAG", "Job ha fallado");
        }
    }

    private void createNotificacionChanel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotificacion() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_alarm);
        builder.setContentTitle("Notificacion INTEGRA SERVICES");
        builder.setContentText("Esta es una prueba");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }

    public void doLogin(View view){
        new LoginTask().execute(userName.getText().toString());
        name = userName.getText().toString();
        pass = password.getText().toString();
    }

    public void acceso(){

        if(name.equals("user") && pass.equals("user")){
            Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Failed access", Toast.LENGTH_LONG).show();
        }
      }

    class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                name = userName.getText().toString();
                pass = password.getText().toString();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
            btnLogin.setEnabled(true);
            acceso();
        }
    }
}