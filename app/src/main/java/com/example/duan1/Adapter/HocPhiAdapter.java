package com.example.duan1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.DAO.GhiChuDAO;
import com.example.duan1.DAO.HocPhiDAO;
import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.Model.HocPhi;
import com.example.duan1.R;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

public class HocPhiAdapter extends RecyclerView.Adapter<HocPhiAdapter.HocPhiViewHolder> {

    private Context context;
    private List<HocPhi> hocPhiList;
    private TextView tvTongTien;
    // Constructor
    public HocPhiAdapter(Context context, List<HocPhi> hocPhiList, TextView tvTongTien) {
        this.context = context;
        this.hocPhiList = hocPhiList;
        this.tvTongTien = tvTongTien;
    }

    @NonNull
    @Override
    public HocPhiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hocphi, parent, false);
        return new HocPhiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HocPhiViewHolder holder, int position) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("VND"));
        HocPhi hocPhi = hocPhiList.get(position);

        // Set data to views
        holder.tvMaHocPhi.setText("Mã học phí: " + hocPhi.getMaHocPhi());
        MonHocDAO monHocDAO = new MonHocDAO(holder.itemView.getContext());
        String tenMonHoc = monHocDAO.getTenMonHocByMa(hocPhi.getMaMonHoc());
        holder.tvNameMon.setText("Môn học: " + tenMonHoc);
        holder.tvSoTien.setText("Số tiền: " + format.format(hocPhi.getSoTien()));
        holder.tvHocLai.setText("Số lần đã học: " + hocPhi.getSoLanDaHoc());
        holder.tvTienDaDong.setText("Số tiền đã đóng: " + format.format(hocPhi.getSoTienDaDong()) );

        // Handle button click events
        holder.btnHocLai.setOnClickListener(v -> {
            int newSoLanHocLai = hocPhi.getSoLanDaHoc() + 1;
            hocPhi.setSoLanDaHoc(newSoLanHocLai);

            // Tính lại tổng tiền
            double newTongTien = (hocPhi.getSoTien() * newSoLanHocLai); // Ví dụ
            hocPhi.setSoTienDaDong( newTongTien);

            // Cập nhật vào cơ sở dữ liệu
            HocPhiDAO hocPhiDAO = new HocPhiDAO(context);
            int isUpdated = hocPhiDAO.updateHocPhi(hocPhi.getMaHocPhi(), newSoLanHocLai, newTongTien);

            if (isUpdated>0) {
                // Cập nhật giao diện
                holder.tvHocLai.setText("Số lần học lại: " + newSoLanHocLai);
                holder.tvTienDaDong.setText("Số tiền đã đóng: " + newTongTien);
                Toast.makeText(context, "Đã cập nhật học phí!", Toast.LENGTH_SHORT).show();
                String total = calculateTotal();
                tvTongTien.setText("Tổng tiền: " + total);
                notifyItemChanged(position);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnReset.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Cảnh báo");
            builder.setIcon(R.drawable.iconwarning);
            builder.setMessage("Bạn có chắc chắn muốn đặt lại số lần học lại chứ?");
            builder.setPositiveButton("Có", (dialog, which) -> {
                // Xóa ghi chú bằng GhiChuDAO
                hocPhi.setSoLanDaHoc(1);
                double newTongTien = (hocPhi.getSoTien() * hocPhi.getSoLanDaHoc()); // Ví dụ
                hocPhi.setSoTienDaDong( newTongTien);

                // Cập nhật vào cơ sở dữ liệu
                HocPhiDAO hocPhiDAO = new HocPhiDAO(context);
                int isUpdated = hocPhiDAO.updateHocPhi(hocPhi.getMaHocPhi(), hocPhi.getSoLanDaHoc(), newTongTien);

                if (isUpdated>0) {
                    // Cập nhật giao diện
                    holder.tvHocLai.setText("Số lần học lại: " + hocPhi.getSoLanDaHoc());
                    holder.tvTienDaDong.setText("Số tiền đã đóng: " + newTongTien + " VND");
                    Toast.makeText(context, "Đã cập nhật học phí!", Toast.LENGTH_SHORT).show();
                    String total = calculateTotal();
                    tvTongTien.setText("Tổng tiền: " + total + " VND");
                    notifyItemChanged(position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            });
            builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        });
        if (hocPhi.getSoLanDaHoc() >=2) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return hocPhiList.size();
    }

    // ViewHolder class
    public static class HocPhiViewHolder extends RecyclerView.ViewHolder {

        TextView tvMaHocPhi, tvNameMon, tvSoTien, tvHocLai, tvTienDaDong;
        Button btnHocLai, btnReset;

        public HocPhiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaHocPhi = itemView.findViewById(R.id.tvMaHocPhi);
            tvNameMon = itemView.findViewById(R.id.tvNameMon);
            tvSoTien = itemView.findViewById(R.id.tvSoTien);
            tvHocLai = itemView.findViewById(R.id.tvHocLai);
            tvTienDaDong = itemView.findViewById(R.id.tvTienDaDong);
            btnHocLai = itemView.findViewById(R.id.btnHocLai);
            btnReset = itemView.findViewById(R.id.btnReset);
        }
    }

    private String calculateTotal() {
        double total = 0;
        // Duyệt qua danh sách hocPhiList để tính tổng số tiền đã đóng
        for (HocPhi hocPhi : hocPhiList) {
            total += hocPhi.getSoTienDaDong(); // Cộng số tiền đã đóng của mỗi học phí
        }
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(Currency.getInstance("VND"));
        return format.format(total);
    }

}
