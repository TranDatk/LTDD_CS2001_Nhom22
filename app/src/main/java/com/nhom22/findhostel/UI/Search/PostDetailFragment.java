package com.nhom22.findhostel.UI.Search;

import android.content.Context;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Detail_Utilities;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.Save_Post;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Detail_FurnitureService;
import com.nhom22.findhostel.Service.Detail_ImageService;
import com.nhom22.findhostel.Service.Detail_UtilitiesService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.Service.Save_PostService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostDetailFragment extends Fragment {
    Posts p;

    List<Save_Post> l;

    private List<Detail_Furniture> furs;

    List<Detail_Utilities> utis;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostDetailFragment newInstance(String param1, String param2) {
        PostDetailFragment fragment = new PostDetailFragment();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        TextView tvDescription = view.findViewById(R.id.tvDescription);
        TextView tvType = view.findViewById(R.id.tvType);
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        TextView tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        TextView tvBed = view.findViewById(R.id.tvBed);
        TextView tvShower = view.findViewById(R.id.tvShower);
        TextView tvDateCounter = view.findViewById(R.id.tvDateCounter);
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        ImageButton btnSave = view.findViewById(R.id.btnSave);
        ImageButton btnCall = view.findViewById(R.id.btnCall);
        ImageButton btnSms = view.findViewById(R.id.btnSms);
        ViewPager imageViewPager = view.findViewById(R.id.imageViewPager);
        ImageView imgAvatar = view.findViewById(R.id.imgAvatar);
        GridView gvFurniture = view.findViewById(R.id.gvFurniture);
        GridView gvUtilities = view.findViewById(R.id.gvUtilities);

        Bundle dataBundle = getArguments();
        if (dataBundle != null) {
            int id =dataBundle.getInt("id");
            PostsService postsService = new PostsService();
            try {
                p = postsService.getPostById(id);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            tvDescription.setText(p.getDescription());
            tvType.setText(p.getType().getName());
            tvAddress.setText(p.getAddress().getHouseNumber() + ", " +
                    p.getAddress().getStreets().getName() + ", " +
                    p.getAddress().getSubDistrics().getName() + ", " +
                    p.getAddress().getDistricts().getName() + ", " +
                    p.getAddress().getCities().getName());
            tvPrice.setText(String.valueOf(p.getPrice()) + "đ");
            tvPhoneNumber.setText(p.getUserAccount().getPhone());
            Date from = p.getTimeFrom();
            Date to = p.getTimeTo();
            long diffInMillis = to.getTime() - from.getTime();
            long daysdiff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

            tvDateCounter.setText("Bài đăng còn " + String.valueOf(daysdiff) + " ngày");

            Detail_FurnitureService detail_furnitureService = new Detail_FurnitureService();
            try {
                furs = detail_furnitureService.getListDetailFurnitureByPostId(id);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (furs != null && !furs.isEmpty()) {
                for (int x = 0; x < furs.size(); x++) {
                    if(furs.get(x).getFurniture().getName().equals("Giường")) {
                        tvBed.setText(String.valueOf(furs.get(x).getQuantity()));
                        tvShower.setText(String.valueOf(furs.get(x).getQuantity()));
                    }
                }
                FurnitureAdapter adapter = new FurnitureAdapter(this, furs);
                gvFurniture.setAdapter(adapter);
            }

            Detail_UtilitiesService detail_utilitiesService = new Detail_UtilitiesService();
            try {
                utis = detail_utilitiesService.getListDetailUtilitiesByPostId(id);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (utis != null && !utis.isEmpty()) {
                UtilitiesAdapter adapter = new UtilitiesAdapter(this, utis);
                gvUtilities.setAdapter(adapter);
            }

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                    int userId = sharedPreferences.getInt("userId", -1);
                    boolean checkSavedPost = false;
                    if (userId > 0) {
                        Save_PostService save_postService = new Save_PostService();
                        try {
                            l = save_postService.getListSavePostByUserAccountId(userId);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        if (l != null && !l.isEmpty()) {
                            for (int x = 0; x < l.size(); x++) {
                                if (l.get(x).getPosts().getId() == id) {
                                    checkSavedPost = true;
                                    break;
                                }
                            }
                            if (checkSavedPost == true) {
                                Toast.makeText(getContext(), "Bạn đã lưu bài viết này", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                try {
                                    save_postService.addASavePost(id, userId);
                                    Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                                    btnSave.setClickable(false);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        else {
                            try {
                                save_postService.addASavePost(id, userId);
                                Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                                btnSave.setClickable(false);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Hãy đăng nhập để lưu bài viết", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + p.getUserAccount().getPhone()));
                    startActivity(intent);
                }
            });

            btnSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:" + p.getUserAccount().getPhone()));
                    startActivity(intent);
                }
            });

            Detail_ImageService detail_imageService = new Detail_ImageService();
            List<Images> images = null;
            try {
                images = detail_imageService.getListImageByPostsId(id);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (images != null && !images.isEmpty()) {
                ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(getContext(), images);
                imageViewPager.setAdapter(imageSliderAdapter);
            }
            byte[] avatar = p.getUserAccount().getImage();
            if (avatar != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
                imgAvatar.setImageBitmap(bitmap);
            }

        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        return view;
    }

}