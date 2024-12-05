package com.example.duan1.UI;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.HocPhiAdapter;
import com.example.duan1.DAO.HocPhiDAO;
import com.example.duan1.Model.HocPhi;
import com.example.duan1.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class HocPhiFragment extends Fragment {

    private RecyclerView recyclerViewHocPhi;
    private HocPhiAdapter hocPhiAdapter;
    private List<HocPhi> hocPhiList;
    private ImageView btnSearchHocPhi;
    private HocPhiDAO hocPhiDAO;
    private TextView tvTong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hoc_phi, container, false);

        // Initialize views
        recyclerViewHocPhi = rootView.findViewById(R.id.recyclerViewHocPhi);
//        btnSearchHocPhi = rootView.findViewById(R.id.btnSearchHocPhi);
        tvTong = rootView.findViewById(R.id.tvTongTienHoc);

        // Initialize DAO
        hocPhiDAO = new HocPhiDAO(getContext());

        // Initialize list of HocPhi
        hocPhiList = hocPhiDAO.getAllHocPhi(); // Get data from DAO
        String totalAmount = calculateTotal();
        tvTong.setText("Tổng tiền: " + totalAmount+" VND");

        // Set up RecyclerView
        hocPhiAdapter = new HocPhiAdapter(getContext(), hocPhiList,tvTong);

        recyclerViewHocPhi.setAdapter(hocPhiAdapter);
        recyclerViewHocPhi.setLayoutManager(new LinearLayoutManager(getContext()));

        // Handle search click
//        btnSearchHocPhi.setOnClickListener(v -> {
//            // Assuming you have an EditText for search (edtSearchHocPhi)
//            String searchQuery = rootView.findViewById(R.id.edtSearchHocPhi).toString().trim();
//            if(!searchQuery.isEmpty()){
//
//            }else{
//
//            }
//        });

        return rootView;
    }
    private String calculateTotal() {
        double total = 0;
        // Duyệt qua danh sách hocPhiList để tính tổng số tiền đã đóng
        for (HocPhi hocPhi : hocPhiList) {
            total += hocPhi.getSoTienDaDong(); // Cộng số tiền đã đóng của mỗi học phí
        }
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("VND"));
        return format.format(total);
    }
}
