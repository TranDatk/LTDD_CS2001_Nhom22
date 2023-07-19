package com.nhom22.findhostel.UI.Search;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Detail_ImageService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.UI.Account.SecondRegisterFragment;
import com.nhom22.findhostel.databinding.FragmentSearchPageBinding;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPageFragment extends Fragment {

    List<Posts> items = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchPageFragment newInstance(String param1, String param2) {
        SearchPageFragment fragment = new SearchPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSearchPageBinding binding = FragmentSearchPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        ListView lvPost = view.findViewById(R.id.lvPost);
        String[] itemsSpinner = {"Mặc định", "Mới nhất", "Giá thấp đến cao", "Giá cao đến thấp"};
        Spinner snOptions = view.findViewById(R.id.snOptions);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, itemsSpinner);
        snOptions.setAdapter(adapterSpinner);
        PostsService postsService = new PostsService();
        try {
            items = postsService.getAllPost();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (!items.isEmpty()) {
            SearchPageAdapter adapter = new SearchPageAdapter(this, items);
            lvPost.setAdapter(adapter);
        }

        lvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Tạo Bundle và chuyển dữ liệu cần truyền vào
                Bundle dataBundle = new Bundle();

                dataBundle.putInt("id", items.get(i).getId());

                // Tạo Fragment mới và gắn Bundle vào Fragment
                PostDetailFragment postDetailFragment = new PostDetailFragment();
                postDetailFragment.setArguments(dataBundle);

                // Thực hiện thay thế Fragment hiện tại bằng Fragment mới có dữ liệu được truyền
                replaceFragment(postDetailFragment);
            }
        });

        snOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (itemsSpinner[i].equals("Mới nhất")) {
                    Toast.makeText(getContext(), "moi nhat", Toast.LENGTH_SHORT).show();
                }

                if (itemsSpinner[i].equals("Giá thấp đến cao")) {
                    Toast.makeText(getContext(), "gia thap den cao", Toast.LENGTH_SHORT).show();
                }

                if (itemsSpinner[i].equals("Giá cao đến thấp")) {
                    Toast.makeText(getContext(), "gia cao den thap", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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