package com.farido.websockettestapp.network;

import com.tinder.scarlet.WebSocket;
import com.tinder.scarlet.ws.Receive;

import io.reactivex.Flowable;

public interface WebsocketService {
    @Receive
    Flowable<WebSocket.Event> observeWebSocketEvent();
}