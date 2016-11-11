package fingernmath.touchnedu.com.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import fingernmath.touchnedu.com.R;
import fingernmath.touchnedu.com.module.MainModule;
import fingernmath.touchnedu.com.module.MyScriptModule;
import fingernmath.touchnedu.com.module.WebViewMain;

public class MainActivity extends Activity {
  private Context mContext;
  private MyScriptModule msModule;
  private WebViewMain wvMain;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_main);

    mContext = this;

    /** MyScriptWidget Setting */
    msModule = new MyScriptModule(mContext);
    msModule.initMathWidget();

    /** WebView Setting */
    wvMain = new WebViewMain(this);
    wvMain.initWebView();
    wvMain.initPage();

    /** Page Setting */
    new MainModule().initMainActivity(mContext);

  }

  @Override
  protected void onDestroy() {
    msModule.clearWidgetListener();
    super.onDestroy();
  }
}
