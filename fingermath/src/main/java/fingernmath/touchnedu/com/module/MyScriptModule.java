package fingernmath.touchnedu.com.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.myscript.atk.math.widget.MathWidgetApi;
import com.myscript.certificate.MyCertificate;

import java.util.Timer;
import java.util.TimerTask;

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
  private Timer aniTimer;
  private TimerTask aniTimerTask;
  private Activity mActivity;
  private int frameCount = 0;

  public MyScriptModule(Context mContext) {
    this.mContext = mContext;
  }

  public void initMathWidget() {
    mActivity = (Activity)mContext;
    mWidget = (MathWidgetApi)mActivity.findViewById(R.id.mathWidget);
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

    frameCount = 0;
    if(!widget.getResultAsText().equals("")) {
      ((View)mWidget).setVisibility(View.INVISIBLE);
      genAniTimerTask();
      doTimerTask();
    }

  }

  private void genAniTimerTask() {
    if(aniTimerTask == null) {
      aniTimerTask = new TimerTask() {
        @Override
        public void run() {
          mActivity.runOnUiThread(genAnswer);
        }
      };
    }
  }

  private void doTimerTask() {
    if(aniTimer == null) {
      aniTimer = new Timer();
      aniTimer.scheduleAtFixedRate(aniTimerTask, 0, 50);
    }
  }

  private Runnable genAnswer = new Runnable() {
    Bitmap answerBitmap;
    Bitmap backBitmap;
    Canvas canvas;
    int bitmapWidth;
    int bitmapHeight;


    @Override
    public void run() {
      frameCount++;

      /** 12프레임까지는 이미지를 생성, 12프레임에 이미지가 모두 생성되면 정/오답 체크. */
      if(frameCount > 12) {
        closeTimerRef();
        Log.i("MyScriptModule", "test : " + mWidget.getResultAsText());
      } else {
        answerBitmap = mWidget.getResultAsImage();
        bitmapWidth = answerBitmap.getWidth();
        bitmapHeight = answerBitmap.getHeight();
        answerBitmap = Bitmap.createBitmap(answerBitmap, 0, 0, bitmapWidth, bitmapHeight);
        backBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ALPHA_8);
        canvas = new Canvas(backBitmap);
        canvas.drawBitmap(answerBitmap, bitmapWidth, bitmapHeight, null);

        if(frameCount == 13) {

        }
      }

    }
  };

  private void closeTimerRef() {
    ((View)mWidget).setVisibility(View.VISIBLE);
    if(aniTimerTask != null) {
      aniTimerTask.cancel(); // Timer 큐에서 task 삭제
      aniTimerTask = null;
    }
    if(aniTimer != null) {
      aniTimer.cancel(); // 스케쥴 task, 타이머 취소
      aniTimer.purge(); // task 큐의 모든 task 제거
      aniTimer = null;
    }
  }


}
