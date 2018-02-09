package com.coder.neighborhood.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.message.ChatActivity;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.fragment.CircleFragment;
import com.coder.neighborhood.fragment.HomeFragment;
import com.coder.neighborhood.fragment.MallFragment;
import com.coder.neighborhood.fragment.MessageFragment;
import com.coder.neighborhood.fragment.UserFragment;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import ww.com.core.Debug;
import ww.com.core.adapter.MenuTabAdapter;

/**
 * @author feng
 */
public class MainActivity extends BaseActivity<VoidView, VoidModel> {

    @BindViews({R.id.tab_home_layout, R.id.tab_cart_layout, R.id.tab_circle_layout, R.id
            .tab_message_layout, R.id.tab_user_layout})
    List<View> menus;
    @BindViews({R.id.tab_home_image, R.id.tab_cart_image, R.id.tab_circle_image, R.id
            .tab_message_image, R.id.tab_user_image})
    List<View> images;
    @BindViews({R.id.tab_home_text, R.id.tab_cart_text, R.id.tab_circle_text, R.id
            .tab_message_text, R.id.tab_user_text})
    List<View> texts;


    private MenuTabAdapter adapter;
    private List<Fragment> fragments;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new MallFragment());
        fragments.add(new CircleFragment());
        fragments.add(createMessageFragment());
        fragments.add(new UserFragment());

        adapter = new MenuTabAdapter(this, menus, fragments, R.id.main_content);

        adapter.setOnMenuClickListener(new MenuTabAdapter.OnMenuClickListener() {
            @Override
            public void onDoubleClick(View view) {

            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tab_home_layout:
                        changeMenu(0);
                        break;
                    case R.id.tab_cart_layout:
                        changeMenu(1);
                        break;
                    case R.id.tab_circle_layout:
                        changeMenu(2);
                        break;
                    case R.id.tab_message_layout:
                        changeMenu(3);
                        break;
                    case R.id.tab_user_layout:
                        changeMenu(4);
                        break;
                    default:
                        break;
                }
            }
        });

        initEMClient();

        changeMenu(0);
    }

    public void changeMenu(int index){
        adapter.changeMenuStatus(index);
        changeMenuStatus(index);
        adapter.changeMenu(index);
    }


    private Fragment createMessageFragment(){
        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setConversationListItemClickListener(
                conversation -> startActivity(new Intent(MainActivity.this,
                        ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId())));
        return messageFragment;
    }

    public void changeMenuStatus(int index) {
        int imageSize = images.size();
        for (int i = 0; i < imageSize; i++) {
            if (i == index) {
                this.images.get(i).setSelected(true);
                this.texts.get(i).setSelected(true);
            } else {
                this.images.get(i).setSelected(false);
                this.texts.get(i).setSelected(false);
            }
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                Toast.makeText(getApplicationContext(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                BaseApplication.getInstance().exitApp();
                onBackPressed();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initEMClient(){
        UserBean user  = (UserBean) BaseApplication.getInstance().getUserInfo();

        if (user !=null){
            EMClient.getInstance().login(user.getEasemobUsername(),user.getEasemobPassword(),new EMCallBack() {
                @Override
                public void onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    Debug.d("登录聊天服务器成功！");
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    Debug.e("登录聊天服务器失败！");
                }
            });
        }
    }


}
