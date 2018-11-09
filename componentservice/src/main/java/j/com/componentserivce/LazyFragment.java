package j.com.componentserivce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import j.com.componentserivce.base.LazyBaseFragment;
import j.com.componentservice.R;
import timber.log.Timber;

public class LazyFragment extends LazyBaseFragment {
    private boolean isInit = false;//真正要显示的View是否已经被初始化（正常加载）
    private Bundle savedInstanceState;
    public static final String INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad";
    private boolean isLazyLoad = true;
    private FrameLayout layout;
    private boolean isStart = false;//是否处于可见状态，in the screen

    @Override
    protected final void onCreateView(Bundle savedInstanceState) {

        super.onCreateView(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        }
        //判断是否懒加载
        if (isLazyLoad) {
            //一旦isVisibleToUser==true即可对真正需要的显示内容进行加载
            if (getUserVisibleHint() && !isInit) {
                this.savedInstanceState = savedInstanceState;
                onCreateViewLazy(savedInstanceState);
                isInit = true;
            } else {
                //进行懒加载
                layout = new FrameLayout(getApplicationContext());
                layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_lazy_loading, null);
                ((ProgressBar)view.findViewById(R.id.progress)).getIndeterminateDrawable().setColorFilter(container.getContext().getResources().getColor(R.color.common_appthemecolor), android.graphics.PorterDuff.Mode.MULTIPLY);
                layout.addView(view);
                super.setContentView(layout);
            }
        } else {
            //不需要懒加载，开门江山，调用onCreateViewLazy正常加载显示内容即可
            onCreateViewLazy(savedInstanceState);
            isInit = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Timber.d("setUserVisibleHint() called with: " + "isVisibleToUser = [" + isVisibleToUser + "]");
        //一旦isVisibleToUser==true即可进行对真正需要的显示内容的加载

        //可见，但还没被初始化
        if (isVisibleToUser && !isInit && getContentView() != null) {
            onCreateViewLazy(savedInstanceState);
            isInit = true;
            onResumeLazy();
        }
        //已经被初始化（正常加载）过了
        if (isInit && getContentView() != null) {
            if (isVisibleToUser) {
                isStart = true;
                onFragmentStartLazy();
            } else {
                isStart = false;
                onFragmentStopLazy();
            }
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        //判断若isLazyLoad==true,移除所有lazy view，加载真正要显示的view
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            View view = inflater.inflate(layoutResID, layout, false);
            layout.addView(view);
        }
        //否则，开门见山，直接加载
        else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void setContentView(View view) {
        //判断若isLazyLoad==true,移除所有lazy view，加载真正要显示的view
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            layout.addView(view);
        }
        //否则，开门见山，直接加载
        else {
            super.setContentView(view);
        }
    }

    @Deprecated
    @Override
    public final void onStart() {
        Timber.d("onStart() : " + "getUserVisibleHint():" + getUserVisibleHint());
        super.onStart();
        if (isInit && !isStart && getUserVisibleHint()) {
            isStart = true;
            onFragmentStartLazy();
        }
    }

    @Deprecated
    @Override
    public final void onStop() {
        super.onStop();
        Timber.d("onStop() called: " + "getUserVisibleHint():" + getUserVisibleHint());
        if (isInit && isStart && getUserVisibleHint()) {
            isStart = false;
            onFragmentStopLazy();
        }
    }

    //当Fragment被滑到可见的位置时，调用
    protected void onFragmentStartLazy() {
        Timber.d("onFragmentStartLazy() called with: " + "");
    }

    //当Fragment被滑到不可见的位置，offScreen时，调用
    protected void onFragmentStopLazy() {
        Timber.d("onFragmentStopLazy() called with: " + "");
    }

    protected void onCreateViewLazy(Bundle savedInstanceState) {
        Timber.d( "onCreateViewLazy() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
    }

    protected void onResumeLazy() {
        Timber.d("onResumeLazy() called with: " + "");
    }

    protected void onPauseLazy() {
        Timber.d("onPauseLazy() called with: " + "");
    }

    protected void onDestroyViewLazy() {

    }

    @Override
    @Deprecated
    public final void onResume() {
        super.onResume();
        if (isInit) {
            onResumeLazy();
        }
    }

    @Override
    @Deprecated
    public final void onPause() {
        Timber.d( "onPause() : " + "getUserVisibleHint():" + getUserVisibleHint());
        super.onPause();
        if (isInit) {
            onPauseLazy();
        }
    }

    @Override
    @Deprecated
    public final void onDestroyView() {
        Timber.d("onDestroyView() : " + "getUserVisibleHint():" + getUserVisibleHint());
        super.onDestroyView();
        if (isInit) {
            onDestroyViewLazy();
        }
        isInit = false;
    }
}