package sharma.vardan.me.grabonrenttest.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import sharma.vardan.me.grabonrenttest.R;
import timber.log.Timber;

import static sharma.vardan.me.grabonrenttest.GrabOnRentApp.getContext;

public class CommonUtil {
  private CommonUtil() {
  }

  public static @Nullable String getAndroidId() {
    Context context = getContext();
    String androidID;
    androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    return androidID;
  }

  public static void toast(@NonNull String msg) {
    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
  }

  public static void longToast(@NonNull String msg) {
    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
  }

  public static void toast(@StringRes int msg) {
    Toast.makeText(getContext(), getContext().getText(msg), Toast.LENGTH_SHORT).show();
  }

  public static void longToast(@StringRes int msg) {
    Toast.makeText(getContext(), getContext().getText(msg), Toast.LENGTH_LONG).show();
  }

  /**
   * Uses static final constants to detect if the device's platform version is Honeycomb or
   * later.
   */
  public static boolean aboveHoneyComb() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  /**
   * Uses static final constants to detect if the device's platform version is Honeycomb or
   * later.
   */
  public static boolean aboveIcs() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }

  /**
   * Uses static final constants to detect if the device's platform version is lolipop or
   * later.
   */
  public static boolean aboveLolipop() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  /**
   * Provides the package name for the application
   * we can use this method instead of hard coding it
   */

  public static String getPackageName() {
    return getContext().getPackageName();
  }

  /**
   * Converts DP into pixels
   *
   * @param dp the size of pixels in dp
   * @return returns the pixels value of the input
   */
  public static int dpToPixels(int dp) {
    Resources r = getContext().getResources();
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
  }

  public static void hideKeyboard(Activity activity) {
    if (activity.getCurrentFocus() == null) {
      Timber.e("get Current Focus is null");
      return;
    }
    InputMethodManager inputMethodManager =
        (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
  }

  public static Drawable getDrawable(int id) {
    final int version = Build.VERSION.SDK_INT;
    return (version >= Build.VERSION_CODES.LOLLIPOP) ? ContextCompat.getDrawable(getContext(), id)
        : getContext().getResources().getDrawable(id);
  }

  public static int getScreenWidth(Activity activity) {
    DisplayMetrics metrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    return metrics.widthPixels;
  }

  public static int getScreenHeight(Activity activity) {
    DisplayMetrics metrics = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    return metrics.heightPixels;
  }

  public static void showKeyboard(Context context, View view) {
    InputMethodManager inputMethodManager =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(),
        InputMethodManager.SHOW_FORCED, 0);
  }

  public static Snackbar showErrorSnackBar(View view, @StringRes int message) {
    Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
    ViewGroup group = (ViewGroup) snackbar.getView();
    group.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.snackbar_error));
    snackbar.show();
    return snackbar;
  }
}
