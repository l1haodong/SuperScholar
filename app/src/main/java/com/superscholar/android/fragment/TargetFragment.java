package com.superscholar.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superscholar.android.R;

/**
 * Created by zhqy on 2017/6/14.
 */

public class TargetFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.page_target,container,false);
        return view;
    }

}
