package j.com.jetpack.api;

import io.reactivex.Flowable;
import j.com.jetpack.data.GankIoDataBean;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArticleApi {

    @GET("xiandu/data/id/appinn/count/{size}/page/{page}")
    Flowable<GankIoDataBean> getArticleList(@Path("size")int size, @Path("page")int page);
}
