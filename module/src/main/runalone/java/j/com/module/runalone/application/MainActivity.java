package j.com.module.runalone.application;

import android.os.Bundle;

import com.luojilab.router.facade.annotation.RouteNode;

import j.com.componentserivce.base.BaseActivity;
import j.com.module.R;

@RouteNode(path = "/module", desc = "module")
public class MainActivity extends BaseActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.module_activity_main;
    }
}
