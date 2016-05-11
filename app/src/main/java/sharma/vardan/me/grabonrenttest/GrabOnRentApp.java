package sharma.vardan.me.grabonrenttest;

import android.app.Application;
import android.content.Context;
import timber.log.Timber;

/**
 * Created by Vardan sharma on 11/5/16.
 */
public class GrabOnRentApp extends Application {
  private static Application application;

  @Override public void onCreate() {
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
    application = this;
  }
  public static Context getContext(){
    return application;
  }
}
