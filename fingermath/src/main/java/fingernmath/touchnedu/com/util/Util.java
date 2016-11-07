package fingernmath.touchnedu.com.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by HONG-QC on 2016-11-07.
 */

public class Util {
  /* 네트워크 체크 */
  public static boolean checkNetwork(Context context) {
    boolean result;
    ConnectivityManager cm =
                      (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

    /**
     * getNetWorkInfo() is deprecated.
     * -> getActiveNetworkInfo()
     * NetworkInfo networkInfo = cm.getActiveNetworkInfo();
     * networkInfo.getType()로 MOBILE, WIFI, WIMAX 등 연결된 네트워크의 타입을 확인할 수 있다.
     * ConnectivityManager.TYPE_MOBILE ...
     */

    if(cm.getActiveNetworkInfo() != null)
      result = true;
    else
      result = false;

    return result;
  }

  public static void customOneDialog(Context context) {

  }

}
