package j.com.componentserivce.base;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.luojilab.component.componentlib.BuildConfig;
import com.luojilab.component.componentlib.service.AutowiredService;

import java.io.File;

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import j.com.componentserivce.util.ActivityUtils;
import j.com.componentserivce.util.StatusBarUtil;
import j.com.componentservice.R;

public abstract class BaseActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;

    private ProgressDialog loadingDialog;
    private View refreshView;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    /**
     * 界面初始化前期准备
     */
    protected void beforeInit() {

    }

    /**
     * 初始化布局以及View控件
     */
    protected abstract void initView(Bundle savedInstanceState);

    protected void onErrorRefersh() {
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(getString(R.string.common_loading));
        loadingDialog.setCanceledOnTouchOutside(false);
//        loadingDialog.show();

        refreshView = LayoutInflater.from(this).inflate(R.layout.vs_refersh, null);
        refreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onErrorRefersh();
            }
        });

        AutowiredService.Factory.getSingletonImpl().autowire(this);

        beforeInit();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.common_appthemecolor), 0);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            initView(savedInstanceState);
        }
    }

    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    protected void showError() {
        if (refreshView != null) {
            ViewGroup contentView = findViewById(android.R.id.content);
            if (contentView != null && contentView.getChildCount() > 0) {
                contentView.getChildAt(0).setVisibility(View.GONE);
            }
            refreshView.setVisibility(View.VISIBLE);
        }
    }

    protected void showContentView() {
        ViewGroup contentView = findViewById(android.R.id.content);
        if (contentView != null && contentView.getChildCount() > 0) {
            contentView.getChildAt(0).setVisibility(View.VISIBLE);
        }
        refreshView.setVisibility(View.GONE);
    }

    protected void setToolBar(Toolbar toolBar) {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
    }


    protected abstract int getContentViewLayoutID();


    public void setStatusBarTransparent() {
        StatusBarUtil.setTransparent(this);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setStatusBarColor(@ColorInt int color) {
        setStatusBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param statusBarAlpha 透明度
     */
    public void setStatusBarColor(@ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        StatusBarUtil.setColorForSwipeBack(this, color, statusBarAlpha);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(newConfig, dm);
    }

    public void onNetworkError() {
        Toasty.error(getApplicationContext(), "netowrk error").show();
    }

    public void onTimeout() {
        Toasty.error(getApplicationContext(), "netowrk timeout").show();
    }

    public void onUnknownError(String message) {
        Toasty.error(getApplicationContext(), "unknown error").show();

    }

    public void showMessage(String msg) {
        Toasty.info(getApplicationContext(), msg).show();
    }

    public void showSuccessMsg(String msg) {
        Toasty.success(getApplicationContext(), msg).show();
    }

    public void showErrorMsg(String msg) {
        Toasty.error(getApplicationContext(), msg).show();
    }

    public File getMediaStorageDir(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        return mediaStorageDir;
    }

    public void startToPermissionSettings() {
        String sdk = Build.VERSION.SDK; // SDK号

        String model = Build.MODEL; // 手机型号

        String release = Build.VERSION.RELEASE; // android系统版本号
        String brand = Build.BRAND;//手机厂商
        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            gotoMiuiPermission();//小米
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            gotoMeizuPermission();
        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            gotoHuaweiPermission();
        } else {
            startActivityForResult(getAppDetailSettingIntent(), PERMISSION_CODE);

        }
    }

    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return localIntent;
    }


    private void gotoMiuiPermission() {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", getPackageName());
            startActivityForResult(localIntent, PERMISSION_CODE);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", getPackageName());
                startActivity(localIntent);
            } catch (Exception e1) { // 否则跳转到应用详情
                startActivityForResult(getAppDetailSettingIntent(), PERMISSION_CODE);
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private void gotoMeizuPermission() {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            startActivityForResult(intent, PERMISSION_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            startActivityForResult(getAppDetailSettingIntent(), PERMISSION_CODE);
        }
    }

    /**
     * 华为的权限管理页面
     */
    private void gotoHuaweiPermission() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            startActivityForResult(intent, PERMISSION_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            startActivityForResult(getAppDetailSettingIntent(), PERMISSION_CODE);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }else{
            finish();
        }
    }

    public void smartFinish(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }else{
            finish();
        }
    }

    public void stopLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public void startLoading() {
        if (loadingDialog != null) {
            loadingDialog.show();
        }

    }

    private void setupViewFragment(String fName, Bundle bundle, @LayoutRes int id) {
        Fragment fragment = Fragment.instantiate(this, fName, bundle);
        if (fragment == null) {
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), fragment, id);
        }
    }
}
