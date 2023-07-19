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

import java.util.ArrayList;
import java.util.List;

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

        Cursor cursor = db.query("hostel_collection", columns, selection, selectionArgs, null, null, null);
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

    @SuppressLint("Range")
    public List<HostelCollection> getAllHostelCollection() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                "id",
                "text",
                "image",
                "address_id"
        };


        Cursor cursor = db.query("hostel_collection", columns, null, null, null, null, null);
        List<HostelCollection> hostelCollectionList = new ArrayList<>();

        while (cursor != null && cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String text = cursor.getString( cursor.getColumnIndex("text"));
            int address_id = cursor.getInt(cursor.getColumnIndex("address_id"));
            byte[] image = cursor.getBlob( cursor.getColumnIndex("image"));


            HostelCollection hostelCollection = new HostelCollection(id, text, image, address_id);
            hostelCollectionList.add(hostelCollection);
        }
        cursor.close();
        db.close();

        return hostelCollectionList;
    }
}
