package com.nhom22.findhostel.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.HostelCollection;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.YourApplication;

public class HostelCollectionDAO {
    private DatabaseHelper dbHelper;

    public HostelCollectionDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addHostelCollection(HostelCollection hostelCollection) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("text", hostelCollection.getTitle());
        values.put("image",hostelCollection.getImage());
        values.put("address_id",hostelCollection.getAddress());

        long id = db.insert("hostel_collection", null, values);

        db.close();

        return id;
    }


    @SuppressLint("Range")
    public HostelCollection getHostelCollectionById(Integer id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "text",
                "image",
                "address_id"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query("user_account", columns, selection, selectionArgs, null, null, null);
        HostelCollection hostelCollection = null;

        if (cursor != null && cursor.moveToFirst()) {
            String text = cursor.getString( cursor.getColumnIndex("text"));
            byte[] image = cursor.getBlob( cursor.getColumnIndex("image"));
            int address_id = cursor.getInt(cursor.getColumnIndex("address_id"));


            hostelCollection = new HostelCollection(id,text, image, address_id);
            cursor.close();
        }
        db.close();

        return hostelCollection;
    }
}
