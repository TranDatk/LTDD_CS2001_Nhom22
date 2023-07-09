package com.nhom22.findhostel.UI.Notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhom22.findhostel.databinding.FragmentNotificationPageBinding;


public class NotificationPageFragment extends Fragment {

    FragmentNotificationPageBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNotificationPageBinding binding = FragmentNotificationPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }
}