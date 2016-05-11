package sharma.vardan.me.grabonrenttest.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import java.util.List;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import sharma.vardan.me.grabonrenttest.R;
import sharma.vardan.me.grabonrenttest.data.DataSource;
import sharma.vardan.me.grabonrenttest.data.Repository;
import sharma.vardan.me.grabonrenttest.data.model.Product;
import sharma.vardan.me.grabonrenttest.data.remote.RestDataSource;
import sharma.vardan.me.grabonrenttest.domain.GetAllProductsUseCase;
import sharma.vardan.me.grabonrenttest.presenter.ProductListContract;
import sharma.vardan.me.grabonrenttest.presenter.ProductListPresenter;
import sharma.vardan.me.grabonrenttest.util.RxRecyclerUtil;

import static sharma.vardan.me.grabonrenttest.util.CommonUtil.dpToPixels;
import static sharma.vardan.me.grabonrenttest.util.CommonUtil.showErrorSnackBar;
import static sharma.vardan.me.grabonrenttest.util.CommonUtil.toast;

public class ProductListActivity extends AppCompatActivity implements ProductListContract.Screen {

  @Bind(R.id.product_list) RecyclerView productList;
  @Bind(R.id.retry_view) View retryView;
  @Bind(R.id.progress) ProgressBar progress;
  private CompositeSubscription subscriptions;
  private ProductListPresenter presenter;
  private ProductAdapter adapter;
  private View empty;

  private final RecyclerView.ItemDecoration SPACING_DECORATOR = new RecyclerView.ItemDecoration() {
    @Override public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
      super.getItemOffsets(outRect, itemPosition, parent);
      // default 4 dp spaces for all
      outRect.left = outRect.right = outRect.top = outRect.bottom = dpToPixels(4);
      // extra spacing for left most item
      if (itemPosition % 2 == 0) {
        outRect.left = dpToPixels(8);
      } else {//extra spacing for right most item
        outRect.right = dpToPixels(8);
      }
      if (itemPosition == 0 || itemPosition == 1) {
        outRect.top = dpToPixels(16);
      }
    }
  };

  public static Intent createIntent(Context context) {
    return new Intent(context, ProductListActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    subscriptions = new CompositeSubscription();
    //for now not implementing local data source so passing null :p
    DataSource dataSource = Repository.getInstance(null, new RestDataSource());
    GetAllProductsUseCase useCase = new GetAllProductsUseCase(dataSource);
    presenter = new ProductListPresenter(this, useCase);

    setContentView(R.layout.activity_product_list);
    ButterKnife.bind(this);

    setUpProductList();

    presenter.requestProducts();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  private void setUpProductList() {
    adapter = new ProductAdapter();
    productList.setAdapter(adapter);

    GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        return adapter.getItemColumnSpan(position);
      }
    });
    productList.setLayoutManager(layoutManager);
    productList.addItemDecoration(SPACING_DECORATOR);
    subscriptions.add(RxRecyclerUtil.lastItemReached(productList)
        .subscribe(new Action1<RecyclerViewScrollEvent>() {
          @Override public void call(RecyclerViewScrollEvent recyclerViewScrollEvent) {
            presenter.requestProducts();
          }
        }));
  }

  @Override public void showLoading() {
    progress.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    progress.setVisibility(View.GONE);
  }

  @Override public void showBottomLoading() {
    adapter.setIsLoadingData(true);
  }

  @Override public void hideBottomLoading() {
    adapter.setIsLoadingData(false);
  }

  @Override public void showProducts(@NonNull List<Product> products) {
    adapter.addProductsToList(products);
  }

  @Override public void showRetry() {
    if (retryView == null) {
      ViewStub viewStub = (ViewStub) findViewById(R.id.retry_view);
      retryView = viewStub.inflate();
      retryView.findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          presenter.onRetryClicked();
        }
      });
    }
    retryView.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    if (retryView != null) retryView.setVisibility(View.GONE);
  }

  @Override public void showNoMoreItemsView() {
    toast(R.string.product_list_no_more_products);
  }

  @Override public void showEmptyView() {
    //add a view stub or something
    if (empty == null) {
      ViewStub viewStub = (ViewStub) findViewById(R.id.stub_no_search_results);
      empty = viewStub.inflate();
    } else {
      empty.setVisibility(View.VISIBLE);
    }
  }

  @Override public void showBottomRetry() {
    showErrorSnackBar(productList, R.string.unable_to_get_data).setAction("retry",
        new View.OnClickListener() {
          @Override public void onClick(View v) {
            presenter.onRetryClicked();
          }
        }).show();
  }

  @Override public void hideEmptyView() {
    //add a hide view stub or something
    if (empty != null) empty.setVisibility(View.GONE);
  }
}
