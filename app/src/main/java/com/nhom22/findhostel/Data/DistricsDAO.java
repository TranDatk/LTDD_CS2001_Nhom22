package com.nhom22.findhostel.Data;

import android.content.Context;

public class DistricsDAO {
    private DatabaseHelper dbHelper;

    public DistricsDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
}
