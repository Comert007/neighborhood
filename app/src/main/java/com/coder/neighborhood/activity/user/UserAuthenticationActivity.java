package com.coder.neighborhood.activity.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.user.CommunityAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.user.CommunityBean;
import com.coder.neighborhood.mvp.model.user.UserModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomRecyclerView;

/**
 * @author feng
 * @Date 2018/1/16
 */

@SuppressLint("Registered")
public class UserAuthenticationActivity extends BaseActivity<VoidView, UserModel> {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_id_card)
    EditText etIdCard;
    @BindView(R.id.tv_community)
    TextView tvCommunity;

    private BottomSheetDialog sheetDialog;
    private List<CommunityBean> communityBeans;
    private String communityId;

    public static void start(Activity context) {
        Intent intent = new Intent(context, UserAuthenticationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_authentication;
    }

    @Override
    protected void init() {
        communities();
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }


    @OnClick({R.id.ll_community_selector})
    public void onAuthentication(View view) {
        switch (view.getId()) {
            case R.id.ll_community_selector:
                showSheetDialog();
                break;
            default:
                break;
        }
    }

    private void showSheetDialog() {
        if (sheetDialog == null) {
            sheetDialog = new BottomSheetDialog(this,R.style.CustomerDialogStyle);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_community, null);
            ScreenUtil.scale(view);
            CustomRecyclerView crv = ButterKnife.findById(view, R.id.crv);
            crv.setLayoutManager(new LinearLayoutManager(this));
            CommunityAdapter adapter = new CommunityAdapter(this);
            adapter.setOnActionListener((position, view1) -> {
                CommunityBean bean = adapter.getItem(position);
                tvCommunity.setText(bean.getCommunityName());
                communityId = bean.getCommunityId();
                sheetDialog.dismiss();
            });
            adapter.addList(communityBeans);
            crv.setAdapter(adapter);
            sheetDialog.setContentView(view);
        }
        sheetDialog.show();
    }

    private void communities() {
        m.communities(bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<List<CommunityBean>>(this, true) {
                    @Override
                    public void onNext(List<CommunityBean> communityBeans) {
                        UserAuthenticationActivity.this.communityBeans = communityBeans;
                    }
                });
    }

    @OnClick(R.id.btn_verified)
    public void onAuthen(){
        onCertification();
    }

    private void onCertification() {
        String name = etName.getText().toString().trim();
        String idCard = etIdCard.getText().toString().trim().replaceAll(" ","");

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(idCard)) {
            ToastUtils.showToast("请输入身份证号");
            return;
        }

        if (!isIDCard(idCard)) {
            ToastUtils.showToast("请输入正确的身份证号");
            return;
        }

        if (TextUtils.isEmpty(communityId)) {
            ToastUtils.showToast("请选择小区");
            return;
        }

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null && !TextUtils.isEmpty(user.getUserId())) {

            m.userCertification(user.getUserId(), name, idCard, communityId, bindUntilEvent
                            (ActivityEvent.DESTROY)
                    , new HttpSubscriber<String>(this, true) {
                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast(s);
                            UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
                            if (user != null) {
                               user.setUserStatus("1");
                               BaseApplication.getInstance().saveUserInfo(user);
                            }
                            finish();
                        }
                    });
        }

    }

    /**
     * 判断是否符合身份证号码的规范
     *
     * @param idCard 身份证号
     * @return
     */
    public static boolean isIDCard(String idCard) {
        if (idCard != null) {
            String idCardRegex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x|Y|y)$)";
            return idCard.matches(idCardRegex);
        }
        return false;
    }

}
