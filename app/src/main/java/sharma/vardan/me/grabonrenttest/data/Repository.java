package sharma.vardan.me.grabonrenttest.data;

import android.support.annotation.NonNull;
import java.util.List;
import rx.Observable;
import sharma.vardan.me.grabonrenttest.data.local.LocalDataSource;
import sharma.vardan.me.grabonrenttest.data.model.Product;
import sharma.vardan.me.grabonrenttest.data.remote.RestDataSource;

public class Repository implements DataSource {
  private static Repository sInstance;
  private LocalDataSource localDataSource;
  private RestDataSource restDataSource;

  private Repository(@NonNull LocalDataSource localDataSource,
      @NonNull RestDataSource restDataSource) {
    this.localDataSource = localDataSource;
    this.restDataSource = restDataSource;
  }

  public static Repository getInstance(@NonNull LocalDataSource localDataSource,
      @NonNull RestDataSource restDataSource) {
    if (sInstance == null) {
      sInstance = new Repository(localDataSource, restDataSource);
    }
    return sInstance;
  }

  //for now not implementing the local datasource
  @Override public Observable<List<Product>> getProducts(int page) {
    return restDataSource.getProducts(page);
  }
}
