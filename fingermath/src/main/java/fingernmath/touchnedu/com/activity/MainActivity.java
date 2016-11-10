package fingernmath.touchnedu.com.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.myscript.atk.math.widget.MathWidgetApi;
import com.myscript.certificate.MyCertificate;

import fingernmath.touchnedu.com.BuildConfig;
import fingernmath.touchnedu.com.R;


public class MainActivity extends Activity implements MathWidgetApi.OnConfigureListener,
                                                      MathWidgetApi.OnRecognitionListener {
  private static final String TAG = "MathWidget";
  private Context mContext;
  private MathWidgetApi mWidget;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_main);

    mContext = this;

    widgetInit();
    viewInit();


  }

  private void viewInit() {
    Button menuBtn = (Button)findViewById(R.id.btn_menu_quiz),
           soundBtn = (Button)findViewById(R.id.btn_sound);
    menuBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//        Toast.makeText(mContext, "메뉴 버튼", Toast.LENGTH_SHORT).show();
        mWidget.clear(true);
      }
    });
    soundBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(mContext, "소리 버튼", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void widgetInit() {
    Log.i(TAG, "widgetInit 진입");
    mWidget = (MathWidgetApi)findViewById(R.id.mathWidget);
    mWidget.setOnConfigureListener(this);
    mWidget.setOnRecognitionListener(this);
    mWidget.addSearchDir("zip://" + getPackageCodePath() + "!/assets/conf");
    mWidget.configure("math", "standard");

    Log.i(TAG, "Certificate : " + mWidget.registerCertificate(MyCertificate.getBytes()));
    if (!mWidget.registerCertificate(MyCertificate.getBytes())) {
      AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
      dlgAlert.setMessage("Please use a valid certificate.");
      dlgAlert.setTitle("Invalid certificate");
      dlgAlert.setCancelable(false);
      dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          //dismiss the dialog
        }
      });
      dlgAlert.create().show();
      return;
    }

    mWidget.clearSearchPath();
    mWidget.addSearchDir("zip://" + getPackageCodePath() + "!/assets/conf/");
    mWidget.configure("math", "standard");

    // Configure clear button
    final View clearButton = findViewById(R.id.btn_menu_quiz);
    if (clearButton != null) {
      clearButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
          mWidget.clear(true);
        }
      });
    }
  }

  @Override
  public void onConfigureBegin(MathWidgetApi widget) {

  }

  @Override
  public void onConfigureEnd(MathWidgetApi widget, boolean success) {
    if(!success) {
      Toast.makeText(getApplicationContext(), widget.getErrorString(), Toast.LENGTH_LONG).show();
      Log.e(TAG, "Unable to configure the Math Widget : " + widget.getErrorString());
      return;
    }
    Toast.makeText(getApplicationContext(), "Math Widget Configured", Toast.LENGTH_SHORT).show();
    if (BuildConfig.DEBUG)
      Log.d(TAG, "Math Widget configured!");
  }

  @Override
  public void onRecognitionBegin(MathWidgetApi widget) {
    Log.d(TAG, "Recognition Begun..");
  }

  @Override
  public void onRecognitionEnd(MathWidgetApi widgetApi) {
    Toast.makeText(getApplicationContext(), "Recognition update", Toast.LENGTH_SHORT).show();
    if (BuildConfig.DEBUG) {
      Log.d(TAG, "Math Widget recognition : " + widgetApi.getResultAsText());
    }
  }

  @Override
  protected void onDestroy() {
    mWidget.setOnRecognitionListener(null);
    mWidget.setOnConfigureListener(null);
    mWidget.release();
    super.onDestroy();
  }
}
