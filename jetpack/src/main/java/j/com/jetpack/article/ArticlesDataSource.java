package j.com.jetpack.article;

import io.reactivex.Flowable;
import j.com.jetpack.data.GankIoDataBean;
import rx.Observable;

public interface ArticlesDataSource {

    Flowable<GankIoDataBean> getArticles(int page);

}
