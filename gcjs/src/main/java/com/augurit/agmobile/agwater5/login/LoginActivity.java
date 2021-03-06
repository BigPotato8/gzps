package com.augurit.agmobile.agwater5.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.augurit.agmobile.agwater5.MainActivity;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlConstant;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.dict.GcjsDictRepository;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.gcjs.util.LoginUtil;
import com.augurit.agmobile.agwater5.gcjspad.MainPadActivity;
import com.augurit.agmobile.agwater5.login.method.AwSsoAccountLoginMethod;
import com.augurit.agmobile.agwater5.login.util.AwTokenInterceptor;
import com.augurit.agmobile.agwater5.login.util.BitmapChange;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.LoginSettings;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.busi.common.update.ApkUpdateManager;
import com.augurit.agmobile.busi.common.update.utils.CheckUpdateUtils;
import com.augurit.agmobile.busi.ui.login.BaseLoginActivity;
import com.augurit.agmobile.busi.ui.login.adapter.UserNameAdapter;
import com.augurit.agmobile.common.im.timchat.manager.IMManager;
import com.augurit.agmobile.common.im.timchat.ui.GlobalAlertActivity;
import com.augurit.agmobile.agwater5.im.IMConstant;
import com.augurit.agmobile.common.lib.net.NetConnectionUtil;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.permission.PermissionUtil;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.widget.WEUIButton;
import com.augurit.common.common.util.ProgressDialogUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMUserStatusListener;
import com.zhouyou.http.EasyHttp;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.filetransfer.RequestOption;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.HttpException;

/**
 * ??????Activity
 *
 * @author ????????? ???xiejiexin
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.agwater5.login
 * @createTime ???????????? ???2018/8/17
 * @modifyBy ????????? ???xiejiexin
 * @modifyTime ???????????? ???2018/8/17
 * @modifyMemo ???????????????
 */
public class LoginActivity extends BaseLoginActivity {

        private String[] appUpdateUrlArr = {"http://19.130.245.196:18081/"};   // ??????ip 210.72.5.106 ??????????????????????????????ip??????
//    private String[] appUpdateUrlArr = {"http://219.130.221.47:8084/"};   // ??????ip 210.72.5.106 ??????????????????????????????ip??????
    protected View view;
    protected ViewGroup fl_content;
    protected AutoCompleteTextView at_account;
    protected EditText et_password;
    protected WEUIButton btn_login;
    protected View ll_auto_login;
    protected View par_auto_login;
    protected CheckBox cb_auto_login;
    protected CheckBox auto_login_box;
    protected AwSsoAccountLoginMethod mLoginMethod;
    protected Disposable mLoginDisposable;
    protected Button btn_setting;
    protected ApkUpdateManager mApkUpdateManager;
    private AutoCompleteTextView at_phone;
    private AutoCompleteTextView ph_account;
    private ImageView ph_user_icon;
    private ImageView iv_phone_icon;
    private RelativeLayout rl_code;
    private TextView tv_get_code;
    private EditText et_code;
    private ImageView iv_user_icon;
    private ImageView iv_psw_icon;
    private RelativeLayout rl_user_code;
    private EditText et_user_code;
    private ImageView iv_user_get_code;
    private WEUIButton btn_login_phone;
    private Handler mHandler;
    private String mSession;
    private String verCodeSession;
    private String rVerCode;
    private String mBase64Code;

    @Override
    public void showIndex() {


    }

