package com.lf.tempcore.tempModule.tempRemotComm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lf.tempcore.tempConfig.TempLoginConfig;
import com.lf.tempcore.tempModule.tempDebuger.Debug;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by longf on 2016/8/26.
 */
public enum TempRemotAPIConnecter {
    INSTANCE;
    private Retrofit retrofit;
//    private OkHttpClient client;

    okhttp3.OkHttpClient.Builder builder  = new OkHttpClient.Builder();
    private TempUserInfoAccessable userInfoAccessable = TempUserInfoAccessableImpl.getInstance();
    private  Interceptor requestInterceptor;
    public void init(TempRemotAPIConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("ERROR_INIT_CONFIGURATION_WITH_NULL");
        }

        requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
//                String cookie = "";
                Request compressedRequest = request.newBuilder()
//                        .header("Cookie", cookie)
//                        .header("Accept-Language", Locale.getDefault().toString())
//                        .header("Accept-Charset", "utf-8")
//                        .header("Connection", "Keep-Alive")
                        .header("Authorization", buildUserIdentify())
                        .build();
                Response response = chain.proceed(compressedRequest);
                return response;
            }
        };
        if (Debug.DEBUG){

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            httpClient.interceptors().add(httpLoggingInterceptor);
            builder.addInterceptor(httpLoggingInterceptor);
        }

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();//使用 gson coverter，统一日期请求格式

        builder.addInterceptor(requestInterceptor);
//        httpClient.interceptors().add(requestInterceptor);
        builder.retryOnConnectionFailure(true);
        builder.readTimeout(40, TimeUnit.SECONDS).
                connectTimeout(15, TimeUnit.SECONDS).build();
//        OkHttpClient okHttpClient = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(configuration.getBaseUri())

                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(builder.build())
                .build();

       /* if (client == null) {
            Log.i("TempRemotAPIConnecter","init");
            client = new OkHttpClient();
           *//* OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws IOException {
                                    Request request = chain.request().newBuilder()
                                            .addHeader("Authorization", buildUserIdentify()).build();
                                    return chain.proceed(request);
                                }
                            }).build();*//*
//            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            client.interceptors().add(httpLoggingInterceptor);
            // 用户验证
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
//                Request newRequest = chain.request().newBuilder().addHeader("Authorization", buildUserIdentify()).build();
                    Request newRequest = chain.request().newBuilder().build();
                    return chain.proceed(newRequest);
                }
            };
           client.interceptors().add(interceptor);

            retrofit = new Retrofit.Builder().baseUrl(configuration.getBaseUri()).
        client(client).addConverterFactory(GsonConverterFactory.create()).
//        addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                            build();*/

//        requestBody = new MultipartBuilder().
//                type(MultipartBuilder.FORM).
//                addPart(
//                    Headers.of("Content-Disposition", "form-data; name=\"mycustomfile.png\""),
//                    RequestBody.create(MEDIA_TYPE_PNG, file))
//            .build();
//        }

    }

 /*   public OkHttpClient getClient() {
        return client;
    }*/


    public Retrofit getRetrofit() {
        return retrofit;
    }

public String dealWithErrorMessage(retrofit2.Response response){
        String message ;
        try {
            JSONObject object = new JSONObject(response.errorBody().string());
            message = object.isNull("Message") ? object.getString("errorMsg") : object.getString("Message");
        } catch (Exception e) {
            e.printStackTrace();
            message="连接服务器异常";
        }

 return message;
}
    /**
     * 构造用户认证的头信息
     * @return
     */
    private  String buildUserIdentify() {
//        final String localUserIdentify = userInfoAccessable.getLocalUserIdentity();
//        final Long userId = userInfoAccessable.getUserId();
//        final String username = userInfoAccessable.getUsername();
//        final String encryptPwd = userInfoAccessable.getEncryptPassword();
//        if (StringUtils.isNotBlank(localUserIdentify)) {
//            return localUserIdentify;
//        } else {
//        Debug.info("构造用户认证的头信息="+username + "|" + userId + "|" + encryptPwd + "|" + localUserIdentify);
//        return username + "|" + userId + "|" + encryptPwd + "|" + localUserIdentify;
//        Debug.info("header="+"BasicAuth "+ TempLoginConfig.sf_getToken());
       return "BasicAuth "+ TempLoginConfig.sf_getToken();
//        }

    }
    public  <T> void  executeRemotAPI(Call<T> call, retrofit2.Callback<T> callback){
        call.enqueue(callback);
    }
    /*public  <API,T> void  executeRemotAPI(Class<API> clazz,Call<T> call, retrofit2.Callback<T> callback){
        call.enqueue(callback);
    }*/
    /**
     * 构造远程接口对象
     *
     * @param clazz 接口的interface class
     * @param <API>
     * @return
     */
    public  <API> API createRemoteApi(Class<API> clazz) {
        return retrofit.create(clazz);
    }
   /* public <T> void executeMethod(Observable<T> observable) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(T o) {
//                        Toast.makeText(context.this, o., Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public  <T> void executeMethod(Observable<T> observable, final OnCallBack<T> callback) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        if (callback != null) {
                            callback.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (callback != null) {
                            callback.onError(e);
                        }
                    }

                    @Override
                    public void onNext(T o) {
                        if (callback != null) {
                            callback.onSucceed(o);
                        }
                    }
                });
    }

    public interface OnCallBack<T> {
        void onSucceed(T data);

        void onCompleted();

        void onError(Throwable e);


    }
    interface OnProgressCallBack<T> extends OnCallBack<T>{
        void onLoading(long total, long current, boolean isUploading);
    }*/
}
