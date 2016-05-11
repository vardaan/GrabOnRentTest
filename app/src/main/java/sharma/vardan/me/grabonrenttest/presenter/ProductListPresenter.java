package sharma.vardan.me.grabonrenttest.presenter;

import android.support.annotation.NonNull;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import sharma.vardan.me.grabonrenttest.data.model.Product;
import sharma.vardan.me.grabonrenttest.domain.GetAllProductsUseCase;
import sharma.vardan.me.grabonrenttest.util.Preconditions;
import timber.log.Timber;

final public class ProductListPresenter implements ProductListContract.Presenter {
  private final GetAllProductsUseCase useCase;
  private boolean isLoadingData;
  private boolean reachedEndOfList;

  private CompositeSubscription subscriptions;
  private final ProductListContract.Screen screen;
  private int page;

  public ProductListPresenter(@NonNull ProductListContract.Screen screen,
      @NonNull GetAllProductsUseCase useCase) {
    this.screen = Preconditions.checkNotNull(screen);
    this.subscriptions = new CompositeSubscription();
    this.useCase = useCase;
    this.reachedEndOfList = false;
  }

  public void getProducts() {
    hideViews();
    //show correct type of loading based on whether or not we have products
    showApropriateLoadingType();

    isLoadingData = true;
    subscriptions.add(useCase.execute(page)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Action1<List<Product>>() {
          @Override public void call(List<Product> products) {
            isLoadingData = false;
            screen.hideLoading();
            screen.hideBottomLoading();

            screen.showProducts(products);
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            Timber.e("Error getting products -> %s", throwable.getMessage());
            isLoadingData = false;
            ProductListPresenter.this.handleError();
          }
        }));
  }

  @Override public void onRetryClicked() {
    isLoadingData = true;
    getProducts();
  }

  @Override public void onDestroy() {
    if (subscriptions != null && !subscriptions.isUnsubscribed()) {
      subscriptions.unsubscribe();
    }
  }

  private void handleError() {
    //todo show some kind
    //if it has products then show some kind of retry view
    hideViews();
    revertNextPageRequest();
    showApproriateRetryView();
  }

  private void revertNextPageRequest() {
    if (page > 2) page--;
  }

  private void showApproriateRetryView() {
    if (!hasProducts()) {
      screen.showRetry();
    } else {
      screen.showBottomRetry();
    }
  }

  private boolean hasProducts() {
    return page != 1;
  }

  private void showApropriateLoadingType() {
    if (!hasProducts()) {
      screen.showLoading();
    } else {
      screen.showBottomLoading();
    }
  }

  private void hideViews() {
    //hide all previous views
    screen.hideEmptyView();
    screen.hideRetry();
    screen.hideLoading();
    screen.hideBottomLoading();
    screen.hideRetry();
  }

  @Override public void requestProducts() {
    if (isLoadingData || reachedEndOfList) {
      Timber.i("reached end or loading data");
      return;
    }
    isLoadingData = true;
    //update the request
    page++;
    getProducts();
  }
}
