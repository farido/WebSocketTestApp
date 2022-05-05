package com.farido.websockettestapp.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.farido.websockettestapp.db.entity.CompanyDBEntity;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface CompanyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCompany(CompanyDBEntity companyDBEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCompanies(List<CompanyDBEntity> companyDBEntityList);

    @Query("SELECT * FROM companies")
    Maybe<List<CompanyDBEntity>> getCompanies();

}
