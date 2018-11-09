package j.com.jetpack.article;

import java.util.List;

import io.reactivex.Flowable;
import j.com.jetpack.data.GankIoDataBean;
import j.com.jetpack.data.GankIoItemBean;
import j.com.jetpack.database.ArticleDao;

public class ArticleLocalRepository implements ArticlesDataSource {

    private ArticleDao articleDao;


    private volatile static ArticleLocalRepository INSTANCE = null;

    public static ArticleLocalRepository getInstance(){
        if (INSTANCE == null){
            synchronized (ArticleLocalRepository.class){
                if (INSTANCE == null) {
                    INSTANCE = new ArticleLocalRepository();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Flowable<GankIoDataBean> getArticles(int page) {
        return null;
    }

    @Override
    public Flowable<List<GankIoItemBean>> getLocalArticles() {
        return articleDao.getAllArticles();
    }
}
