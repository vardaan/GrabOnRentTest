package sharma.vardan.me.grabonrenttest.domain;

import java.util.List;
import rx.Observable;
import sharma.vardan.me.grabonrenttest.data.DataSource;
import sharma.vardan.me.grabonrenttest.data.model.Product;

public class GetAllProductsUseCase {

  private DataSource dataSource;

  public GetAllProductsUseCase(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public Observable<List<Product>> execute(int page) {
    return dataSource.getProducts(page);
  }
}
