package j.com.jetpack.article;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.disposables.CompositeDisposable;
import j.com.componentserivce.CallbackWrapper;
import j.com.componentserivce.util.schedulers.BaseSchedulerProvider;
import j.com.jetpack.data.GankIoDataBean;

public class ArticlePresenter implements ArticleContract.Presenter, LifecycleObserver {



    private ArticlesRepository repository;

    private ArticleContract.View mView;

    private final BaseSchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    public ArticlePresenter(@NonNull ArticlesRepository repository,
                            @NonNull ArticleContract.View view,
                            @NonNull BaseSchedulerProvider schedulerProvider){
        this.repository = repository;
        this.mView = view;
        this.mSchedulerProvider = schedulerProvider;
        view.setPresenter(this);

        if (view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
        }
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void subscribe() {
        mCompositeDisposable.dispose();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getArticles(int page) {
        mCompositeDisposable.add(
                repository.getArticles(page)
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribeWith(new CallbackWrapper<GankIoDataBean>(mView) {
                            @Override
                            protected void onSuccess(GankIoDataBean dataBean) {


                                mView.showArticles(dataBean);
                            }
                        })
        );


    }
}
