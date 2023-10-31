package com.example.lab01.modelos.ConsumoApi;

import com.example.lab01.modelos.Entidades.ApiServidores;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IPlaceHolderConsumer {
    @GET(ConstantesConsumo.GET_MEDIA_ALL)
    Call<LinkedList<ApiServidores>> getServidores();

}
