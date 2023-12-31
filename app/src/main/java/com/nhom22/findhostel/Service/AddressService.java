package com.nhom22.findhostel.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhom22.findhostel.Data.AddressDAO;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.YourApplication;

import java.util.List;

public class AddressService {
    private final static AddressDAO ADDRESS_DAO;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference addressRef = database.getReference("address");

    static {
        Context appContext = YourApplication.getInstance().getApplicationContext();
        ADDRESS_DAO = new AddressDAO(appContext);
    }

    public Address getAddressById(int addressId) {
        if(addressId >= 0){
            return ADDRESS_DAO.getAddressById(addressId); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null addressId", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public List<Address> getAllAddress() {
            return ADDRESS_DAO.getAllAddress();
    }

    public long addAddress(Address address){
        if(address != null){

            long result = ADDRESS_DAO.addAddress(address);
            if(result > 0){
                address.setId(Integer.parseInt(String.valueOf(result)));
                addressRef.child(String.valueOf(address.getId())).setValue(address);
            }else{
                Log.e("addAddress", String.valueOf(result));
            }
            return result;
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Address is null", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    public long updateAddress(Address address){
        if (address != null){
            if (address.getId() > 0) {
                long result = ADDRESS_DAO.updateAddress(address);
                if (result > 0) {
                    addressRef.child(String.valueOf(address.getId())).setValue(address);
                } else {
                    Log.e("updateAddress", String.valueOf(result));
                }
                return result;
            } else {
                return addAddress(address);
            }
        } else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Address is null", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    public Address getAddressByNameStreetAndHouseNumber(String streetName, String houseNumber) {
        if(!streetName.isEmpty() && !houseNumber.isEmpty()){
            return ADDRESS_DAO.getAddressByNameStreetAndHouseNumber(streetName,houseNumber); // -1 Unsuccessful, >0 Successful
        }
        else {
            Context context = YourApplication.getInstance().getApplicationContext();
            Toast.makeText(context, "Null streetName and houseNumber ", Toast.LENGTH_SHORT).show();
            return null; // Return -1 to indicate unsuccessful operation
        }
    }

    public void deleteAllAddress() {
        ADDRESS_DAO.deleteAllAddress();
    }

    public void resetAddressAutoIncrement() {
        ADDRESS_DAO.resetAddressAutoIncrement();
    }
}
