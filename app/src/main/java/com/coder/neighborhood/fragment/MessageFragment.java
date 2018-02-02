package com.coder.neighborhood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coder.neighborhood.R;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

/**
 *
 * @author feng
 * @date 2017/12/23
 */

public class MessageFragment extends EaseConversationListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message,null);
    }
}