    @Override
    protected LoginViewSettings initSettings() {
        PermissionUtil.instance().with(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .requestCode(101)
                .check(permissions -> {
                });
        LoginManager.getInstance()
                .getSettings(new LoginSettings(LoginConstant.SERVER_URL,
                        LoginConstant.SERVER_URL, false));
        return super.initSettings();
    }


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isPad(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// ??????
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ??????
        }
        LoginSettings loginSettings = LoginManager.getInstance().getSettings();
        if (loginSettings == null) {
            loginSettings = LoginManager.getInstance()
                    .getSettings(new LoginSettings(LoginConstant.SERVER_URL,
                            LoginConstant.SERVER_URL, false));
        }
        this.initView();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
                if (msg.what == 0) {
//                    if(msg.obj !=null){
//                        Glide.with(LoginActivity.this).load((Bitmap) msg.obj).error(R.drawable.ic_error).into(iv_user_get_code);
//                    }
                    iv_user_get_code.setImageBitmap((Bitmap) msg.obj);
                    iv_user_get_code.setClickable(true);
                } else if (msg.what == 1) {
                    String message = msg.getData().getString("msg");
                    tv_get_code.setClickable(true);
                    ToastUtil.shortToast(LoginActivity.this, message);
                }
            }
        };
        if (IMConstant.IS_ENABLED) {
            if (IMConstant.IS_TEST) {
                IMConstant.initTestUsers(this);
            }
            initIm();
        }

        AwUrlManager.setServerUrl(loginSettings.getServerUrl());
        EasyHttp.getInstance().setBaseUrl(AwUrlManager.serverUrl());
        LoginSettings settings = LoginManager.getInstance().getSettings();
        if (settings.isAutoLogin()) {
            cb_auto_login.setChecked(true);
            if (IMConstant.IS_ENABLED) {
                if (IMConstant.IS_TEST) {
                    // ??????????????????
                    User lastUser = LoginManager.getInstance().getLastUser(false);
                    if (IMConstant.isTestUser(lastUser.getLoginName())) {
                        setViewLoading(true);
                        IMManager.getInstance().login(lastUser.getLoginName(),
                                IMConstant.getTestUserSig(lastUser.getLoginName()),
                                aVoid -> {
                                    IMManager.getInstance().getSettings().setRootOrg("A", "????????????????????????????????????");
                                    IMManager.getInstance().getSettings().setUserOrg("150", "????????????");
                                    autoLogin();
                                },
                                error -> {
                                    setViewLoading(false);
                                    Toast.makeText(this, R.string.login_auto_login_failed, Toast.LENGTH_SHORT).show();
                                });
                        return;
                    }
                } else {
                    autoLogin();
                }
            }
//            autoLogin();
        }

        //??????????????????,???ip?????????????????????
        new GcjsDictRepository().updateDicts().subscribe(new Consumer<List<Boolean>>() {
            @Override
            public void accept(List<Boolean> booleans) throws Exception {
                Log.e("GcjsDictRepository", booleans == null ? "0" : booleans.size() + "");
            }
        }, throwable -> throwable.printStackTrace());

    }


    protected void initView() {
        setContentView(R.layout.login_view_account_gcjs);

        View btn_back = findViewById(R.id.btn_back);
//        btn_back.setVisibility(View.GONE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView versionText = findViewById(R.id.text_version);
//        findViewById(R.id.ll_login_bottom).setVisibility(View.GONE);
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            versionText.setText("???????????????V" + packageInfo.versionName);
        } catch (Exception e) {
            versionText.setVisibility(View.GONE);
        }
        this.btn_login = findViewById(R.id.btn_login);
        this.btn_login_phone = findViewById(R.id.btn_login_phone);
        this.btn_setting = findViewById(R.id.btn_setting);
        this.at_account = findViewById(R.id.at_account);
        this.ph_account = findViewById(R.id.ph_account);
        this.ph_user_icon = findViewById(R.id.ph_user_icon);
        this.et_password = findViewById(R.id.et_password);
        this.cb_auto_login = findViewById(R.id.cb_auto_login);
        this.ll_auto_login = findViewById(R.id.ll_auto_login);
        this.par_auto_login = findViewById(R.id.par_auto_login);
        this.auto_login_box = findViewById(R.id.auto_login_box);
        this.at_phone = findViewById(R.id.at_phone);
        this.iv_phone_icon = findViewById(R.id.iv_phone_icon);
        this.rl_code = findViewById(R.id.rl_code);
        this.et_code = findViewById(R.id.et_code);
        this.tv_get_code = findViewById(R.id.tv_get_code);
        this.iv_user_icon = findViewById(R.id.iv_user_icon);
        this.iv_psw_icon = findViewById(R.id.iv_psw_icon);
        this.et_user_code = findViewById(R.id.et_user_code);
        this.rl_user_code = findViewById(R.id.rl_user_code);
        this.iv_user_get_code = findViewById(R.id.iv_user_get_code);
        fl_content = findViewById(R.id.fl_content);
        initVisiable();
        iv_user_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateCode();
            }
        });


        this.et_password.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId == 6 || event != null && 66 == event.getKeyCode() && 0 == event.getAction()) && this.btn_login.isEnabled()) {
                this.btn_login.performClick();
            }
            return false;
        });

