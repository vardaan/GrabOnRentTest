package sharma.vardan.me.grabonrenttest.data;

import java.util.List;
import rx.Observable;
import sharma.vardan.me.grabonrenttest.data.model.Product;

public interface DataSource {
  Observable<List<Product>> getProducts(int page);
}
