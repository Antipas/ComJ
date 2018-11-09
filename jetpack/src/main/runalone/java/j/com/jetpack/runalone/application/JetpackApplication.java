package j.com.jetpack.runalone.application;

import android.app.Application;
import android.content.Context;


public class JetpackApplication extends Application{

    static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        ComponentInit.init(this);


    }

    public static Context getContext(){
        return context;
    }
}
