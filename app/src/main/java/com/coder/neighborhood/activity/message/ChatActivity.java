package com.coder.neighborhood.activity.message;

import android.annotation.SuppressLint;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.fragment.OnlineConsultationFragment;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.hyphenate.easeui.EaseConstant;

/**
 * @author feng
 * @Date 2018/2/2.
 */

@SuppressLint("Registered")
public class ChatActivity extends BaseActivity<VoidView, VoidModel> {

    private String toChatUsername;
    private OnlineConsultationFragment onlineConsultationFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init() {
        toChatUsername = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID);
        onlineConsultationFragment = new OnlineConsultationFragment();
        //set arguments
        onlineConsultationFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container,
                onlineConsultationFragment).commit();

        setTitleText("与"+toChatUsername+"聊天中");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        // enter to chat activity when click notification bar, here make sure only one chat activiy
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username)) {
            super.onNewIntent(intent);
        } else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        onlineConsultationFragment.onBackPressed();
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }
}
