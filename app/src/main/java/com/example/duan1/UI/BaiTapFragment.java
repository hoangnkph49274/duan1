package com.example.duan1.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.BaiTap;
import com.example.duan1.Adapter.BaiTapAdapter;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BaiTapFragment extends Fragment {

    private RecyclerView recyclerView;
    private BaiTapAdapter adapter;
    private List<BaiTap> baiTapList;
    private FloatingActionButton fabAdd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bai_tap, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBaiTap);
        fabAdd = view.findViewById(R.id.fabAddBaitap);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        baiTapList = new ArrayList<>();
        baiTapList.add(new BaiTap("BT1", "Lập trình C", "11/12/2024", "Đã hoàn thành", "Lập trình C"));
        baiTapList.add(new BaiTap("BT2", "Cấu trúc dữ liệu", "15/12/2024", "Chưa hoàn thành", "Khoa học máy tính"));

        adapter = new BaiTapAdapter(baiTapList);

        recyclerView.setAdapter(adapter);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_QlyBaiTap_to_nav_ThemBaiTap);
            }
        });
        return view;
    }
}
