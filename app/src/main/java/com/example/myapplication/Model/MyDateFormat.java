package com.example.myapplication.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateFormat {
    private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    public static Date format(String date){
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String toString(Date date){
        try {
            return df.format(date);
        }
        finally {
            return null;
        }
    }

    public static String Convert(String dt){
        //2020-02-02T45:05:06         dd.mm.yyyy HH:mm:ss
        String tarih = dt.split("T")[0];    //2021-05-06 16:00:00.000
        String yil = tarih.split("-")[0];
        String ay = tarih.split("-")[1];
        String gun = tarih.split("-")[2];
        return gun+"."+ay+"."+yil+" "+dt.split("T")[1];
    }
}
