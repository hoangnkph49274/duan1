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

import com.example.duan1.DAO.MucTieuDAO;
import com.example.duan1.Model.MucTieu;
import com.example.duan1.Adapter.MucTieuAdapter;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MucTieuFragment extends Fragment {

    private RecyclerView recyclerView;
    private MucTieuAdapter adapter;
    private List<MucTieu> mucTieuList;
    private EditText edtSearchMucTieu;
    private ImageView btnSearchMucTieu;
    private FloatingActionButton fabAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_muc_tieu, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMucTieu);
        fabAdd = view.findViewById(R.id.fabAddMucTieu);
        edtSearchMucTieu = view.findViewById(R.id.edtSearchMucTieu);
        btnSearchMucTieu = view.findViewById(R.id.btnSearchMucTieu);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Tạo dữ liệu mẫu cho mucTieuList
        MucTieuDAO mucTieuDAO = new MucTieuDAO(getContext());
        mucTieuList = mucTieuDAO.getAllMucTieu();
        // Thêm các mục tiêu khác tại đây

        adapter = new MucTieuAdapter(getContext(), mucTieuList);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_MucTieu_to_nav_ThemMucTieu);
            }

        });
        btnSearchMucTieu.setOnClickListener(v ->{
            String keyword = edtSearchMucTieu.getText().toString().trim();
            if(!keyword.isEmpty()){
                List<MucTieu> filteredMucTieuList = mucTieuDAO.searchMucTieu(keyword);
                adapter.updateData(filteredMucTieuList);
                adapter.notifyDataSetChanged();
            }else{
                List<MucTieu> filteredMucTieuList = mucTieuDAO.getAllMucTieu();
                adapter.updateData(filteredMucTieuList);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}
