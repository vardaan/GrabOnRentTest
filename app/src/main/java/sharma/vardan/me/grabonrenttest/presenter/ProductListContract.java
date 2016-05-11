package sharma.vardan.me.grabonrenttest.presenter;

import android.support.annotation.NonNull;
import java.util.List;
import sharma.vardan.me.grabonrenttest.data.model.Product;

public class ProductListContract {

  public interface Screen {
    void showLoading();

    void hideLoading();

    void showBottomLoading();

    void hideBottomLoading();

    void showProducts(@NonNull List<Product> products);

    void showRetry();

    void hideRetry();

    void showNoMoreItemsView();

    void showEmptyView();

    void showBottomRetry();

    void hideEmptyView();
  }

  interface Presenter {
    void onDestroy();

    void getProducts();

    void onRetryClicked();

    void requestProducts();
  }
}
