package com.example.lab01.ui.serversApi;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

import android.app.job.JobScheduler;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lab01.R;
import com.example.lab01.modelos.BDSesiones.AlmacenSesiones;
import com.example.lab01.modelos.ConsumoApi.ConstantesConsumo;
import com.example.lab01.modelos.ConsumoApi.IPlaceHolderConsumer;
import com.example.lab01.modelos.Entidades.ApiServidores;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServersFragment extends Fragment {

    public static AlmacenSesiones almacenSesiones;
    private RecyclerView rv_api;
    private AdaptadorRV adaptadorRV;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton fab_stop;
    private final static int ID_SERVICIO = 99;
    private LinkedList<ApiServidores> servidores = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servers, container, false);
        rv_api = view.findViewById(R.id.rv_api);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_api.setLayoutManager(linearLayoutManager);
        fab_stop = view.findViewById(R.id.fab_stop);
        getServidores();
        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
                jobScheduler.cancel(ID_SERVICIO);
                jobScheduler.cancelAll();
                Toast.makeText(getActivity(), "Servicio en Stop", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
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
                adaptadorRV = new AdaptadorRV(servidores, getActivity());
                rv_api.setAdapter(adaptadorRV);

            }

            @Override
            public void onFailure(Call<LinkedList<ApiServidores>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error al obtener los datos\n" + t + "\n" +
                        call, Toast.LENGTH_SHORT).show();
            }
        });
    }
}