package j.com.module.runalone.application;

import android.app.Application;
import android.content.Context;


public class ModuleApplication extends Application{

    static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
