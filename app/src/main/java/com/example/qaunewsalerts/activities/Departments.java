package com.example.qaunewsalerts.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.adaptor.AllDepartmentAdaptor;

import java.util.ArrayList;

public class Departments extends AppCompatActivity {
    private RecyclerView all_department_Recycleview;
    private AllDepartmentAdaptor all_department_Adapter;
    private RecyclerView.LayoutManager all_departmentlayoutManager;
    CheckBox DepartmentCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);
        getSupportActionBar().setTitle("Departments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        all_department_Recycleview = findViewById(R.id.all_department_name);

        ArrayList<String> departmentName = new ArrayList<>();
        departmentName.add("Chemistry");
        departmentName.add("Computer Sciences");
        departmentName.add("Earth Sciences");
        departmentName.add("Electronics");
        departmentName.add("Mathematics");
        departmentName.add("Physics");
        departmentName.add("Statistics");
        departmentName.add("Management Sciences");
        departmentName.add("Anthropology");
        departmentName.add("Sociology");
        departmentName.add("Defence and Strategic Studies");
        departmentName.add("School of Economics");
        departmentName.add("History");
        departmentName.add("School of Politics and IR");
        departmentName.add("Area Study Centre");
        departmentName.add("Center of Excellence in Gender Studies");
        departmentName.add("National Institute of Psychology");
        departmentName.add("Taxila Institute of Asian Civilization");
        departmentName.add("National Institute of Pakistan Studies");
        departmentName.add("Linguistics");
        departmentName.add("Zoology");
        departmentName.add("Biochemistry");
        departmentName.add("Environmental Sciences");
        departmentName.add("Microbiology");
        departmentName.add("Plant Sciences");
        departmentName.add("Biotechnology");
        departmentName.add("National Center for Bioinformatics");
        departmentName.add("Pharmacy");

        all_departmentlayoutManager = new LinearLayoutManager(Departments.this);
        all_department_Adapter = new AllDepartmentAdaptor( departmentName, this);
        all_department_Recycleview.setHasFixedSize(true);
        all_department_Recycleview.setLayoutManager(all_departmentlayoutManager);
        all_department_Recycleview.setAdapter(all_department_Adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem menuitem=menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                all_department_Adapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }
}