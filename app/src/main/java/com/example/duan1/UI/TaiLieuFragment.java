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

import com.example.duan1.Adapter.MonHoc;
import com.example.duan1.Adapter.MonHocAdapter;
import com.example.duan1.Adapter.TaiLieu;
import com.example.duan1.Adapter.TaiLieuAdapter;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class TaiLieuFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private TaiLieuAdapter adapter;
    private List<TaiLieu> taiLieuList;

    public TaiLieuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tai_lieu, container, false);

        // Initialize RecyclerView and FloatingActionButton
        recyclerView = view.findViewById(R.id.recyclerViewTaiLieu);
        fabAdd = view.findViewById(R.id.fabAddTaiLieu);

        // Initialize data
        taiLieuList = new ArrayList<>();
        taiLieuList.add(new TaiLieu("TL1","Lập trình C","Bài giảng","https://www.google.com/","Lập trình C"));
        taiLieuList.add(new TaiLieu("TL2","Lập trình Java","Tham khảo","https://www.google.com/","Lập trình Java"));
        // Add more courses as needed

        // Set up the RecyclerView
        adapter = new TaiLieuAdapter(taiLieuList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Set up the FloatingActionButton
        fabAdd.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_nav_QlyTaiLieu_to_nav_ThemTaiLieu);
        });

        return view;
    }
}