package sharma.vardan.me.grabonrenttest.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class RxRecyclerUtil {

  public static Observable<RecyclerViewScrollEvent> lastItemReached(
      final RecyclerView recyclerView) {
    return RxRecyclerView.scrollEvents(recyclerView)
        .debounce(100, TimeUnit.MILLISECONDS)
        .filter(new Func1<RecyclerViewScrollEvent, Boolean>() {
          @Override public Boolean call(RecyclerViewScrollEvent recyclerViewScrollEvent) {
            return isLastItemDisplaying(recyclerView);
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(AndroidSchedulers.mainThread());
  }

  private static boolean isLastItemDisplaying(RecyclerView recyclerView) {
    RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
    int visibleItemCount = recyclerView.getChildCount();
    int totalItemCount = manager.getItemCount();
    int firstVisibleItem = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
    return (totalItemCount - visibleItemCount) <= (firstVisibleItem + 3);
  }
}
