package com.gkk.candycoded_gk.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CandyDbHelper extends SQLiteOpenHelper {
    public CandyDbHelper(@Nullable Context context, int version) {
        super(context, CandyContract.DB_NAME, null, version);
    }

    public CandyDbHelper(Context context) {
        super(context,
                CandyContract.DB_NAME,
                null,
                CandyContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CandyContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(CandyContract.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
