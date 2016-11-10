package fingernmath.touchnedu.com.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.myscript.atk.math.widget.MathWidgetApi;
import com.myscript.certificate.MyCertificate;

import fingernmath.touchnedu.com.BuildConfig;
import fingernmath.touchnedu.com.R;

/**
 * Created by HONG-QC on 2016-11-10.
 */

public class MyScriptModule implements MathWidgetApi.OnConfigureListener,
                                       MathWidgetApi.OnRecognitionListener {
  private static final String TAG = "MathWidget";
  private Context mContext;
  private MathWidgetApi mWidget;

  public MyScriptModule(Context mContext) {
    this.mContext = mContext;
  }

  public void initMathWidget() {
    Activity activity = (Activity)mContext;
    mWidget = (MathWidgetApi)activity.findViewById(R.id.mathWidget);
    mWidget.setOnConfigureListener(this);
    mWidget.setOnRecognitionListener(this);
    mWidget.setBaselineColor(View.INVISIBLE);
    mWidget.setBackgroundColor(View.INVISIBLE);

    Log.i(TAG, "Certificate : " + mWidget.registerCertificate(MyCertificate.getBytes()));
    if (!mWidget.registerCertificate(MyCertificate.getBytes())) {
      AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(mContext);
      dlgAlert.setMessage("Please use a valid certificate.");
      dlgAlert.setTitle("Invalid certificate");
      dlgAlert.setCancelable(false);
      dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {

        }
      });
      dlgAlert.create().show();
      return;
    }

    mWidget.clearSearchPath();
    mWidget.addSearchDir("zip://" + mContext.getPackageCodePath() + "!/assets/conf/");
    mWidget.configure("math", "standard");

    // Configure clear button
    final View clearButton = ((Activity)mContext).findViewById(R.id.btn_menu_quiz);
    if (clearButton != null) {
      clearButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
          mWidget.clear(true);
        }
      });
    }

  }

  public void clearWidget() {
    mWidget.clear(true);
  }

  public void clearWidgetListener() {
    mWidget.setOnRecognitionListener(null);
    mWidget.setOnConfigureListener(null);
    mWidget.release();
  }

  @Override
  public void onConfigureBegin(MathWidgetApi widget) {

  }

  @Override
  public void onConfigureEnd(MathWidgetApi widget, boolean success) {
    if(!success) {
      Toast.makeText(mContext, widget.getErrorString(), Toast.LENGTH_LONG).show();
      Log.e(TAG, "Unable to configure the Math Widget : " + widget.getErrorString());
      return;
    }
    Toast.makeText(mContext, "Math Widget Configured", Toast.LENGTH_SHORT).show();
    if (BuildConfig.DEBUG)
      Log.d(TAG, "Math Widget configured!");
  }

  @Override
  public void onRecognitionBegin(MathWidgetApi widget) {

  }

  @Override
  public void onRecognitionEnd(MathWidgetApi widget) {
    Toast.makeText(mContext, "Recognition update", Toast.LENGTH_SHORT).show();
    if (BuildConfig.DEBUG) {
      Log.d(TAG, "Math Widget recognition : " + widget.getResultAsText());
    }
  }
}
