package com.example.duan1.UI;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.BaiTapAdapter;
import com.example.duan1.DAO.BaiTapDAO;
import com.example.duan1.Model.BaiTap;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BaiTapFragment extends Fragment {

    private RecyclerView recyclerView;
    private BaiTapAdapter adapter;
    private List<BaiTap> baiTapList;
    private FloatingActionButton fabAdd;
    private BaiTapDAO baiTapDAO;
    private EditText editSearchBaiTap;
    private ImageView btnSearchBaiTap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bai_tap, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBaiTap);
        fabAdd = view.findViewById(R.id.fabAddBaitap);
        editSearchBaiTap = view.findViewById(R.id.edtSearchBaiTap);
        btnSearchBaiTap = view.findViewById(R.id.btnSearchBaiTap);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        baiTapDAO = new BaiTapDAO(getContext());
        baiTapList = loadBaiTapFromDatabase();

        adapter = new BaiTapAdapter(baiTapList);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_nav_QlyBaiTap_to_nav_ThemBaiTap);
            }
        });
        btnSearchBaiTap.setOnClickListener(v -> {
            String keyword = editSearchBaiTap.getText().toString().trim();
            if (!keyword.isEmpty()) {
                List<BaiTap> baiTapList = baiTapDAO.searchBaiTap(keyword);
                Log.d("zzzzzzzzzzzzzz", ""+baiTapList.size());
                adapter.updateData(baiTapList);
                adapter.notifyDataSetChanged();
            }else{
                List<BaiTap> List = loadBaiTapFromDatabase();
                adapter.updateData(List);
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }
    private List<BaiTap> loadBaiTapFromDatabase() {
        List<BaiTap> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = baiTapDAO.getAllBaiTap();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int maBaiTap = cursor.getInt(cursor.getColumnIndexOrThrow("MaBaiTap"));
                    String tenBaiTap = cursor.getString(cursor.getColumnIndexOrThrow("TenBaiTap"));
                    String hanNop = cursor.getString(cursor.getColumnIndexOrThrow("HanNop"));
                    int trangThai = cursor.getInt(cursor.getColumnIndexOrThrow("TrangThai"));
                    String noiDungBaiTap = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungBaiTap"));
                    int maMonHoc = cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc"));

                    list.add(new BaiTap(maBaiTap,tenBaiTap, hanNop, trangThai, maMonHoc));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }



}
