package j.com.componentserivce.base;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;
import j.com.componentservice.R;


public abstract class BaseFragment extends Fragment {

    public void onNetworkError() {
        Toasty.error(getContext(), "netowrk error").show();
    }

    public void onTimeout() {
        Toasty.error(getContext(), "netowrk timeout").show();
    }

    public void onUnknownError(String message) {
        Toasty.error(getContext(), "unknown error").show();

    }

    public void showSuccessMsg(String msg) {
        Toasty.success(getContext(), msg).show();
    }

    public void showErrorMsg(String msg) {
        Toasty.error(getContext(), msg).show();
    }


}
