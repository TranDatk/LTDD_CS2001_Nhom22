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
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.HotelCollectionService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.YourApplication;
import com.nhom22.findhostel.databinding.FragmentExtensionPageBinding;
import com.nhom22.findhostel.databinding.FragmentListPostsBinding;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class ListPostsFragement extends Fragment {

    private String subDistric_id;
    public static DatabaseHelper dataBase;
    ListView lsvItem;
    List<Posts> arrItem;
    ItemPostsHostelAdapter itemAdapter;
    private final PostsService postsService = new PostsService();
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
        try {
            arrItem = postsService.getPostsBySubDistrics(Integer.parseInt(subDistric_id));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        itemAdapter = new ItemPostsHostelAdapter(YourApplication.getInstance().getApplicationContext(), R.layout.item_search_post, arrItem);
        lsvItem.setAdapter(itemAdapter);


        itemAdapter.notifyDataSetChanged();


        return view;
    }
}