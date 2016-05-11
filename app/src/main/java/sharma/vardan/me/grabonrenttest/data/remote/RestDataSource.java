package sharma.vardan.me.grabonrenttest.data.remote;

import java.util.List;
import rx.Observable;
import sharma.vardan.me.grabonrenttest.data.DataSource;
import sharma.vardan.me.grabonrenttest.data.model.Product;

import static java.lang.String.valueOf;

public class RestDataSource implements DataSource {
  @Override public Observable<List<Product>> getProducts(int page) {
    return ApiClient.getInstance().getProducts(valueOf(page));
  }
}
