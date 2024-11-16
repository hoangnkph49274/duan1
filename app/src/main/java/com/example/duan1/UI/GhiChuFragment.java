package com.example.duan1.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1.Adapter.GhiChu;
import com.example.duan1.Adapter.GhiChuAdapter;
import com.example.duan1.Adapter.MonHoc;
import com.example.duan1.Adapter.MonHocAdapter;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class GhiChuFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private GhiChuAdapter adapter;
    private List<GhiChu> ghiChuList;

    public GhiChuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ghi_chu, container, false);

        // Initialize RecyclerView and FloatingActionButton
        recyclerView = view.findViewById(R.id.recyclerViewGhiChu);
        fabAdd = view.findViewById(R.id.fabAddGhiChu);

        // Initialize data
        ghiChuList = new ArrayList<>();
        ghiChuList.add(new GhiChu("ghichu1","Ghi Chú 1","11/12/2024"));
        ghiChuList.add(new GhiChu("ghichu2","Ghi Chú 2","12/12/2024"));
        // Add more courses as needed

        // Set up the RecyclerView
        adapter = new GhiChuAdapter(getContext(),ghiChuList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Set up the FloatingActionButton
        fabAdd.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_nav_GhiChu_to_nav_ThemGhiChu);
        });

        return view;
    }

}