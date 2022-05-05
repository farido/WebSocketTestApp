package com.farido.websockettestapp.repository;

import android.content.Context;

import com.farido.websockettestapp.db.AppDatabase;
import com.farido.websockettestapp.db.entity.CompanyDBEntity;
import com.farido.websockettestapp.network.WebsocketService;
import com.farido.websockettestapp.network.ScarletRequest;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class CompanyRepository {
    private static CompanyRepository instance;
    private WebsocketService websocketService;
    private AppDatabase db;

    /**
     * Singleton pattern
     */

    public CompanyRepository(Context context) {
        db = AppDatabase.getInstance(context);
        websocketService = ScarletRequest.getScarletInstance().create(WebsocketService.class);
    }

    public static CompanyRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CompanyRepository(context);
        }
        return instance;
    }

    /**
     * Impl DAO
     **/

    public Completable insertCompany(CompanyDBEntity companyDBEntity) {
        return db.companyDao().insertCompany(companyDBEntity);
    }

    public Completable insertCompanies(List<CompanyDBEntity> companyDBEntityList) {
        return db.companyDao().insertCompanies(companyDBEntityList);
    }

    public Maybe<List<CompanyDBEntity>> getCompanies(){
        return db.companyDao().getCompanies();
    }

    /**
     * Impl API (from SessionService)
     **/

    public WebsocketService getMyWebsocketService(){
        return websocketService;
    }
}
