package fingernmath.touchnedu.com.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.myscript.atk.core.CaptureInfo;
import com.myscript.atk.math.widget.MathWidgetApi;
import com.myscript.certificate.MyCertificate;

import java.util.Timer;
import java.util.TimerTask;

import fingernmath.touchnedu.com.R;

/**
 * Created by HONG-QC on 2016-11-10.
 */

public class MyScriptModule implements MathWidgetApi.OnConfigureListener,
                                       MathWidgetApi.OnRecognitionListener,
                                       MathWidgetApi.OnPenListener {
  private static final String TAG = "MathWidget";
  private Context mContext;
  private MathWidgetApi mWidget;
  private Timer aniTimer;
  private TimerTask aniTimerTask;
  private Activity mActivity;
  private ImageView answerImgView;
  private int frameCount = 0;
  private int writePosX, writePosY;

  public MyScriptModule(Context mContext) {
    this.mContext = mContext;
  }

  public void initMathWidget() {
    mActivity = (Activity)mContext;
    mWidget = (MathWidgetApi)mActivity.findViewById(R.id.mathWidget);
    mWidget.setOnConfigureListener(this);
    mWidget.setOnRecognitionListener(this);
    mWidget.setOnPenListener(this);
    mWidget.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "STIXGeneral-Italic.ttf"));
    mWidget.setBeautificationOption(MathWidgetApi.RecognitionBeautification.BeautifyFontify);
    mWidget.setPaddingRatio(1, 1, 1, 1);
    mWidget.setBaselineColor(View.INVISIBLE);
    mWidget.setBackgroundColor(View.INVISIBLE);
    answerImgView = (ImageView)mActivity.findViewById(R.id.get_img_area);

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
  }

  @Override
  public void onRecognitionBegin(MathWidgetApi widget) {

  }

  @Override
  public void onRecognitionEnd(MathWidgetApi widget) {
    frameCount = 0;
    if(!widget.getResultAsText().equals("")) {
//      ((View)mWidget).setVisibility(View.INVISIBLE);
      Bitmap b = mWidget.getResultAsImage();
      answerImgView.setImageBitmap(b);
//      genAniTimerTask();
//      doTimerTask();
    }

  }

  @Override
  public void onPenDown(MathWidgetApi widget, CaptureInfo point) {
    writePosX = (int)point.getX();
    writePosY = (int)point.getY();
  }

  @Override
  public void onPenUp(MathWidgetApi widget, CaptureInfo point) {
  }

  @Override
  public void onPenMove(MathWidgetApi widget, CaptureInfo point) {
  }

  @Override
  public void onPenAbort(MathWidgetApi widget) { }

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
    @Override
    public void run() {
      Bitmap answerBitmap;
      int bitmapWidth;
      int bitmapHeight;
      frameCount++;

      /** 12프레임까지는 이미지를 생성, 12프레임에 이미지가 모두 생성되면 정/오답 체크. */
      if(frameCount > 12) {
        closeTimerRef();

      } else {
        answerBitmap = mWidget.getResultAsImage();

        if(answerBitmap != null) {
          bitmapWidth = answerBitmap.getWidth();
          bitmapHeight = answerBitmap.getHeight();

          answerBitmap = Bitmap.createBitmap(answerBitmap, 0, 0, bitmapWidth, bitmapHeight);
          answerImgView.setImageBitmap(answerBitmap);
          answerImgView.setX(writePosX + 2);
          answerImgView.setY(writePosY);
        }

        if(frameCount == 13) {

        }
      }

    }
  };

  private void closeTimerRef() {
    if(aniTimerTask != null) {
      aniTimerTask.cancel(); // Timer 큐에서 task 삭제
      aniTimerTask = null;
    }
    if(aniTimer != null) {
      aniTimer.cancel(); // 스케쥴 task, 타이머 취소
      aniTimer.purge(); // task 큐의 모든 task 제거
      aniTimer = null;
    }
//    ((View)mWidget).setVisibility(View.VISIBLE);
    mWidget.clear(true);
  }


}
