package de.cidaas.sdk.android.cidaasverification.data.service;

import android.content.Context;
import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import de.cidaas.sdk.android.cidaasverification.BuildConfig;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.helper.general.DBHelper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CidaasSDK_V2_Service {
    private static Context mcontext;


    public void setContext(Context context) {
        mcontext = context;
    }

    public ICidaasSDK_V2_Services getInstance() {

        String baseurl = CidaasHelper.baseurl;

        if (baseurl == null || baseurl.equals("")) {
            baseurl = "https://www.google.com";
        }


        ICidaasSDK_V2_Services iCidaasSDK_v2_services = null;
        OkHttpClient okHttpClient = null;

        final String HEADER_USER_AGENT = "User-Agent";

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(40, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request requestWithUserAgent = originalRequest.newBuilder()
                                .header(HEADER_USER_AGENT, createCustomUserAgent(originalRequest))

                                .build();
                        for (int i = 0; i < requestWithUserAgent.headers().size(); i++) {
                            //  Timber.d("User-Agent : "+String.format("%s: %s", requestWithUserAgent.headers().name(i), requestWithUserAgent.headers().value(i)));
                            DBHelper.getShared().setUserAgent("User-Agent : " + String.format("%s: %s", requestWithUserAgent.headers().name(i), requestWithUserAgent.headers().value(i)));
                        }

                        return chain.proceed(requestWithUserAgent);
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                // .baseUrl(DBHelper.getShared().getLoginProperties().get("DomainURL"))
                .baseUrl(baseurl)//done Get Base URL
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
        iCidaasSDK_v2_services = retrofit.create(ICidaasSDK_V2_Services.class);
        return iCidaasSDK_v2_services;
    }

    private String createCustomUserAgent(Request originalRequest) {
        // App name can be also retrieved programmatically, but no need to do it for this sample needs
        String ua = "Cidaas-" + CidaasHelper.APP_NAME;
        String baseUa = System.getProperty("http.agent");
        if (baseUa != null) {
            ua = ua + "/" + CidaasHelper.APP_VERSION + "_" + BuildConfig.VERSION_NAME +" Make:" + Build.BRAND+"_"+Build.DEVICE+" Model:" + Build.MODEL+ " " + baseUa;
        }
        return ua;
    }
}
