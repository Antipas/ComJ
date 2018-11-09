package j.com.comj;

import android.os.Bundle;

import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.luojilab.router.facade.annotation.RouteNode;

import j.com.componentserivce.base.BaseActivity;

@RouteNode(path = "/main", desc = "首页")
public class MainActivity extends BaseActivity {

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        UIRouter.getInstance().openUri(MainActivity.this, "ComJ://jetpack/jetpack", null);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }
}
