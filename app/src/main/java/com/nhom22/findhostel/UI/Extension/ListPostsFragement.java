package com.nhom22.findhostel.UI.Extension;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.UI.Search.PostDetailFragment;
import com.nhom22.findhostel.YourApplication;
import com.nhom22.findhostel.databinding.FragmentListPostsBinding;

import java.text.ParseException;
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
//        binding.tvAddressIdLp.setText(subDistric_id);


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

        if(arrItem.isEmpty() && arrItem == null){
            Toast.makeText(getContext(), "Hệ thống đang lỗi không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
        }else {
            lsvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // Tạo Bundle và chuyển dữ liệu cần truyền vào
                    Bundle dataBundle = new Bundle();

                    dataBundle.putInt("id", arrItem.get(i).getId());

                    // Tạo Fragment mới và gắn Bundle vào Fragment
                    PostDetailFragment postDetailFragment = new PostDetailFragment();
                    postDetailFragment.setArguments(dataBundle);

                    // Thực hiện thay thế Fragment hiện tại bằng Fragment mới có dữ liệu được truyền
                    replaceFragment(postDetailFragment);
                }
            });
        }

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}