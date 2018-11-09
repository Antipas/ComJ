package j.com.module.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.applicationlike.RegisterCompManual;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;

import j.com.componentserivce.jetpack.JetpackService;

@RegisterCompManual
public class ModuleLike implements IApplicationLike {

    UIRouter uiRouter = UIRouter.getInstance();
    Router router = Router.getInstance();
    @Override
    public void onCreate() {
        uiRouter.registerUI("module");
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("module");
    }
}