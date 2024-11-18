package com.example.duan1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.duan1.Adapter.GhiChu;
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
            Toast.makeText(context, "Xem ghi chú: " + ghiChu.getMa(), Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_nav_GhiChu_to_nav_XemGhiChu);
        });

        holder.btnXoa.setOnClickListener(v -> {
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
        return ghiChuList.size();
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
