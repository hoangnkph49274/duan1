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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duan1.Adapter.TaiLieuAdapter;
import com.example.duan1.DAO.TaiLieuDAO;
import com.example.duan1.Model.TaiLieu;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TaiLieuFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private TaiLieuAdapter adapter;
    private List<TaiLieu> taiLieuList;

    private EditText edtSearchTaiLieu;
    private ImageView btnSearchTaiLieu;
    private TaiLieuDAO taiLieuDAO;

    public TaiLieuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tai_lieu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView and FloatingActionButton
        recyclerView = view.findViewById(R.id.recyclerViewTaiLieu);
        fabAdd = view.findViewById(R.id.fabAddTaiLieu);
        edtSearchTaiLieu = view.findViewById(R.id.edtSearchTaiLieu);
        btnSearchTaiLieu = view.findViewById(R.id.btnSearchTaiLieu);

        // Initialize TaiLieuDAO
        taiLieuDAO = new TaiLieuDAO(requireContext());

        // Load data from the database
        taiLieuList = taiLieuDAO.getAllTaiLieu();

        // Set up the RecyclerView
        adapter = new TaiLieuAdapter(getContext(),taiLieuList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Set up the FloatingActionButton
        fabAdd.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_nav_QlyTaiLieu_to_nav_ThemTaiLieu);
        });
        btnSearchTaiLieu.setOnClickListener(v -> {
            String keyword = edtSearchTaiLieu.getText().toString().trim();
            if(!keyword.isEmpty()){
                taiLieuList = taiLieuDAO.searchTaiLieu(keyword);
                adapter.updateData(taiLieuList);
                adapter.notifyDataSetChanged();
            }else{
                taiLieuList = taiLieuDAO.getAllTaiLieu();
                adapter.updateData(taiLieuList);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
