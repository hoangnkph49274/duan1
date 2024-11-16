package com.example.duan1.UI;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.duan1.Adapter.MonHoc;
import com.example.duan1.Adapter.MonHocAdapter;
import com.example.duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MonHocFragment extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private MonHocAdapter adapter;
    private List<MonHoc> monHocList;
    private NavController navController;

    public MonHocFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mon_hoc, container, false);

        // Initialize RecyclerView and FloatingActionButton
        recyclerView = view.findViewById(R.id.recyclerViewMonHoc);
        fabAdd = view.findViewById(R.id.fabAddMonHoc);

        // Initialize data
        monHocList = new ArrayList<>();
        monHocList.add(new MonHoc("C1", "Lập trình C", 3, "Cô Thu"));
        monHocList.add(new MonHoc("Java1", "Lập trình Java", 3, "Thầy Sơn"));
        // Add more courses as needed

        // Set up the RecyclerView
        adapter = new MonHocAdapter(monHocList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Set up the FloatingActionButton
        fabAdd.setOnClickListener(v -> {
            navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_nav_QlyMonHoc_to_nav_ThemMonHoc);
        });

        return view;
    }
}
