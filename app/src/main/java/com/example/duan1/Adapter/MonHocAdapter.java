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

import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.Model.MonHoc;
import com.example.duan1.R;

import java.util.List;

public class MonHocAdapter extends RecyclerView.Adapter<MonHocAdapter.MonHocViewHolder> {

    private List<MonHoc> monHocList;
    private Context context;
    private MonHocDAO monHocDAO;

    public MonHocAdapter(Context context, List<MonHoc> monHocList) {
        this.context = context;
        this.monHocList = monHocList;
        this.monHocDAO = new MonHocDAO(context); // Khởi tạo DAO
    }

    @NonNull
    @Override
    public MonHocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monhoc, parent, false);
        return new MonHocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonHocViewHolder holder, int position) {
        MonHoc monHoc = monHocList.get(position);
        holder.tvMaMon.setText("Mã môn: " + monHoc.getMa());
        holder.tvTenMon.setText("Tên môn học: " + monHoc.getTen());
        holder.tvSoTinChi.setText("Số tín chỉ: " + monHoc.getSoTC());
        holder.tvGiangVien.setText("Giảng viên: " + monHoc.getGiangVien());

        // Sự kiện sửa môn học
        holder.btnEdit.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                MonHoc currentMonHoc = monHocList.get(currentPosition);
                NavController navController = Navigation.findNavController(holder.itemView);

                // Tạo Bundle để truyền dữ liệu môn học
                Bundle bundle = new Bundle();
                bundle.putInt("maMonHoc", currentMonHoc.getMa());
                bundle.putString("tenMonHoc", currentMonHoc.getTen());
                bundle.putInt("soTinChi", currentMonHoc.getSoTC());
                bundle.putString("giangVien", currentMonHoc.getGiangVien());

                // Điều hướng tới màn hình sửa và truyền bundle
                navController.navigate(R.id.action_nav_QlyMonHoc_to_nav_SuaMonHoc, bundle);
            }
        });

        // Sự kiện xóa môn học
        holder.btnDelete.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                MonHoc currentMonHoc = monHocList.get(currentPosition);
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Cảnh báo");
                builder.setIcon(R.drawable.iconwarning);
                builder.setMessage("Bạn có chắc chắn muốn xóa môn học này không? \nNếu bạn xóa các dữ liệu liên quan cũng sẽ được xóa!");
                builder.setPositiveButton("Có", (dialog, which) -> {
                    int result = monHocDAO.deleteMonHoc(currentMonHoc.getMa());
                    if (result > 0) {
                        monHocList.remove(currentPosition);
                        notifyItemRemoved(currentPosition);
                        notifyItemRangeChanged(currentPosition, monHocList.size());
                        Toast.makeText(v.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                });
                builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
                builder.create().show();
            }
        });
    }
    public void updateData(List<MonHoc> newList) {
        this.monHocList = newList; // Cập nhật danh sách mới
        notifyDataSetChanged();   // Thông báo RecyclerView làm mới dữ liệu
    }

    @Override
    public int getItemCount() {
        return monHocList.size();
    }

    public static class MonHocViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaMon, tvTenMon, tvSoTinChi, tvGiangVien;
        ImageView btnEdit, btnDelete;

        public MonHocViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaMon = itemView.findViewById(R.id.tvMa);
            tvTenMon = itemView.findViewById(R.id.tvName);
            tvSoTinChi = itemView.findViewById(R.id.tvTinChi);
            tvGiangVien = itemView.findViewById(R.id.tvGVien);
            btnEdit = itemView.findViewById(R.id.btnsua);
            btnDelete = itemView.findViewById(R.id.btnxoa);
        }
    }
}
