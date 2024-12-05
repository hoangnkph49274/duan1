package com.example.duan1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.duan1.DAO.GhiChuDAO;
import com.example.duan1.Model.GhiChu;
import com.example.duan1.R;

import java.util.List;

public class GhiChuAdapter extends RecyclerView.Adapter<GhiChuAdapter.GhiChuViewHolder> {

    private Context context;
    private List<GhiChu> ghiChuList;

    public GhiChuAdapter(Context context, List<GhiChu> ghiChuList) {
        this.context = context;
        this.ghiChuList = ghiChuList;
    }

    @NonNull
    @Override
    public GhiChuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ghichu, parent, false);
        return new GhiChuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GhiChuViewHolder holder, int position) {
        GhiChu ghiChu = ghiChuList.get(position);

        holder.tvMa.setText("Mã ghi chú: " + ghiChu.getMa());
        holder.tvName.setText("Tên ghi chú: " + ghiChu.getTen());
        holder.tvNgay.setText("Ngày tạo: " + ghiChu.getNgay());

        holder.btnXem.setOnClickListener(v ->{
            Bundle bundle = new Bundle();
            bundle.putInt("maGhiChu", ghiChu.getMa());  // Mã ghi chú
            bundle.putString("tenGhiChu", ghiChu.getTen());  // Tên ghi chú
            bundle.putString("ngayTao", ghiChu.getNgay());  // Ngày tạo

            // Điều hướng và truyền dữ liệu sang fragment khác
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_nav_GhiChu_to_nav_XemGhiChu, bundle);

        });

        holder.btnXoa.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Cảnh báo");
            builder.setIcon(R.drawable.iconwarning);
            builder.setMessage("Bạn có chắc chắn muốn xóa ghi chú này không?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                // Xóa ghi chú bằng GhiChuDAO
                GhiChuDAO ghiChuDAO = new GhiChuDAO(context);
                int result = ghiChuDAO.deleteGhiChu(ghiChu.getMa());

                if (result > 0) {
                    // Xóa thành công -> Cập nhật danh sách
                    ghiChuList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), ghiChuList.size());
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Xóa thất bại
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            });
            builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return ghiChuList.size();
    }

    public void updateData(List<GhiChu> searchResults) {
        this.ghiChuList = searchResults;
        notifyDataSetChanged();
    }

    public static class GhiChuViewHolder extends RecyclerView.ViewHolder {

        TextView tvMa, tvName, tvNgay;
        ImageView btnXem, btnXoa;

        public GhiChuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMa);
            tvName = itemView.findViewById(R.id.tvName);
            tvNgay = itemView.findViewById(R.id.tvNgay);
            btnXem = itemView.findViewById(R.id.btnXem);
            btnXoa = itemView.findViewById(R.id.btnxoa);
        }
    }
}
