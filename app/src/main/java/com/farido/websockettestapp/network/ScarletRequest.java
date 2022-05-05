package com.farido.websockettestapp.network;

import com.farido.websockettestapp.util.Constants;
import com.tinder.scarlet.MessageAdapter;
import com.tinder.scarlet.Scarlet;
import com.tinder.scarlet.StreamAdapter;
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter;
import com.tinder.scarlet.retry.BackoffStrategy;
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory;
import com.tinder.scarlet.websocket.okhttp.OkHttpClientUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ScarletRequest {
    private static Scarlet scarlet;

    public static Scarlet getScarletInstance() {
        if (scarlet == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .pingInterval(10, TimeUnit.SECONDS)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();

            Object gsonMessageAdapterFactories = new GsonMessageAdapter.Factory();
            Object rxJava2StreamAdapterFactory = new RxJava2StreamAdapterFactory();

            scarlet = new Scarlet.Builder()
                    .webSocketFactory(OkHttpClientUtils.newWebSocketFactory(okHttpClient, Constants.webSocketUrl))
                    .addMessageAdapterFactory((MessageAdapter.Factory) gsonMessageAdapterFactories)
                    .addStreamAdapterFactory((StreamAdapter.Factory) rxJava2StreamAdapterFactory)
                    //.backoffStrategy()
                    //.lifecycle()
                    .build();
        }
        return scarlet;
    }

}
