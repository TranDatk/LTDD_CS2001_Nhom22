package com.nhom22.findhostel.Data;

import android.content.Context;

public class CitiesDAO {
    private DatabaseHelper dbHelper;

    public CitiesDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
}
