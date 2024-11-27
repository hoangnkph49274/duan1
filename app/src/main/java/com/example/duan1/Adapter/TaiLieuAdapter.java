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
import com.example.duan1.DAO.TaiLieuDAO;
import com.example.duan1.Model.TaiLieu;
import com.example.duan1.R;

import java.util.List;

public class TaiLieuAdapter extends RecyclerView.Adapter<TaiLieuAdapter.TaiLieuViewHolder> {

    private List<TaiLieu> taiLieuList;
    private TaiLieuDAO taiLieuDAO;
    private Context context;

    public TaiLieuAdapter(Context context, List<TaiLieu> taiLieuList) {
        this.context = context;
        this.taiLieuList = taiLieuList;
        this.taiLieuDAO = new TaiLieuDAO(context); // Khởi tạo DAO
    }

    @NonNull
    @Override
    public TaiLieuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tailieu, parent, false);
        return new TaiLieuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaiLieuViewHolder holder, int position) {
        TaiLieu taiLieu = taiLieuList.get(position);

        // Đặt dữ liệu cho các TextView
        holder.tvMa.setText("Mã tài liệu: " + taiLieu.getMa());
        holder.tvName.setText("Tên tài liệu: " + taiLieu.getTen());
        holder.tvLoai.setText("Loại tài liệu: " + taiLieu.getLoai());
        holder.tvDdan.setText("Đường dẫn: " + taiLieu.getDdan());

        // Lấy tên môn học từ maMonHoc
        MonHocDAO monHocDAO = new MonHocDAO(context);
        String tenMonHoc = monHocDAO.getTenMonHocByMa(taiLieu.getMon());
        holder.tvMon.setText("Tên môn học: " + tenMonHoc);

        // Xử lý sự kiện khi nhấn nút "Xem"
        holder.btnXem.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);

            // Truyền dữ liệu vào bundle để xem chi tiết
            Bundle bundle = new Bundle();
            bundle.putInt("maTaiLieu", taiLieu.getMa());
            bundle.putString("tenTaiLieu", taiLieu.getTen());
            bundle.putString("loaiTaiLieu", taiLieu.getLoai());
            bundle.putString("duongDan", taiLieu.getDdan());
            bundle.putInt("maMonHoc", taiLieu.getMon());

            navController.navigate(R.id.action_nav_QlyTaiLieu_to_nav_XemTaiLieu, bundle);
        });

        // Xử lý sự kiện khi nhấn nút "Xóa"
        holder.btnXoa.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cảnh báo");
            builder.setIcon(R.drawable.iconwarning);
            builder.setMessage("Bạn có chắc chắn muốn xóa tài liệu này?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                int result = taiLieuDAO.deleteTaiLieu(taiLieu.getMa());
                if (result > 0) {
                    taiLieuList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            });
            builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });
    }


    @Override
    public int getItemCount() {
        return taiLieuList.size();
    }

    public static class TaiLieuViewHolder extends RecyclerView.ViewHolder {
        TextView tvMa, tvName, tvLoai, tvDdan, tvMon;
        ImageView btnXem, btnXoa;

        public TaiLieuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMa);
            tvName = itemView.findViewById(R.id.tvName);
            tvLoai = itemView.findViewById(R.id.tvLoai);
            tvDdan = itemView.findViewById(R.id.tvDdan);
            tvMon = itemView.findViewById(R.id.tvMon);
            btnXem = itemView.findViewById(R.id.btnXem);
            btnXoa = itemView.findViewById(R.id.btnxoa);
        }
    }
}
