package com.nhom22.findhostel.Service;

import android.content.Context;
import android.widget.Toast;

import com.nhom22.findhostel.Data.AddressDAO;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.YourApplication;

public class AddressService {
    private final static AddressDAO ADDRESS_DAO;

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

    public long addAddress(Address address){
        if(address != null){
            return ADDRESS_DAO.addAddress(address);
        }
        else {
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