//        btn_setting.setOnClickListener((v) -> {
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction
//                    .setCustomAnimations(R.anim.anim_view_right_in, R.anim.anim_view_left_out,
//                            R.anim.anim_view_left_in, R.anim.anim_view_right_out)
//                    .replace(R.id.fl_content, new LoginSettingsFragment())
//                    .addToBackStack(null)
//                    .commit();
//        });
        this.ll_auto_login.setOnClickListener((v) -> {
            this.cb_auto_login.performClick();
        });
        this.par_auto_login.setOnClickListener((v) -> {
            this.auto_login_box.performClick();
        });
        this.btn_login.setOnClickListener((v) -> {
            login();
        });

        // ???????????????
        //new???????????????,???????????????,?????????????????????????????????
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);
        this.tv_get_code.setOnClickListener((v) -> {
//                myCountDownTimer.start();//???????????????
            //???????????????????????????
            getVerCode(myCountDownTimer);
        });

        this.btn_login_phone.setOnClickListener((v) -> {//???????????????
            phoneLogin();
        });
        User lastUser = LoginManager.getInstance().getLastUser(true);
        if (lastUser != null) {
            this.at_account.setText(lastUser.getLoginName());
        }

        List<User> users = LoginManager.getInstance().getUsers(true);
        UserNameAdapter adapter = new UserNameAdapter(this, users);
        int status = getIntent().getIntExtra("status", -1);
        if (status == 0) {
            this.at_account.setAdapter(adapter);
            this.at_account.setThreshold(1);
            this.at_account.setDropDownVerticalOffset(2);
            adapter.setOnItemClickListener(new UserNameAdapter.OnItemClickListener() {
                public void onNameClick(View v, User user) {
                    at_account.setText(user.getLoginName());
                    at_account.setSelection(at_account.getText().length());
                    at_account.dismissDropDown();
                }

                public void onDeleteClick(View v, User user) {
                    LoginManager.getInstance().saveUser(user, false);
                }
            });
        } else {
            this.ph_account.setAdapter(adapter);
            this.ph_account.setThreshold(1);
            this.ph_account.setDropDownVerticalOffset(2);
            adapter.setOnItemClickListener(new UserNameAdapter.OnItemClickListener() {
                public void onNameClick(View v, User user) {
                    ph_account.setText(user.getLoginName());
                    ph_account.setSelection(ph_account.getText().length());
                    ph_account.dismissDropDown();
                }

                public void onDeleteClick(View v, User user) {
                    LoginManager.getInstance().saveUser(user, false);
                }
            });
        }

        LoginViewSettings viewSettings = getViewSettings();
        if (viewSettings != null && viewSettings.checkAutoLogin) {
            this.cb_auto_login.setChecked(true);
            this.auto_login_box.setChecked(true);
        }
