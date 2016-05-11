package sharma.vardan.me.grabonrenttest.data.remote;

import android.support.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
  private static final String baseUrl = "http://www.thinkrent.in/";
  private static GrabOnRentService grabOnRentService;

  private ApiClient() {
    throw new AssertionError("No instances");
  }

  public static GrabOnRentService getInstance() {
    if (grabOnRentService == null) {
      final OkHttpClient okHttpClient = makeOkHttpClient();

      final Retrofit client = makeRetrofit(okHttpClient);

      grabOnRentService = client.create(GrabOnRentService.class);
    }
    return grabOnRentService;
  }

  @NonNull private static Retrofit makeRetrofit(OkHttpClient okHttpClient) {
    return new Retrofit.Builder().baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }

  @NonNull private static OkHttpClient makeOkHttpClient() {
    OkHttpClient okHttpClient = new OkHttpClient();

    final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    //add the profile time interceptor

    //add interceptor for the automatic logging of request and response from api
    //okHttpClient.interceptors().add(httpLoggingInterceptor);//not working

    //add the interceptor for logging the curl commands
    return okHttpClient;
  }
}