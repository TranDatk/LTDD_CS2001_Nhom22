package com.nhom22.findhostel.UI.Extension;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.HostelCollection;
import com.nhom22.findhostel.Model.PostDecor;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.AddressService;
import com.nhom22.findhostel.Service.HotelCollectionService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.YourApplication;
import com.nhom22.findhostel.databinding.FragmentHostelCollectionPageAddBinding;
import com.nhom22.findhostel.databinding.FragmentHostelCollectionPageBinding;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class HostelCollectionPageFragment extends Fragment {
    public static DatabaseHelper dataBase;
    ListView lsvItem;
    List<HostelCollection> arrItem;
    ItemHotelCollectionAdapter itemAdapter;
    private final AddressService addressService = new AddressService();

    private final HotelCollectionService hotelCollectionService = new HotelCollectionService();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHostelCollectionPageBinding binding = FragmentHostelCollectionPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        dataBase = new DatabaseHelper(YourApplication.getInstance().getApplicationContext());

        lsvItem = binding.lvHotelCollection;
        arrItem = hotelCollectionService.getAllHostelCollection();
        itemAdapter = new ItemHotelCollectionAdapter(YourApplication.getInstance().getApplicationContext(), R.layout.item_hostel_collection_layout, arrItem);
        lsvItem.setAdapter(itemAdapter);

        itemAdapter.notifyDataSetChanged();

        binding.btnAddHotelCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HostelCollectionPageAddFragment());
            }
        });

        binding.lvHotelCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HostelCollection item = itemAdapter.getItem(position);
//                TextView textView = view.findViewById(R.id.tv_DistrictHotelCollection);
//                String text = textView.getText().toString();
//                int itemId = item.getId();
//                String itemTitle = item.getTitle();
                int itemDistrict = item.getAddress();
                Address address = addressService.getAddressById(itemDistrict);

                dataBase = new DatabaseHelper(YourApplication.getInstance().getApplicationContext());



//                int itemSubDistrict = address.getSubDistrics().getId();
//                String text = String.valueOf(itemSubDistrict);
//                Toast.makeText(getContext(), String.valueOf(text), Toast.LENGTH_SHORT).show();

                ListPostsFragement fragment = new ListPostsFragement();
                Bundle bundle = new Bundle();
                bundle.putString("key", "6");
                fragment.setArguments(bundle);

                replaceFragment(fragment);
            }
        });

        binding.imgBtnBackExtensionFromHostelCollectionPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ExtensionPageFragment());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }



}