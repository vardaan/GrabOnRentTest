package sharma.vardan.me.grabonrenttest.data.remote;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import sharma.vardan.me.grabonrenttest.data.model.Product;

public interface GrabOnRentService {
  @GET("/products/show") Observable<List<Product>> getProducts(@Query("page") String page);
}
