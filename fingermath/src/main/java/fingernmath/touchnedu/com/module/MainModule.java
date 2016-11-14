package fingernmath.touchnedu.com.module;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import fingernmath.touchnedu.com.R;

/**
 * Created by HONG-QC on 2016-11-11.
 */

public class MainModule implements View.OnClickListener {
  private static final String TAG = "MainModule";

  public void initMainActivity(Context context) {
    Activity activity = (Activity)context;
    Button menuBtn      = (Button)activity.findViewById(R.id.btn_menu_quiz),
           soundBtn     = (Button)activity.findViewById(R.id.btn_sound),
           leftQuizBtn  = (Button)activity.findViewById(R.id.btn_left_quiz),
           rightQuizBtn = (Button)activity.findViewById(R.id.btn_right_quiz);

    menuBtn.setOnClickListener(this);
    soundBtn.setOnClickListener(this);
    leftQuizBtn.setOnClickListener(this);
    rightQuizBtn.setOnClickListener(this);

    Log.i(TAG, "MainModule initMainActivity Finished!");

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_menu_quiz:
        showLog("메뉴 버튼");
        break;

      case R.id.btn_sound:
        showLog("소리 버튼");
        break;

      case R.id.btn_left_quiz:
        showLog("이전 문제");
        break;

      case R.id.btn_right_quiz:
        showLog("다음 문제");
        break;

    }
  }

  private void showLog(String msg) {
    Log.d(TAG, msg);
  }
}
