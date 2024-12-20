package com.example.duan1.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Model.MucTieu;
import com.example.duan1.R;

import java.util.List;

public class MucTieuAdapter extends RecyclerView.Adapter<MucTieuAdapter.ViewHolder> {

    private List<MucTieu> mucTieuList;
    private Context context;

    // Constructor
    public MucTieuAdapter(Context context, List<MucTieu> mucTieuList) {
        this.context = context;
        this.mucTieuList = mucTieuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_muctieu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MucTieu mucTieu = mucTieuList.get(position);

        holder.tvMa.setText("Mã mục tiêu: " + mucTieu.getMa());
        holder.tvTen.setText("Tên mục tiêu: " + mucTieu.getNoidung());
        holder.tvNgayHoc.setText("Ngày bắt đầu: " + mucTieu.getNgay());
        if(mucTieu.getTrangthai().equals("1")){
            holder.tvTrangThai.setText("Trạng thái: Đã hoàn thành");
        }else{
            holder.tvTrangThai.setText("Trạng thái: Chưa hoàn thành");
        }

        // Xử lý sự kiện khi nhấn vào nút chỉnh sửa
        holder.btnEdit.setOnClickListener(v -> {
            // Thực hiện hành động khi nhấn vào nút chỉnh sửa
            // Ví dụ: hiển thị một thông báo
            Bundle bundle = new Bundle();
            bundle.putInt("MucTieuId", mucTieu.getMa());
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_nav_MucTieu_to_nav_UpdateMucTieu, bundle);
            Toast.makeText(context, "Chỉnh sửa: " + mucTieu.getNoidung(), Toast.LENGTH_SHORT).show();

            // Thêm các thao tác khác tùy vào yêu cầu của bạn, ví dụ mở dialog hoặc intent
        });
    }

    @Override
    public int getItemCount() {
        return mucTieuList.size();
    }

    public void updateData(List<MucTieu> filteredMucTieuList) {
        this.mucTieuList = filteredMucTieuList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMa, tvTen, tvNgayHoc, tvTrangThai;
        ImageView btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMa);
            tvTen = itemView.findViewById(R.id.tvten);
            tvNgayHoc = itemView.findViewById(R.id.tvNgayHoc);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
