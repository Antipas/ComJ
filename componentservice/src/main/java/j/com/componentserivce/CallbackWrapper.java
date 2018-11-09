package j.com.componentserivce;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import io.reactivex.subscribers.DisposableSubscriber;
import j.com.componentserivce.base.BaseResult;
import j.com.componentserivce.base.BaseView;

public abstract class CallbackWrapper <T extends BaseResult> extends DisposableSubscriber<T> {

    private WeakReference<BaseView> weakReference;

    public CallbackWrapper(BaseView view) {
        this.weakReference = new WeakReference<>(view);
    }

    protected abstract void onSuccess(T t);

    @Override
    public void onNext(T t) {
        if(t.isSuccessStatus()){
            onSuccess(t);
        } else{
            onError(new Exception(t.status));

        }
        BaseView view = weakReference.get();
        if(view != null){
            view.stopLoading();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        BaseView view = weakReference.get();
        if(view != null){
            view.startLoading();
        }
    }

    @Override
    public void onError(Throwable e) {
        BaseView view = weakReference.get();
        if(view != null){
            if (e instanceof SocketTimeoutException) {
                view.onTimeout();

            } else if (e instanceof IOException) {
                view.onNetworkError();
            } else {
                view.onUnknownError(e.getMessage());
            }
            view.stopLoading();
        }

    }

    @Override
    public void onComplete() {

    }

}