package com.example.duan1.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.DiemDAO;
import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.Model.BangDiem;
import com.example.duan1.R;

import java.text.DecimalFormat;
import java.util.List;

public class DiemAdapter extends RecyclerView.Adapter<DiemAdapter.DiemViewHolder> {

    private Context context;
    private List<BangDiem> bangDiemList;

    // Constructor
    public DiemAdapter(Context context, List<BangDiem> bangDiemList) {
        this.context = context;
        this.bangDiemList = bangDiemList;
    }

    @NonNull
    @Override
    public DiemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_diem, parent, false);
        return new DiemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiemViewHolder holder, int position) {
        BangDiem bangDiem = bangDiemList.get(position);

        // Set data to views
        holder.tvMaDiem.setText("Mã điểm: " + bangDiem.getMaDiem());
        MonHocDAO monHocDAO = new MonHocDAO(holder.itemView.getContext());
        String tenMonHoc = monHocDAO.getTenMonHocByMa(bangDiem.getMaMonHoc());
        holder.tvMonDiem.setText("Môn học: " + tenMonHoc);
        holder.tvDiemGiuaKi.setText("Điểm giữa kỳ: " + bangDiem.getDiemGiuaKy());
        holder.tvCuoiki.setText("Điểm cuối kỳ: " + bangDiem.getDiemCuoiKy());
        holder.tvKhac.setText("Điểm khác: " + bangDiem.getDiemKhac());
        DecimalFormat df = new DecimalFormat("#.##"); // Chỉ giữ 2 chữ số thập phân
        holder.tvTongKet.setText("Điểm tổng kết: " + df.format(bangDiem.getDiemTongKet()));
        holder.tvTrangThaiDiem.setText("Trạng thái: " + bangDiem.getTrangThai());


        // Handle edit button click
        holder.btnEditDiem.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update_diem, null);

            // Ánh xạ các thành phần giao diện trong dialog
            TextView etMaMonHoc = dialogView.findViewById(R.id.etMaMonHoc);
            TextView etDiemGiuaKy = dialogView.findViewById(R.id.etDiemGiuaKy);
            TextView etDiemCuoiKy = dialogView.findViewById(R.id.etDiemCuoiKy);
            TextView etDiemKhac = dialogView.findViewById(R.id.etDiemKhac);

            // Điền dữ liệu hiện tại
            String tenMon = monHocDAO.getTenMonHocByMa(bangDiem.getMaMonHoc());
            etMaMonHoc.setText(""+tenMon);
            etDiemGiuaKy.setText(String.valueOf(bangDiem.getDiemGiuaKy()));
            etDiemCuoiKy.setText(String.valueOf(bangDiem.getDiemCuoiKy()));
            etDiemKhac.setText(String.valueOf(bangDiem.getDiemKhac()));

            // Tạo dialog
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("Sửa điểm");
            builder.setView(dialogView);

            // Thêm nút Lưu
            builder.setPositiveButton("Lưu", (dialog, which) -> {
                // Lấy dữ liệu chỉnh sửa
                bangDiem.setMaMonHoc(bangDiem.getMaMonHoc());
                bangDiem.setDiemGiuaKy(Double.parseDouble(etDiemGiuaKy.getText().toString().trim()));
                bangDiem.setDiemCuoiKy(Double.parseDouble(etDiemCuoiKy.getText().toString().trim()));
                bangDiem.setDiemKhac(Double.parseDouble(etDiemKhac.getText().toString().trim()));

                // Tính toán điểm tổng kết (nếu cần)
                double diemTongKet = (bangDiem.getDiemGiuaKy() + bangDiem.getDiemCuoiKy() + bangDiem.getDiemKhac()) / 3;
                bangDiem.setDiemTongKet(diemTongKet);
                if(diemTongKet>=5){
                    bangDiem.setTrangThai("Đạt");
                }else{
                    bangDiem.setTrangThai("Chưa đạt");
                }

                // Sử dụng DiemDAO để cập nhật cơ sở dữ liệu
                DiemDAO diemDAO = new DiemDAO(context);
                int isUpdated = diemDAO.updateDiem(bangDiem.getMaDiem(), bangDiem.getDiemGiuaKy(), bangDiem.getDiemCuoiKy(), bangDiem.getDiemKhac(), bangDiem.getDiemTongKet(), bangDiem.getTrangThai());

                if (isUpdated>0) {
                    notifyItemChanged(position); // Cập nhật lại RecyclerView
                    Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            });

            // Thêm nút Hủy
            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

            // Hiển thị dialog
            builder.create().show();
        });
        Log.d("DiemAdapter", "Trạng thái: " + bangDiem.getTrangThai());

        if (bangDiem.getTrangThai().trim().equalsIgnoreCase("Đạt")) {
            Log.d("DiemAdapter", "Màu xanh được áp dụng");
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        } else {
            Log.d("DiemAdapter", "Màu đỏ được áp dụng");
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return bangDiemList.size();
    }

    public void updateDiemList(List<BangDiem> diemList) {
        this.bangDiemList = diemList;
        notifyDataSetChanged();
    }

    // ViewHolder class
    public static class DiemViewHolder extends RecyclerView.ViewHolder {

        TextView tvMaDiem, tvMonDiem, tvDiemGiuaKi, tvCuoiki, tvKhac, tvTongKet, tvTrangThaiDiem;
        ImageView btnEditDiem;

        public DiemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaDiem = itemView.findViewById(R.id.tvMaDiem);
            tvMonDiem = itemView.findViewById(R.id.tvMonDiem);
            tvDiemGiuaKi = itemView.findViewById(R.id.tvDiemGiuaKi);
            tvCuoiki = itemView.findViewById(R.id.tvCuoiki);
            tvKhac = itemView.findViewById(R.id.tvKhac);
            tvTongKet = itemView.findViewById(R.id.tvTongKet);
            tvTrangThaiDiem = itemView.findViewById(R.id.tvTrangThaiDiem);
            btnEditDiem = itemView.findViewById(R.id.btnEditDiem);
        }
    }

    // Interface for item click listener

}
