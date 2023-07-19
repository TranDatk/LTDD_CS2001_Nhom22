package com.nhom22.findhostel.UI.Search;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Detail_ImageService;
import com.nhom22.findhostel.Service.PostsService;

import java.text.ParseException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostDetailFragment extends Fragment {
    Posts p;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        TextView tvDescription = view.findViewById(R.id.tvDescription);
        TextView tvType = view.findViewById(R.id.tvType);
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        TextView tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        Button btnCall = view.findViewById(R.id.btnCall);
        Button btnSms = view.findViewById(R.id.btnSms);
        ViewPager imageViewPager = view.findViewById(R.id.imageViewPager);
        ImageView imgAvatar = view.findViewById(R.id.imgAvatar);

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
            tvPrice.setText(String.valueOf(p.getPrice()));
            tvPhoneNumber.setText(p.getUserAccount().getPhone());

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

        TextView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        return view;
    }

}