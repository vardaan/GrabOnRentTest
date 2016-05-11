package sharma.vardan.me.grabonrenttest.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import sharma.vardan.me.grabonrenttest.GrabOnRentApp;
import sharma.vardan.me.grabonrenttest.R;
import sharma.vardan.me.grabonrenttest.data.model.Product;
import timber.log.Timber;

class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int TYPE_LOADING_MORE = 0x01;
  private static final int TYPE_PRODUCT = 0x02;

  private boolean isLoadingData = false;
  private ArrayList<Product> products;

  public ProductAdapter() {
    products = new ArrayList<>();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    if (viewType == TYPE_LOADING_MORE) {
      return new LoadingMoreHolder(
          layoutInflater.inflate(R.layout.infinite_loading, parent, false));
    }
    if (viewType == TYPE_PRODUCT) {
      return new ProductVH(layoutInflater.inflate(R.layout.item_product, parent, false));
    }

    throw new IllegalStateException("Invalid view type");
  }

  @Override public int getItemCount() {
    return products.size() + 1;
  }

  @Override public int getItemViewType(int position) {
    return (getItemCount() - 1 == position) ? TYPE_LOADING_MORE : TYPE_PRODUCT;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
    if (vh instanceof ProductVH) {
      bindProductData((ProductVH) vh, position);
    } else {
      bindLoadingViewHolder((LoadingMoreHolder) vh);
    }
  }

  private void bindProductData(ProductVH vh, int position) {
    final Product product = products.get(position);
    Picasso.with(GrabOnRentApp.getContext()).load(product.getImage()).into(vh.imgProduct);
    vh.txtProductName.setText(product.getText());
  }

  private void bindLoadingViewHolder(LoadingMoreHolder holder) {
    // only show the infinite load progress spinner if there are already items in the
    // grid i.e. it's not the first item & data is being loaded
    Timber.i("load more view binding");
    holder.progress.setVisibility(
        (holder.getAdapterPosition() > 0 && isLoadingData()) ? View.VISIBLE : View.INVISIBLE);
  }

  public boolean isLoadingData() {
    return isLoadingData;
  }

  public int getItemColumnSpan(int position) {
    switch (getItemViewType(position)) {
      case TYPE_LOADING_MORE:
        return 2;//todo remove this hardcoding
      default:
        return 1;//todo remove this hardcoding
    }
  }

  public void setIsLoadingData(boolean showLoading) {
    this.isLoadingData = showLoading;
    notifyItemRangeChanged(getItemCount() - 1,
        getItemCount());// notifyItemChanged(getItemCount()) not working :(
  }

  public void addProductsToList(List<Product> products) {
    this.products.addAll(products);
    notifyDataSetChanged();
  }

  class ProductVH extends RecyclerView.ViewHolder {
    @Bind(R.id.img_product) ImageView imgProduct;
    @Bind(R.id.txt_product_name) TextView txtProductName;

    public ProductVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  class LoadingMoreHolder extends RecyclerView.ViewHolder {
    final ProgressBar progress;

    public LoadingMoreHolder(View itemView) {
      super(itemView);
      progress = (ProgressBar) itemView;
    }
  }
}