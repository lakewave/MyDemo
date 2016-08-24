package com.ghb.mydemo.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghb.mydemo.R;

/**
 * (what to do)
 * Created by lakewave on 2016/8/24.
 */
public class PageFragment extends Fragment {

    public static final String TAB_PAGE = "TAB_PAGE";
    private int mPage;

    public static PageFragment newInstance(int page){
        Bundle bundle = new Bundle();
        bundle.putInt(TAB_PAGE,page);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(bundle);
        return pageFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(TAB_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_textview,container,false);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText("Fragment# "+mPage);
        return view;
    }
}
