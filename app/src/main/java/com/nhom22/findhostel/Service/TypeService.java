package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.TypeDAO;
import com.nhom22.findhostel.Model.Type;
import com.nhom22.findhostel.YourApplication;

public class TypeService {
    private final static TypeDAO TYPE_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        TYPE_DAO = new TypeDAO(appContext);
    }

    public Type getTypeById(int typeId) {
        if(typeId >= 0){
            return TYPE_DAO.getTypeById(typeId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null typeId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }
}
