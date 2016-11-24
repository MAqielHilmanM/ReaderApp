package gits.helpers;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    ApiInterface mApiInterface;

    public ApiClient(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        mApiInterface = retrofit.create(ApiInterface.class);
    }

    public ApiInterface getApiInteface(){
        return mApiInterface;
    }
}
