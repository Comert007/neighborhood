package com.coder.neighborhood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coder.neighborhood.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

/**
 * @author feng
 * @Date 2018/1/16
 */

public class OnlineConsultationFragment extends EaseChatFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online_consultation, container, false);
    }
}
