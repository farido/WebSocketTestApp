package com.farido.websockettestapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "companies", indices={@Index(value = {"NAME"},unique = true)})
public class CompanyDBEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "DIRECTION")
    private String direction;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "VALUE_1")
    private String value1;

    @ColumnInfo(name = "VALUE_2")
    private String value2;

    @ColumnInfo(name = "VALUE_3")
    private String value3;

    @ColumnInfo(name = "VALUE_4")
    private String value4;

    @ColumnInfo(name = "PARAMETER")
    private String parameter;

    @ColumnInfo(name = "DATETIME")
    private Long dateTime;

    public CompanyDBEntity(String direction, String name,
                           String value1, String value2, String value3, String value4,
                           String parameter, Long dateTime){
        this.direction = direction;
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.parameter = parameter;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }


}
