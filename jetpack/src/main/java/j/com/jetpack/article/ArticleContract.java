package j.com.jetpack.article;

import j.com.componentserivce.base.BasePresenter;
import j.com.componentserivce.base.BaseView;
import j.com.jetpack.data.GankIoDataBean;

public class ArticleContract {

    public interface View extends BaseView<Presenter>{
        void showArticles(GankIoDataBean dataBean);
    }

    public interface Presenter extends BasePresenter {
        void getArticles(int page);

        void getLocalArticles();
    }
}
