package j.com.jetpack;

import android.os.Bundle;


import com.luojilab.router.facade.annotation.Autowired;
import com.luojilab.router.facade.annotation.RouteNode;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import j.com.componentserivce.base.BaseActivity;
import j.com.componentserivce.util.schedulers.SchedulerProvider;
import j.com.jetpack.article.ArticleContract;
import j.com.jetpack.article.ArticleLocalRepository;
import j.com.jetpack.article.ArticlePresenter;
import j.com.jetpack.article.ArticlesRepository;
import j.com.jetpack.data.GankIoDataBean;

@RouteNode(path = "/jetpack", desc = "jetpack")
public class MainActivity extends BaseActivity implements ArticleContract.View{

    @BindView(R2.id.recyclerview)
    RecyclerView recyclerview;

    private ArticleContract.Presenter presenter;

//    private FeedListAdapter adapter;

    @Autowired(name = "desc")
    String desc;

    @Override
    protected void initView(Bundle savedInstanceState) {

//        adapter = new FeedListAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
//        recyclerview.setAdapter(adapter);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ArticlePresenter(
                ArticlesRepository.getInstance(getApplicationContext()),
                ArticleLocalRepository.getInstance(),
                this,
                SchedulerProvider.getInstance()
        );



    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    public void showArticles(GankIoDataBean dataBean) {
//        dataList.addAll(dataBean.getResults());

    }

    @Override
    public void setPresenter(ArticleContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
