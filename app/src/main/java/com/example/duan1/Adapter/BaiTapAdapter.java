package com.example.duan1.Adapter;

import android.app.AlertDialog;
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

import com.example.duan1.DAO.BaiTapDAO;
import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.Model.BaiTap;
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

        MonHocDAO monHocDAO = new MonHocDAO(holder.itemView.getContext());
        String tenMonHoc = monHocDAO.getTenMonHocByMa(baiTap.getMon());
        holder.tvMon.setText("Môn học: " + tenMonHoc);
        if (baiTap.getTrangThai()==1) {
            holder.tvTrangThai.setText("Trạng thái: Đã hoàn thành");
        } else {
            holder.tvTrangThai.setText("Trạng thái: Chưa hoàn thành");
        }

        holder.btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("maBaiTap", baiTap.getMa());
                bundle.putString("tenBaiTap", baiTap.getTen());
                bundle.putString("hanNop", baiTap.getHanNop());
                bundle.putInt("maMon", baiTap.getMon());

                // Điều hướng với dữ liệu
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_nav_QlyBaiTap_to_nav_XemBaiTap, bundle);
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
                        BaiTapDAO baiTapDAO = new BaiTapDAO(v.getContext());
                        int result = baiTapDAO.deleteBaiTap(baiTap.getMa());

                        if (result > 0) {
                            // Xóa thành công
                            baiTapList.remove(holder.getAdapterPosition()); // Xóa bài tập khỏi danh sách
                            notifyItemRemoved(holder.getAdapterPosition()); // Cập nhật RecyclerView
                            Toast.makeText(v.getContext(), "Xóa bài tập thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            // Xóa thất bại
                            Toast.makeText(v.getContext(), "Xóa bài tập thất bại", Toast.LENGTH_SHORT).show();
                        }

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

    public void updateData(List<BaiTap> list) {
        this.baiTapList = list; // Cập nhật danh sách mới
        notifyDataSetChanged();
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
