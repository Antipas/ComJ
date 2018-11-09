package j.com.jetpack;

import android.content.Context;

import j.com.componentserivce.Constants;
import j.com.componentserivce.NetworkManager;
import j.com.jetpack.api.ArticleApi;

public class JetpackNetworkManager extends NetworkManager {

    private  volatile static JetpackNetworkManager INSTANCE = null;

    Context context;


    private JetpackNetworkManager(Context context){
        this.context = context;
    }

    public static JetpackNetworkManager getInstance(Context context){
        if (INSTANCE == null){
            synchronized (JetpackNetworkManager.class){
                if (INSTANCE == null) {
                    INSTANCE = new JetpackNetworkManager(context);
                }
            }
        }

        return INSTANCE;
    }


    public ArticleApi getArticleApi(){

        return getBuilder(Constants.API_GANKIO, context)
                .build().create(ArticleApi.class);
    }
}
