package j.com.jetpack.article;

import java.util.List;

import io.reactivex.Flowable;
import j.com.jetpack.data.GankIoDataBean;
import j.com.jetpack.data.GankIoItemBean;
import rx.Observable;

public interface ArticlesDataSource {

    Flowable<GankIoDataBean> getArticles(int page);


    Flowable<List<GankIoItemBean>> getLocalArticles();
}
