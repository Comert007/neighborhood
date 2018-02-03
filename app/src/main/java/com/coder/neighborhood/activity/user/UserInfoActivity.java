package com.coder.neighborhood.activity.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.user.UserModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ww.com.core.ScreenUtil;

/**
 * @Author feng
 * @Date 2018/1/16
 */

@SuppressLint("Registered")
public class UserInfoActivity extends BaseActivity<VoidView,UserModel> {

    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.ll_address_type)
    LinearLayout llAddressType;
    @BindView(R.id.tv_address_type)
    TextView tvAddressType;
    @BindView(R.id.et_info)
    EditText etInfo;

    private BottomSheetDialog sheetDialog;
    private String type;

    public static void start(Activity context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivityForResult(intent,0x15);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void init() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null){
         etNickname.setText(user.getNickName());
         etPhone.setText(user.getPhone());
         etInfo.setText(user.getUserInfo());
         type = user.getAddressDisplayFlag();
         if (TextUtils.equals("0",type)){
             tvAddressType.setText("显示居住地");
         }else if (TextUtils.equals("1",type)){
             tvAddressType.setText("对好友显示居住地");
         }else if (TextUtils.equals("2",type)){
             tvAddressType.setText("隐藏居住地");
         }
        }
    }


    @OnClick({R.id.btn_ok,R.id.ll_address_type})
    public void onUserInfo(View view){
        switch (view.getId()){
            case R.id.ll_address_type:
                showBottomDialog();
                break;
            case R.id.btn_ok:
                onModifyInfo();
                break;
            default:
                break;

        }
    }

    private void showBottomDialog(){
        if (sheetDialog ==null){
            sheetDialog = new BottomSheetDialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_sheet_address,null);
            ButterKnife.findById(view,R.id.tv_show_all).setOnClickListener(v -> {
                type ="0";
                tvAddressType.setText("显示居住地");
                sheetDialog.dismiss();
            });

            ButterKnife.findById(view,R.id.tv_show_friends).setOnClickListener(v -> {
                type ="1";
                tvAddressType.setText("对好友显示居住地");
                sheetDialog.dismiss();
            });

            ButterKnife.findById(view,R.id.tv_hide).setOnClickListener(v -> {
                type ="2";
                tvAddressType.setText("隐藏居住地");
                sheetDialog.dismiss();

            });

            ButterKnife.findById(view,R.id.btn_cancel).setOnClickListener(v -> {
                sheetDialog.dismiss();
            });

            ScreenUtil.scale(view);
            sheetDialog.setContentView(view);
        }

        if (sheetDialog.isShowing()){
            sheetDialog.dismiss();
        }else {
            sheetDialog.show();
        }

    }

    private void onModifyInfo(){
        String nickname = etNickname.getText().toString().trim();
        String phone = etPhone.getText().toString().trim().replaceAll(" ","");
        String info = etInfo.getText().toString();

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();

        if (user!=null && !TextUtils.isEmpty(user.getUserId())){
            m.modifyUserInfo(user.getUserId(), nickname, phone, type, info, bindUntilEvent(ActivityEvent.DESTROY)

                    , new HttpSubscriber<String>(this,true) {
                        @Override
                        public void onNext(String s) {
                            user.setNickName(nickname);
                            user.setPhone(phone);
                            user.setUserInfo(info);
                            user.setAddressDisplayFlag(type);

                            BaseApplication.getInstance().saveUserInfo(user);
                            ToastUtils.showToast(s,true);

                            finishActivity();
                        }
                    });
        }

    }

    private void finishActivity(){
        Intent intent = getIntent();
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }
}
