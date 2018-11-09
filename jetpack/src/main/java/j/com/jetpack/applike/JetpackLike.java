package j.com.jetpack.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.applicationlike.RegisterCompManual;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;

import j.com.componentserivce.jetpack.JetpackService;

public class JetpackLike implements IApplicationLike {

    UIRouter uiRouter = UIRouter.getInstance();
    Router router = Router.getInstance();
    @Override
    public void onCreate() {
        uiRouter.registerUI("jetpack");
        router.addService(JetpackService.class.getSimpleName(), new JetpackServcieImp());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("jetpack");
        router.removeService(JetpackService.class.getSimpleName());
    }
}