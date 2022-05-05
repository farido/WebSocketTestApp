package com.farido.websockettestapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.farido.websockettestapp.db.entity.CompanyDBEntity;
import com.farido.websockettestapp.repository.CompanyRepository;
import com.farido.websockettestapp.util.Util;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tinder.scarlet.WebSocket;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CompanyViewModel extends AndroidViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private CompanyRepository companyRepository;

    public MutableLiveData<List<CompanyDBEntity>> companyList = new MutableLiveData<List<CompanyDBEntity>>();
    public MutableLiveData<Boolean> status = new MutableLiveData<Boolean>();

    public CompanyViewModel(@NonNull Application application) {
        super(application);
        companyRepository = new CompanyRepository(application);
    }

    public void startWebsocketService() {
        disposable.add(
                companyRepository.getMyWebsocketService()
                        .observeWebSocketEvent()
                        .observeOn(Schedulers.io())
                        .subscribe(event -> {
                            if (event instanceof WebSocket.Event.OnConnectionOpened) {
                                //Log.i("Websocket", "Opened");

                                status.postValue(true);

                            } else if (event instanceof WebSocket.Event.OnConnectionClosing) {
                                //Log.i("Websocket", "Closing");

                                status.postValue(false);

                            } else if (event instanceof WebSocket.Event.OnConnectionClosed) {
                                //Log.i("Websocket", "Closed");

                                status.postValue(false);

                            } else if (event instanceof WebSocket.Event.OnConnectionFailed) {
                                //Log.i("Websocket", "Failed");

                                if (companyList.getValue() == null)
                                    loadFromDB();

                                status.postValue(false);
                            } else if (event instanceof WebSocket.Event.OnMessageReceived) {
                                //Log.i("Websocket","Message: " +  ((WebSocket.Event.OnMessageReceived) event).getMessage().toString());
                                String message = ((WebSocket.Event.OnMessageReceived) event).getMessage().toString();
                                message = Util.normalizeMessage(message);

                                //Log.i("Websocket", "Message: " + message);

                                if (message.length() > 0) {
                                    JsonElement jsonElement = JsonParser.parseString(message);
                                    if (jsonElement != null) {
                                        try {
                                            if (jsonElement.isJsonArray()) {
                                                List<CompanyDBEntity> companyDBEntityList = Util.getPojoFromMessage(jsonElement);

                                                companyList.postValue(companyDBEntityList);

                                                saveToDB(companyDBEntityList);

                                                status.postValue(null);
                                            }
                                        } catch (Exception e) {
                                            Log.e("socket", e.getMessage());
                                        }
                                    }
                                }
                            }
                        })
        );
    }

    public void saveToDB(List<CompanyDBEntity> companyDBEntityList) {
        disposable.add(
                companyRepository.insertCompanies(companyDBEntityList)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(/*() -> Log.d("mydb", "Successfully acquired repositories")*/)

        );
    }

    public void loadFromDB() {
        disposable.add(
                companyRepository.getCompanies()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(companyDBList -> companyList.postValue(companyDBList))
                        .doOnComplete(() -> companyList.postValue(new ArrayList<CompanyDBEntity>()))
                        .subscribe()
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
