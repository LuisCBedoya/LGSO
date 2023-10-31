package com.example.lab01.ui.dashboard;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

import android.annotation.SuppressLint;
import android.app.job.JobScheduler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab01.R;
import com.example.lab01.databinding.FragmentDashboardBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class DashboardFragment extends Fragment {

    private FloatingActionButton fab_stop;
    private final static int ID_SERVICIO = 99;
    private TextView tv_file;
    private String cadena = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FileTask fileTask = new FileTask();
        fileTask.execute();
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        fab_stop = view.findViewById(R.id.fab_stop);
        tv_file = view.findViewById(R.id.tv_file);
        tv_file.setText("Archivo\n\n\n");
        tv_file.append(cadena);
        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobScheduler jobScheduler = (JobScheduler) getActivity().getBaseContext().getSystemService(JOB_SCHEDULER_SERVICE);
                jobScheduler.cancel(ID_SERVICIO);
                jobScheduler.cancelAll();
                Toast.makeText(getActivity(), "Servicio en Stop", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    class FileTask extends AsyncTask<Void, Void, String> {

        InputStream inputStream = getResources().openRawResource(R.raw.salidalog);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

        @Override
        protected String doInBackground(Void... voids) {
            String line = "";
            try {
                while ((line = reader.readLine()) != null){
                    cadena = cadena + line + "\n\n";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }
}