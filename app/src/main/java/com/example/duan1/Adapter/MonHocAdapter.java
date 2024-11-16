package com.example.duan1.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.MonHoc;
import com.example.duan1.R;

import java.util.List;

public class MonHocAdapter extends RecyclerView.Adapter<MonHocAdapter.MonHocViewHolder> {

    private List<MonHoc> monHocList;

    public MonHocAdapter(List<MonHoc> monHocList) {
        this.monHocList = monHocList;
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

        holder.btnEdit.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(holder.itemView);
            navController.navigate(R.id.action_nav_QlyMonHoc_to_nav_SuaMonHoc);
        });

        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Cảnh báo");
            builder.setIcon(R.drawable.iconwarning);
            builder.setMessage("Bạn có chắc chắn muốn xóa chứ ?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(builder.getContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
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
