package pl.marceen.investmonitor.api.network.control;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.enterprise.inject.Produces;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * @author Marcin Zaremba
 */
public class HttpClientProducer {

    @Produces
    public OkHttpClient produce() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
    }
}
