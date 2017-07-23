package com.burgers.raffy.utils;

import android.os.Environment;

/**
 * Created by Neil on 5/21/2017.
 */

public class Constants {
    public static final int GENERATE = 0;
    public static final int CHECK = 1;
    public static final String PATH = Environment.getExternalStorageDirectory()+"/RaffysBurger/";

    //database
    public static final String COLUMN_NAME="NAME";
    public static final String COLUMN_AMOUNT="AMOUNT";
    public static final String TABLE_NAME="GiftCerts";
    public static final String DATABASE_NAME = "Raffys.db";
    public static final String TABLE_ID = "ID";
    public static final String COLUMN_KEY = "KEY";
    public static final String COLUMN_COLLECT = "COLLECT";

    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    public static final String ADD = "ADD";
    public static final String NAME = "name";
    public static final String AMOUNT = "amount";
    public static final String UPDATE = "UPDATE";
    public static final String KEY = "key";

    //Firebase User
    public static final String ADMIN = "speedshot001@gmail.com";
    public static final String ADMIN_PASSWORD = "superman";
    public static final String GENERAL = "raffysburger@gmail.com";
    public static final String GENERAL_PASSWORD = "pangetSiRaffy";

    //Log
    public static final String LOG = "== GiftCerts ==";

    //firebase
    public static final String WINNERS = "winners";
    public static final String CLAIMED = "claimed";
    public static final String SET_MAIN_ACTIVITY = "com.burgers.raffy.giftcertsserver.SET_MAIN_ACTIVITY";


}
