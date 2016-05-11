package sharma.vardan.me.grabonrenttest.data.local;

import java.util.List;
import rx.Observable;
import sharma.vardan.me.grabonrenttest.data.DataSource;
import sharma.vardan.me.grabonrenttest.data.model.Product;

/**
 * Created by Vardan sharma on 11/5/16.
 */
public class LocalDataSource implements DataSource {
  @Override public Observable<List<Product>> getProducts(int page) {
    throw new IllegalStateException("Not implemented");
  }
}
