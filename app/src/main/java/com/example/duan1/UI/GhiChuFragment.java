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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duan1.DAO.GhiChuDAO;
import com.example.duan1.Model.GhiChu;
import com.example.duan1.Adapter.GhiChuAdapter;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GhiChuFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private GhiChuAdapter adapter;
    private List<GhiChu> ghiChuList;
    private EditText edtSearchGhiChu;
    private ImageView btnSearchGhiChu;
    private GhiChuDAO ghiChuDAO; // Khởi tạo DAO

    public GhiChuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ghi_chu, container, false);

        // Khởi tạo RecyclerView và FloatingActionButton
        recyclerView = view.findViewById(R.id.recyclerViewGhiChu);
        fabAdd = view.findViewById(R.id.fabAddGhiChu);
        edtSearchGhiChu = view.findViewById(R.id.edtSearchGhiChu);
        btnSearchGhiChu = view.findViewById(R.id.btnSearchGhiChu);

        // Khởi tạo DAO và lấy dữ liệu từ cơ sở dữ liệu
        ghiChuDAO = new GhiChuDAO(getContext());
        ghiChuList = ghiChuDAO.getAllGhiChu(); // Lấy dữ liệu từ CSDL

        // Nếu không có dữ liệu, tạo danh sách rỗng
        if (ghiChuList == null) {
            ghiChuList = new ArrayList<>();
        }

        // Thiết lập RecyclerView
        adapter = new GhiChuAdapter(getContext(), ghiChuList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Thiết lập sự kiện cho FloatingActionButton
        fabAdd.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_nav_GhiChu_to_nav_ThemGhiChu);
        });

        btnSearchGhiChu.setOnClickListener(v -> {
            String keyword = edtSearchGhiChu.getText().toString().trim();
            if(!keyword.isEmpty()){
                List<GhiChu> searchResults = ghiChuDAO.searchGhiChu(keyword);
                adapter.updateData(searchResults);
                adapter.notifyDataSetChanged();
            }else{
                List<GhiChu> searchResults = ghiChuDAO.getAllGhiChu();
                adapter.updateData(searchResults);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}
