package com.nhom22.findhostel.UI.Extension;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Model.HostelCollection;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.HotelCollectionService;
import com.nhom22.findhostel.YourApplication;
import com.nhom22.findhostel.databinding.FragmentExtensionPageBinding;
import com.nhom22.findhostel.databinding.FragmentListPostsBinding;

import java.util.ArrayList;


public class ListPostsFragement extends Fragment {

    private String subDistric_id;
    public static DatabaseHelper dataBase;
    ListView lsvItem;
    ArrayList<HostelCollection> arrItem;
    ItemHotelCollectionAdapter itemAdapter;
    private final HotelCollectionService hotelCollectionService = new HotelCollectionService();
    public ListPostsFragement() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentListPostsBinding binding = FragmentListPostsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        Bundle bundle = getArguments();
        if (bundle != null) {
            subDistric_id = bundle.getString("key");
            // Sử dụng dữ liệu ở đây
        }
        binding.tvAddressIdLp.setText(subDistric_id);


        dataBase = new DatabaseHelper(YourApplication.getInstance().getApplicationContext());

        lsvItem = binding.lvHotelCollection;
        arrItem = new ArrayList<>();
        itemAdapter = new ItemHotelCollectionAdapter(YourApplication.getInstance().getApplicationContext(), R.layout.item_hostel_collection_layout, arrItem);
        lsvItem.setAdapter(itemAdapter);

        Cursor cursor = dataBase.GetData("SELECT hc.* \n" +
                "FROM hostel_collection hc\n" +
                "INNER JOIN address a ON hc.address_id = a.id\n" +
                "WHERE a.sub_districts_id = " + subDistric_id);
        while(cursor.moveToNext()){
            arrItem.add(new HostelCollection(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getBlob(2),
                    cursor.getInt(3)
            ));
        }
        itemAdapter.notifyDataSetChanged();


        return view;
    }
}