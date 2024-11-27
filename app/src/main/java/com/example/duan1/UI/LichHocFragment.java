package com.example.duan1.UI;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1.Model.LichHoc;
import com.example.duan1.Adapter.LichHocAdapter;
import com.example.duan1.R;
import com.example.duan1.DAO.LichHocDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LichHocFragment extends Fragment {
    private RecyclerView recyclerView;
    private LichHocAdapter adapter;
    private ArrayList<LichHoc> lichHocList;
    private FloatingActionButton fabAdd;
    private LichHocDAO lichHocDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lich_hoc, container, false);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewLichHoc);
        fabAdd = view.findViewById(R.id.fabAddLichHoc);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo DAO
        lichHocDAO = new LichHocDAO(getContext());

        // Lấy dữ liệu từ DAO
        lichHocList = (ArrayList<LichHoc>) lichHocDAO.getAllLichHoc();

        // Khởi tạo Adapter và gán vào RecyclerView
        adapter = new LichHocAdapter(lichHocList);
        recyclerView.setAdapter(adapter);

        // Lắng nghe sự kiện nhấn FloatingActionButton
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_LichHoc_to_nav_ThemLichHoc);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
