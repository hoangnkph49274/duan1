package com.example.duan1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.LichHoc;
import com.example.duan1.R;

import java.util.List;

public class LichHocAdapter extends RecyclerView.Adapter<LichHocAdapter.LichHocViewHolder> {

    private List<LichHoc> lichHocList;

    public LichHocAdapter(List<LichHoc> lichHocList) {
        this.lichHocList = lichHocList;
    }

    @NonNull
    @Override
    public LichHocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichhoc, parent, false);
        return new LichHocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichHocViewHolder holder, int position) {
        LichHoc lichHoc = lichHocList.get(position);

        holder.tvMaLich.setText("Mã lịch học: " + lichHoc.getMa());
        holder.tvNgayHoc.setText("Ngày học: " + lichHoc.getNgay());
        holder.tvGioHoc.setText("Giờ học: " + lichHoc.getGio());
        holder.tvGiangVien.setText("Giảng viên: " + lichHoc.getGvien());
        holder.tvMonHoc.setText("Môn: " + lichHoc.getMon());
        holder.tvTrangThai.setText("Trạng thái: " + lichHoc.getTrangThai());

        holder.btnEdit.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_nav_LichHoc_to_nav_UpdateLichHoc);
        });
    }

    @Override
    public int getItemCount() {
        return lichHocList.size();
    }

    public static class LichHocViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaLich, tvNgayHoc, tvGioHoc, tvGiangVien, tvMonHoc, tvTrangThai;
        ImageView btnEdit;

        public LichHocViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaLich = itemView.findViewById(R.id.tvMaLich);
            tvNgayHoc = itemView.findViewById(R.id.tvNgayHoc);
            tvGioHoc = itemView.findViewById(R.id.tvGioHoc);
            tvGiangVien = itemView.findViewById(R.id.tvGiangVien);
            tvMonHoc = itemView.findViewById(R.id.tvMonHoc);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

}