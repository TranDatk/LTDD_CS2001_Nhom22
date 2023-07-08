package com.nhom22.findhostel.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom22.findhostel.Model.Cities;

import java.util.ArrayList;
import java.util.List;

public class CitiesFirebase {
    private DatabaseReference databaseReference;

    public CitiesFirebase() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void getCities(final CitiesCallback callback) {
        DatabaseReference citiesRef = databaseReference.child("cities");
        final List<Cities> citiesList = new ArrayList<>();

        citiesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Cities city = snapshot.getValue(Cities.class);
                    citiesList.add(city);
                }
                callback.onCityLoaded(citiesList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public interface CitiesCallback {
        void onCityLoaded(List<Cities> cities);
        void onError(String errorMessage);
    }
}
