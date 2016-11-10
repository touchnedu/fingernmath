package fingernmath.touchnedu.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.WindowManager;

import fingernmath.touchnedu.com.R;
import fingernmath.touchnedu.com.interfacemodule.IOnHandlerMessage;
import fingernmath.touchnedu.com.util.Util;
import fingernmath.touchnedu.com.util.WeakRefHandler;

public class SplashActivity extends Activity implements IOnHandlerMessage {
  WeakRefHandler wrHandler = new WeakRefHandler(this);
  private Context mContext;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                         WindowManager.LayoutParams.FLAG_FULLSCREEN);

    mContext = this;

    setContentView(R.layout.activity_splash);
    wrHandler.sendEmptyMessage(0);

  }

  @Override
  public void dispatchMessage(Message msg) {
    switch(msg.what) {
      case 0:
        boolean isConnect = Util.checkNetwork(mContext);
        if(isConnect)
          wrHandler.sendEmptyMessageDelayed(2, 1500);
        else
          wrHandler.sendEmptyMessageDelayed(2, 1500);
//          wrHandler.sendEmptyMessage(1);
        break;

      case 1:
        Util.customOneDialog(mContext);
        break;

      case 2:
        startMainActivity();
        break;
    }
  }

  private void startMainActivity() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
    finish();
  }

}
