package j.com.comj;

import android.app.Application;

import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;

import androidx.annotation.NonNull;
import es.dmoral.toasty.Toasty;
import timber.log.Timber;

public class ComJAppliaction extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        UIRouter.getInstance().registerUI("app");
        Router.registerComponent("j.com.jetpack.applike.JetpackLike");
        Router.registerComponent("j.com.module.applike.ModuleLike");
        
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Toasty.Config.getInstance()
                .setErrorColor(getResources().getColor(android.R.color.holo_red_light)) // optional
//                .setInfoColor(@ColorInt int infoColor) // optional
//                .setSuccessColor(@ColorInt int successColor) // optional
//                .setWarningColor(@ColorInt int warningColor) // optional
//                .setTextColor(@ColorInt int textColor) // optional
//                .tintIcon(boolean tintIcon) // optional (apply textColor also to the icon)
//                .setToastTypeface(@NonNull Typeface typeface) // optional
//                            .setTextSize(int sizeInSp) // optional
                .apply();
    }
}
