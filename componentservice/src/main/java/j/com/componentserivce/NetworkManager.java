package j.com.componentserivce;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import j.com.componentserivce.util.CheckNetwork;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class NetworkManager {


    public Retrofit.Builder getBuilder(String apiUrl, Context context) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkClient(context));
        builder.baseUrl(apiUrl);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return builder;
    }

    protected OkHttpClient getOkClient(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        // 50 MiB
        int cacheSize = 50 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.readTimeout(30, TimeUnit.SECONDS);
        okBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okBuilder.writeTimeout(30, TimeUnit.SECONDS);

        return okBuilder.build();
    }

    private class AddCacheInterceptor implements Interceptor {
        private Context context;

        AddCacheInterceptor(Context context) {
            super();
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();
            Request request = chain.request();
            if (!CheckNetwork.isNetworkConnected(context)) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (CheckNetwork.isNetworkConnected(context)) {
                // read from cache
                int maxAge = 0;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                // tolerate 4-weeks stale
                int maxStale = 60 * 60 * 24 * 28;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }
}
