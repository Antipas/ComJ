package j.com.componentserivce.base;


public interface BaseView<T> {

    void setPresenter(T presenter);

    void showMessage(String message);

    void onUnknownError(String message);

    void onNetworkError();

    void onTimeout();

    void stopLoading();

    void startLoading();
}
