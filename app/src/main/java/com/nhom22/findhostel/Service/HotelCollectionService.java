package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.HostelCollectionDAO;
import com.nhom22.findhostel.Data.PostDecorDAO;
import com.nhom22.findhostel.Model.HostelCollection;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.YourApplication;

public class HotelCollectionService {
    private final static HostelCollectionDAO HOSTEL_COLLECTION_DAO;

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        HOSTEL_COLLECTION_DAO = new HostelCollectionDAO(appContext);
    }

    public long addHostelCollection(HostelCollection hostelCollection) {
        if(hostelCollection != null){
            return HOSTEL_COLLECTION_DAO.addHostelCollection(hostelCollection); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null hostelCollection", Toast.LENGTH_SHORT).show();
            return -1; // Return -1 to indicate unsuccessful operation
        }
    }

    public HostelCollection getHostelCollectionById(Integer id) {
        if(id != null){
            return HOSTEL_COLLECTION_DAO.getHostelCollectionById(id); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null hostelCollection", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }
}
