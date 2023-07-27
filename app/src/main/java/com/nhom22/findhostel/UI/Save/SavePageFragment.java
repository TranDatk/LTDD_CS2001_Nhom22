package com.nhom22.findhostel.UI.Save;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Save_PostService;
import com.nhom22.findhostel.UI.Search.PostDetailFragment;
import com.nhom22.findhostel.databinding.FragmentExtensionPageBinding;
import com.nhom22.findhostel.databinding.FragmentSavePageBinding;

import java.text.ParseException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavePageFragment extends Fragment {

    ListView lvItems;

    List<Posts> items = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SavePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavePageFragment newInstance(String param1, String param2) {
        SavePageFragment fragment = new SavePageFragment();
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
        FragmentSavePageBinding binding = FragmentSavePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        lvItems = view.findViewById(R.id.lvSavedPost);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId > 0) {
            Save_PostService save_postService = new Save_PostService();
            try {
                items = save_postService.getListPostsByUserAccountId(userId);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (!items.isEmpty()) {
                SavedPostAdapter adapter = new SavedPostAdapter(requireContext(), this, items);
                lvItems.setAdapter(adapter);
            }
            else {
                replaceFragment(new SaveNullFragment());
                Toast.makeText(requireContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            replaceFragment(new SaveNullFragment());
            Toast.makeText(requireContext(), "Hãy đăng nhập để lưu bài viết", Toast.LENGTH_SHORT).show();
        }

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle dataBundle = new Bundle();

                dataBundle.putInt("id", items.get(i).getId());


                PostDetailFragment postDetailFragment = new PostDetailFragment();
                postDetailFragment.setArguments(dataBundle);

                replaceFragment(postDetailFragment);
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