//        //????????????
//        mApkUpdateManager = new ApkUpdateManager(this, UpdateState.INNER_UPDATE, () -> {
//        });
//        checkVersionUpdateWithPermissonCheck();

    }

    //??????????????????????????????
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //????????????
        @Override
        public void onTick(long l) {
            //?????????????????????????????????
            tv_get_code.setClickable(false);
            tv_get_code.setText(l / 1000 + "???");
        }

        //?????????????????????
        @Override
        public void onFinish() {
            //?????????Button????????????
            tv_get_code.setText("????????????");
            //???????????????
            tv_get_code.setClickable(true);
        }
    }

    /**
     * ???????????????
     */
    @SuppressLint("CheckResult")
    private void generateCode() {

        LoginSettings loginSettings = LoginManager.getInstance().getSettings();
        if (loginSettings == null) {
            loginSettings = LoginManager.getInstance()
                    .getSettings(new LoginSettings(LoginConstant.SERVER_URL,
                            LoginConstant.SERVER_URL, false));
        }
        String loginCode = loginSettings.getServerUrl().concat(AwUrlConstant.AW_PORT_AUTHENTICATION) + AwUrlConstant.LOGIN_CODE;
        OkHttpClient okHttpClient = new OkHttpClient();
        iv_user_get_code.setClickable(false);
        Request request = new Request.Builder()
                .url(loginCode)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            public void onResponse(Call call, Response response) throws IOException {
                try {
                    InputStream inputStream = response.body().byteStream();//??????????????????
                    mSession = response.headers().get("Set-Cookie");
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mBase64Code = BitmapChange.BitMapToString(bitmap);
                    Message msg = new Message();
                    msg.obj = bitmap;
                    msg.what = 0;
                    mHandler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * ?????????????????????
     */
    @SuppressLint("CheckResult")
    private void getVerCode(MyCountDownTimer myCountDownTimer) {
        String verCodeUrl = AwUrlConstant.VER_CODE;
        Map<String, String> params = new HashMap<>();
        String phoneNum1 = at_phone.getText().toString();
        StringBuilder code = new StringBuilder();
        code.append(LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.AW_PORT_AUTHENTICATION) + AwUrlConstant.VER_CODE);
        if (phoneNum1.equals("")) {
            ToastUtil.shortToast(this, "??????????????????");
            return;
        } else {
            if (LoginUtil.isMobileNO(phoneNum1)) {
                String username = this.ph_account.getText().toString();
                String nameBase64 = null;
                if (!username.equals("")) {
                    nameBase64 = Base64.encodeToString(username.getBytes(), 0, username.getBytes().length, Base64.DEFAULT);
                }
//                params.put("mobile", phoneNum1);
                code.append("?mobile=" + phoneNum1);
                code.append("&username=" + nameBase64);
                this.tv_get_code.setClickable(false);
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(code + "")
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    public void onFailure(Call call, IOException e) {
                        Message msg = new Message();
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", "??????????????????????????????");
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                        e.printStackTrace();
                    }

                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            verCodeSession = response.headers().get("Set-Cookie");
                            //boolean successful = response.isSuccessful();
                            String bodyValue = response.body().string();
                            if ("".equals(bodyValue)) {
                                myCountDownTimer.start();
                                Message msg = new Message();
                                msg.what = 1;
                                Bundle bundle = new Bundle();
                                bundle.putString("msg", "??????????????????");
                                msg.setData(bundle);
                                mHandler.sendMessage(msg);
                            } else {
                                JSONObject jsonObject = new JSONObject(bodyValue);
                                boolean success = jsonObject.getBoolean("success");
                                if (!success) {
                                    String message = jsonObject.getString("message");
                                    Message msg = new Message();
                                    msg.what = 1;
                                    Bundle bundle = new Bundle();
                                    bundle.putString("msg", message);
                                    msg.setData(bundle);
                                    mHandler.sendMessage(msg);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                ToastUtil.shortToast(this, "????????????????????????");
            }

        }
    }

    /**
     * ?????????????????????
     */
    private void phoneLogin() {
        int status = getIntent().getIntExtra("status", -1);
        this.mLoginMethod = new AwSsoAccountLoginMethod(status);
        LoginSettings loginSettings = LoginManager.getInstance().getSettings();
        AwUrlManager.setServerUrl(loginSettings.getServerUrl());
        EasyHttp.getInstance().setBaseUrl(AwUrlManager.serverUrl());

        String phoneNum = at_phone.getText().toString();
        String verCode = et_code.getText().toString();//?????????
        String account = this.ph_account.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.shortToast(this, "???????????????");
            return;
        }
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.shortToast(this, "??????????????????");
            return;
        }
        if (TextUtils.isEmpty(verCode)) {
            ToastUtil.shortToast(this, "????????????????????????");
            return;
        }

        this.setViewLoading(true);

        // 20190522 ??????IM???????????????????????????????????????????????????????????????
        if (!IMConstant.IS_TEST) {
            doGetVerCodeAndLogin(account, phoneNum, verCode);
        } else {
            //doLogin(account, password);
            fakeLogin();
        }

    }

    private void initVisiable() {
        int status = getIntent().getIntExtra("status", -1);
        //0?????????????????????1????????????????????????
        if (status == 0) {
            // ????????????
            at_phone.setVisibility(View.GONE);
            iv_phone_icon.setVisibility(View.GONE);
            rl_code.setVisibility(View.GONE);
            tv_get_code.setVisibility(View.GONE);
            et_code.setVisibility(View.GONE);
            btn_login_phone.setVisibility(View.GONE);
            ph_account.setVisibility(View.GONE);
            ph_user_icon.setVisibility(View.GONE);
            // ????????????
            at_account.setVisibility(View.VISIBLE);
            iv_user_icon.setVisibility(View.VISIBLE);
            et_password.setVisibility(View.VISIBLE);
            iv_psw_icon.setVisibility(View.VISIBLE);
            ll_auto_login.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.VISIBLE);
            rl_user_code.setVisibility(View.VISIBLE);
//            Glide.with(LoginActivity.this).load(R.drawable.wait).into(iv_user_get_code);
            iv_user_get_code.setImageResource(R.drawable.wait);
            //???????????????
            generateCode();
        } else if (status == 1) {
            // ????????????
            at_phone.setVisibility(View.VISIBLE);
            iv_phone_icon.setVisibility(View.VISIBLE);
            rl_code.setVisibility(View.VISIBLE);
            tv_get_code.setVisibility(View.VISIBLE);
            et_code.setVisibility(View.VISIBLE);
            btn_login_phone.setVisibility(View.VISIBLE);
            ph_account.setVisibility(View.VISIBLE);
            ph_user_icon.setVisibility(View.VISIBLE);
            par_auto_login.setVisibility(View.VISIBLE);
            // ????????????
            at_account.setVisibility(View.GONE);
            iv_user_icon.setVisibility(View.GONE);
            et_password.setVisibility(View.GONE);
            iv_psw_icon.setVisibility(View.GONE);
            btn_login.setVisibility(View.GONE);
            rl_user_code.setVisibility(View.GONE);
            ll_auto_login.setVisibility(View.GONE);
        }
    }

    @Override
    public void jumpToMain() {

        //??????token????????????
        int status = getIntent().getIntExtra("status", -1);
        this.mLoginMethod = new AwSsoAccountLoginMethod(status);
        LoginManager.getInstance().setTokenInterceptor(new AwTokenInterceptor(status));
        if (isPad(this)) {//??????
//        if (false){
            startActivity(new Intent(this, MainPadActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    /**
     * ???????????????????????????
     */
    protected void login() {
        int status = getIntent().getIntExtra("status", -1);
        this.mLoginMethod = new AwSsoAccountLoginMethod(status);
        LoginSettings loginSettings = LoginManager.getInstance().getSettings();
        AwUrlManager.setServerUrl(loginSettings.getServerUrl());
        EasyHttp.getInstance().setBaseUrl(AwUrlManager.serverUrl());

        String account = this.at_account.getText().toString();
        String password = this.et_password.getText().toString();
        String code = et_user_code.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.shortToast(this, "???????????????");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.shortToast(this, "????????????");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.shortToast(this, "??????????????????");
            return;
        }
        this.setViewLoading(true);

        // 20190522 ??????IM???????????????????????????????????????????????????????????????
        if (!IMConstant.IS_TEST) {
            doGetOrgAndLogin(account, password, code);
        } else {
            //doLogin(account, password);
            fakeLogin();
        }


    }

    protected void fakeLogin() {
        String account = this.at_account.getText().toString();
        String password = this.et_password.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.shortToast(this, "???????????????");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.shortToast(this, "????????????");
            return;
        }
        String imUserId = account;
        String imUserSig = null;
        if (IMConstant.IS_TEST) {
            imUserSig = IMConstant.getTestUserSig(account);
            if (imUserSig != null) {
                account = "??????";
                password = "123";
            }
        }

        User user = new User();
        user.setId("dianyi");
        user.setUserId("dianyi");
        user.setOrgId("A");
        user.setLoginName("??????");
        user.setLoginPwd("123");
        LoginManager.getInstance().saveUser(user);
        LoginSettings settings = LoginManager.getInstance().getSettings();
        settings.setAutoLogin(cb_auto_login.isChecked());
        LoginManager.getInstance().saveSettings(settings);

        loginIm(imUserId, imUserSig);

    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????
     *
     * @param account
     * @param password
     * @param code
     */
    private void doGetOrgAndLogin(String account, String password, String code) {
        mLoginMethod.setParams(account, password, code, mSession, mBase64Code);
        mLoginDisposable = LoginManager.getInstance()
                .getOrganizations(mLoginMethod, !NetConnectionUtil.isConnected(this))
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess()) {
                        List<Organization> data = listApiResult.getData();
                        if (ListUtil.isNotEmpty(data) && data.size() > 1) {
                            // ????????????????????????
                            showOrgChoose(data);
                        } else {
                            // ????????????
                            if (ListUtil.isNotEmpty(data) && data.size() > 0) {
                                Organization org = data.get(0);
                                mLoginMethod.setLoginOrg(org.getOrgId());
                                IMManager.getInstance().getSettings().setRootOrg(org.getOrgId(), org.getOrgName());
                            }
                            doLogin(account, password);
                        }
                    } else {
                        JsonObject object = new JsonParser().parse(listApiResult.getMessage()).getAsJsonObject();

                        String msg = object.get("message").getAsString();
                        if (TextUtils.isEmpty(msg)) {
                            msg = "????????????????????????";
                        }
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        setViewLoading(false);
                    }
                }, this::notifyLoginError);
    }

    /**
     * ???????????????????????????
     *
     * @param phoneNum
     * @param verCode
     */
    private void doGetVerCodeAndLogin(String account, String phoneNum, String verCode) {
        mLoginMethod.setParam(account, phoneNum, verCode, verCodeSession);
//        doLoginForPhone(account, phoneNum,verCode);
        mLoginDisposable = LoginManager.getInstance()
                .getOrganizations(mLoginMethod, !NetConnectionUtil.isConnected(this))
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess()) {
                        List<Organization> data = listApiResult.getData();
                        if (ListUtil.isNotEmpty(data) && data.size() > 1) {
                            // ????????????????????????
                            showOrgChoose(data);
                        } else {
                            // ????????????
                            if (ListUtil.isNotEmpty(data) && data.size() > 0) {
                                Organization org = data.get(0);
                                mLoginMethod.setLoginOrg(org.getOrgId());
                                IMManager.getInstance().getSettings().setRootOrg(org.getOrgId(), org.getOrgName());
                            }
                            doLoginForPhone(account, phoneNum, verCode);
                        }
                    } else {
                        JsonObject object = new JsonParser().parse(listApiResult.getMessage()).getAsJsonObject();
                        String msg = object.get("message").getAsString();
                        if (TextUtils.isEmpty(msg)) {
                            msg = "????????????????????????";
                        }
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        setViewLoading(false);
                    }
                }, this::notifyLoginError);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param account
     * @param phoneNum
     * @param verCode
     */
    private void doLoginForPhone(String account, String phoneNum, String verCode) {
        // ??????????????????
        String imUserId = account;
        String imUserSig = null;
        if (IMConstant.IS_TEST) {
            imUserSig = IMConstant.getTestUserSig(account);
            if (imUserSig != null) {
                account = "??????";
                phoneNum = "123";
            }
        }
        mLoginMethod.setParam(account, phoneNum, verCode, verCodeSession);
        String finalImUserSig = imUserSig;
        mLoginDisposable = LoginManager.getInstance().login(mLoginMethod, !NetConnectionUtil.isConnected(this))
                .subscribe((userApiResult) -> {
                    if (userApiResult.isSuccess()) {
//                        showPostLogin();
                        LoginSettings settings = LoginManager.getInstance().getSettings();
                        settings.setAutoLogin(this.auto_login_box.isChecked());
                        LoginManager.getInstance().saveSettings(settings);

                        if (IMConstant.IS_TEST) {
                            // ??????????????????
                            if (finalImUserSig != null) {
                                loginIm(imUserId, finalImUserSig);
                                return;
                            }
                        } else {
                            // ??????IM??????
                            User user = userApiResult.getData();
                            if (user != null && !TextUtils.isEmpty(user.getUserSig())) {
                                loginIm(user.getLoginName(), user.getUserSig());
                                return;
                            }
                        }
                        // ???IM????????????????????????????????????
                        jumpToMain();
                    } else {
                        String msg = userApiResult.getMessage();
                        if (TextUtils.isEmpty(msg)) {
                            msg = "??????????????????????????????????????????";
                        }
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        setViewLoading(false);
                    }

                }, this::notifyLoginError);
    }

    /**
     * ????????????
     *
     * @param account
     * @param password
     */
    private void doLogin(String account, String password) {
        // ??????????????????
        String imUserId = account;
        String imUserSig = null;
        if (IMConstant.IS_TEST) {
            imUserSig = IMConstant.getTestUserSig(account);
            if (imUserSig != null) {
                account = "??????";
                password = "123";
            }
        }
        mLoginMethod.setParams(account, password);
        String finalImUserSig = imUserSig;
        mLoginDisposable = LoginManager.getInstance().login(mLoginMethod, !NetConnectionUtil.isConnected(this))
                .subscribe((userApiResult) -> {
                    if (userApiResult.isSuccess()) {
//                        showPostLogin();
                        LoginSettings settings = LoginManager.getInstance().getSettings();
                        settings.setAutoLogin(this.cb_auto_login.isChecked());
                        LoginManager.getInstance().saveSettings(settings);

                        if (IMConstant.IS_TEST) {
                            // ??????????????????
                            if (finalImUserSig != null) {
                                loginIm(imUserId, finalImUserSig);
                                return;
                            }
                        } else {
                            // ??????IM??????
                            User user = userApiResult.getData();
                            if (user != null && !TextUtils.isEmpty(user.getUserSig())) {
                                loginIm(user.getLoginName(), user.getUserSig());
                                return;
                            }
                        }
                        // ???IM????????????????????????????????????
                        jumpToMain();
                    } else {
                        String msg = userApiResult.getMessage();
                        if (TextUtils.isEmpty(msg)) {
                            msg = "????????????????????????";
                        } else if ("???????????????????????????????????????".equals(msg) || "??????????????????".equals(msg)) {
                            generateCode();
                        }
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        setViewLoading(false);
                    }

                }, this::notifyLoginError);
    }

    /**
     * ????????????????????????
     *
     * @param organizations
     */
    private void showOrgChoose(List<Organization> organizations) {
        AwLoginOrganizationView organizationView = new AwLoginOrganizationView(this, fl_content, organizations);
        organizationView.setOnItemClickListener((position, organization) -> {
            mLoginMethod.setLoginOrg(organization.getOrgId());
            IMManager.getInstance().getSettings().setRootOrg(organization.getOrgId(), organization.getOrgName());
            doLogin(mLoginMethod.getUserName(), mLoginMethod.getPassword());
        });
        organizationView.setOnDismissListener(aVoid -> {
            setViewLoading(false);
        });
        organizationView.show();
    }

    private void notifyLoginError(Throwable throwable) {
        throwable.printStackTrace();
        String msg = throwable.getMessage();
        if (throwable instanceof ConnectException) {
            msg = this.getString(R.string.login_toast_login_failed_connect);
        } else if (throwable instanceof SocketTimeoutException) {
            msg = this.getString(R.string.login_toast_login_failed_timeout);
        } else if (msg.isEmpty()) {
            msg = this.getString(R.string.login_toast_login_failed);
        } else if (msg.contains("??????")) {
            msg = msg.replace("??????", "??????");
        }

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        this.setViewLoading(false);
    }

    protected void setViewLoading(boolean isLoading) {
        this.btn_login.setLoading(isLoading);
        this.at_account.setEnabled(!isLoading);
        this.et_password.setEnabled(!isLoading);
        this.ll_auto_login.setClickable(!isLoading);
        this.par_auto_login.setClickable(!isLoading);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mLoginDisposable != null && !this.mLoginDisposable.isDisposed()) {
            this.mLoginDisposable.dispose();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }


    private void checkVersionUpdateWithPermissonCheck() {
        String updateUrl = appUpdateUrlArr[new Random().nextInt(appUpdateUrlArr.length)];
//        String updateUrl = "http://" + LoginConstant.BASE_GZPS_URL + "/appFile/apk_version.json";
//        String updateUrl =  "http://zhsz.gzcc.gov.cn:8080/agsupport/pdaUpdate/apk_version.json";
        CheckUpdateUtils.setServerUrl(updateUrl);
        CheckUpdateUtils.setUpdatePath("aplanmis-rest/app/apk_version.json");
        mApkUpdateManager.checkUpdate(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mApkUpdateManager != null) {
            mApkUpdateManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * im?????????????????????600??????
     */
    private void initIm() {
        // Realm?????????
//        Realm.init(this);
        // IM?????????
        IMManager.InitSettings settings = new IMManager.InitSettings();
        if (IMConstant.IS_TEST) {
            settings.setImSdkAppId(1400203324);  // IM APPID ???????????????????????????
        } else {
            settings.setImSdkAppId(1400212943);
        }
        settings.setIsTest(IMConstant.IS_TEST);
        settings.setTestUserIds(IMConstant.getTestUserIds());
        settings.setMiPush("2882303761517997488", "5301799760488");
        settings.setAppEntryActivity(MainActivity.class);
        settings.setMessageMainActivity(MainActivity.class);
        settings.setNotifyBlackList(LoginActivity.class);
        settings.setNotificationIcon(R.mipmap.ic_gcjs_start);
        settings.setGroupMemberLimit(20);   // TODO ????????????????????????20???
        String baseUrlTemp;
        String orgUrlTemp;
        if (IMConstant.IS_TEST) {
            baseUrlTemp = "http://139.159.247.149:9000/agcloud/opus/";
            orgUrlTemp = "rest/om/org/getOpuOmSubOrgPosUserByParentId.do";
        } else {
//            baseUrlTemp = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.AW_PORT_CP_REST);
            baseUrlTemp = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.AW_PORT_CP_REST_TEST);
            orgUrlTemp = AwUrlConstant.AW_GET_ORGANIZATION_TREE;
        }
        settings.setBaseUrl(baseUrlTemp);
        settings.setOrganizationUrl(orgUrlTemp);
        // ?????????????????? ?????????????????????????????? ????????????
        settings.setOfflinePushListener(notification -> {
            if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                notification.doNotify(getApplication(), R.mipmap.ic_launcher);
            }
        });
        // ????????????????????????
        settings.setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                Intent intent = GlobalAlertActivity.getIntent(LoginActivity.this, GlobalAlertActivity.TYPE_FORCE_OFFLINE, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onUserSigExpired() {
                Intent intent = GlobalAlertActivity.getIntent(LoginActivity.this, GlobalAlertActivity.TYPE_USER_SIG_EXPIRED, LoginActivity.class);
                startActivity(intent);
            }
        });
        settings.setLogoutRunnables(() -> LoginManager.getInstance().logoff());
        IMManager.getInstance().init(getApplication(), settings);

    }

    private void loginIm(String userName, String userSig) {
        setViewLoading(true);
        IMManager.getInstance().login(userName, userSig, aVoid -> {
            if (IMConstant.IS_TEST) {
                User currentUser = LoginManager.getInstance().getCurrentUser();
                currentUser.setLoginName(userName);
                LoginManager.getInstance().saveUser(currentUser);
                IMManager.getInstance().getSettings().setRootOrg("A", "????????????????????????????????????");
                IMManager.getInstance().getSettings().setUserOrg("150", "????????????");
            }
            jumpToMain();
        }, error -> {
            Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
            jumpToMain();
        });
    }

    /**
     * ?????????????????????????????????????????????????????? Google I/O App for Android
     *
     * @param context
     * @return ???????????? True??????????????? False
     */
    public static boolean isPad(Context context) {
        return false;
//        return (context.getResources().getConfiguration().screenLayout
//                & Configuration.SCREENLAYOUT_SIZE_MASK)
//                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
