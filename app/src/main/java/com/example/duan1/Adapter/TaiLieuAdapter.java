package com.example.duan1.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
 // Đảm bảo bạn đã định nghĩa lớp TaiLieu cho các đối tượng tài liệu
import com.example.duan1.R;

import java.util.List;

public class TaiLieuAdapter extends RecyclerView.Adapter<TaiLieuAdapter.TaiLieuViewHolder> {

    private List<TaiLieu> taiLieuList;

    public TaiLieuAdapter(List<TaiLieu> taiLieuList) {
        this.taiLieuList = taiLieuList;
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
        holder.tvMon.setText("Môn: " + taiLieu.getMon());

        // Xử lý sự kiện khi nhấn nút "Xem"
        holder.btnXem.setOnClickListener(v -> {
            // Xử lý logic khi nhấn vào nút xem
            // Ví dụ: Mở tài liệu hoặc hiển thị chi tiết
        });

        // Xử lý sự kiện khi nhấn nút "Xóa"
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
