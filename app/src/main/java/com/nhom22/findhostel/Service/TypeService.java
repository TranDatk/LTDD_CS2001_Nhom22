package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.TypeDAO;
import com.nhom22.findhostel.Model.Type;
import com.nhom22.findhostel.YourApplication;

import java.util.List;

public class TypeService {
    private final static TypeDAO TYPE_DAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference typeRef = database.getReference("type");

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

    public List<Type> getAllType() {
        List<Type> typeList = TYPE_DAO.getAllType();
        if (typeList != null && !typeList.isEmpty()) {
            return typeList;
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "No types found", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public long addType(Type type) {
        if(type != null){
            typeRef.child(String.valueOf(type.getId())).setValue(type);
            return TYPE_DAO.addType(type); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null type", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public void deleteAllType() {
        TYPE_DAO.deleteAllType();
    }

    public void resetTypeAutoIncrement() {
        TYPE_DAO.resetTypeAutoIncrement();
    }
}
