package fingernmath.touchnedu.com.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import fingernmath.touchnedu.com.R;
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

    viewInit();

  }

  private void viewInit() {
    Button menuBtn  = (Button)findViewById(R.id.btn_menu_quiz),
           soundBtn = (Button)findViewById(R.id.btn_sound),
           leftQuizBtn = (Button)findViewById(R.id.btn_left_quiz),
           rightQuizBtn = (Button)findViewById(R.id.btn_right_quiz);
    menuBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // Menu Button
        msModule.clearWidget();
      }
    });
    soundBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // Sound Button
      }
    });
    leftQuizBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showToast("이전 퀴즈");
      }
    });
    rightQuizBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showToast("다음 퀴즈");
      }
    });

  }

  private void showToast(String msg) {
    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onDestroy() {
    msModule.clearWidgetListener();
    super.onDestroy();
  }
}
