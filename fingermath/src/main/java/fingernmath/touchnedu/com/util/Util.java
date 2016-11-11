package fingernmath.touchnedu.com.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.widget.Toast;

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
    /* Builder 객체를 통해 다이얼로그에 표시할 항목(제목, 내용, 버튼 등)을 세팅한다. */
    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
    dialog.setTitle("제목 테스트").setMessage("내용 테스트");
    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int whichButton) {

      }
    }).show();

    /* Builder 객체를 통해 세팅이 완료되면 메시지를 띄울 AlertDialog 객체를 만든다.
     * Builder.create()
     */
//    AlertDialog alertDialog = dialog.create();
//    alertDialog.show();
  }

  public static void showToast(Context context, String msg) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
  }

}
