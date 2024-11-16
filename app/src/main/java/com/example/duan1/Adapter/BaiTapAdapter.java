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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.BaiTap;
import com.example.duan1.R;
import java.util.List;

public class BaiTapAdapter extends RecyclerView.Adapter<BaiTapAdapter.BaiTapViewHolder> {

    private List<BaiTap> baiTapList;


    public BaiTapAdapter(List<BaiTap> baiTapList) {
        this.baiTapList = baiTapList;
    }

    @NonNull
    @Override
    public BaiTapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baitap, parent, false);
        return new BaiTapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaiTapViewHolder holder, int position) {
        BaiTap baiTap = baiTapList.get(position);
        holder.tvMa.setText("Mã bài tập: " + baiTap.getMa());
        holder.tvName.setText("Tên bài tập: " + baiTap.getTen());
        holder.tvHanNop.setText("Hạn nộp: " + baiTap.getHanNop());
        holder.tvTrangThai.setText("Trạng Thái: " + baiTap.getTrangThai());
        holder.tvMon.setText("Môn: " + baiTap.getMon());

        holder.btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return baiTapList.size();
    }

    static class BaiTapViewHolder extends RecyclerView.ViewHolder {

        TextView tvMa, tvName, tvHanNop, tvTrangThai, tvMon;
        ImageView btnXem, btnXoa;

        BaiTapViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMa);
            tvName = itemView.findViewById(R.id.tvName);
            tvHanNop = itemView.findViewById(R.id.tvHanNop);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            tvMon = itemView.findViewById(R.id.tvMon);
            btnXem = itemView.findViewById(R.id.btnXem);
            btnXoa = itemView.findViewById(R.id.btnxoa);
        }
    }
}
