package j.com.jetpack.article;

import android.content.Context;

import io.reactivex.Flowable;
import j.com.jetpack.JetpackNetworkManager;
import j.com.jetpack.api.ArticleApi;
import j.com.jetpack.data.GankIoDataBean;

public class ArticlesRepository implements ArticlesDataSource{

    private volatile static ArticlesRepository INSTANCE = null;
    private ArticleApi articleApi;

    public static ArticlesRepository getInstance(Context context){
        if (INSTANCE == null){
            synchronized (ArticlesRepository.class){
                if (INSTANCE == null) {
                    INSTANCE = new ArticlesRepository(context);
                }
            }
        }
        return INSTANCE;
    }


    private ArticlesRepository(Context context){
        articleApi = JetpackNetworkManager.getInstance(context).getArticleApi();
    }


    @Override
    public Flowable<GankIoDataBean> getArticles(int page) {
        return articleApi.getArticleList(10, page);
    }
}
