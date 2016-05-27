package com.ghb.mydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.ghb.mydemo.R;

import java.util.ArrayList;

public class SearchViewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ListView lv;
    SearchView sv;
    String[] names = { "aaa", "fas", "dfg", "aud", "aod", "bso", "ago", "bey" };
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayList<String> mAllList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        sv = (SearchView) findViewById(R.id.sv);
        sv.setIconifiedByDefault(false);//搜索图标是否在输入框内
        sv.setOnQueryTextListener(this);
        sv.setSubmitButtonEnabled(false);

        lv = (ListView) findViewById(R.id.lv);



        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                list);
        lv.setAdapter(adapter);
//		sv.setSuggestionsAdapter();

//		for (int i = 0; i < names.length; i++) {
//			list.add(names[i]);
//		}

//		 lv.setTextFilterEnabled(true);// 开启过滤功能
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//		list.clear();
//		adapter.notifyDataSetChanged();
        Toast.makeText(this, "submit", Toast.LENGTH_SHORT).show();
        addData(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ListAdapter adapter = lv.getAdapter();
        if (adapter instanceof Filterable) {
            Filter filter = ((Filterable) adapter).getFilter();
            if (newText == null || newText.length() == 0) {
                filter.filter(null);
            } else {
                filter.filter(newText);
            }
        }
//		lv.setFilterText(newText);
        return true;
    }

    private void addData(String text){
        ArrayList<String> mList = new ArrayList<String>();
//		sv.setVisibility(View.GONE);
        for (int i = 0; i < 20; i++) {
            mList.add(text+" >> "+i);
        }
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList);
        lv.setAdapter(mAdapter);

    }
}
