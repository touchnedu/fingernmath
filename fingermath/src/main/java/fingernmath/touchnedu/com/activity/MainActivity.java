package fingernmath.touchnedu.com.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import fingernmath.touchnedu.com.R;
import android.view.Window;

import com.myscript.atk.math.widget.MathWidgetApi;
import com.myscript.certificate.MyCertificate;


public class MainActivity extends Activity {
  private MathWidgetApi mWidget;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_main);

    mWidget = (MathWidgetApi) findViewById(R.id.mathWidget);
    widgetInit();

  }

  private void widgetInit() {
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


}
