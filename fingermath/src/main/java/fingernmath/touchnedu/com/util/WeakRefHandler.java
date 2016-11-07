package fingernmath.touchnedu.com.util;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;
import fingernmath.touchnedu.com.interfacemodule.IOnHandlerMessage;

public class WeakRefHandler extends Handler {
  private final WeakReference<IOnHandlerMessage> mHandlerActivity;

  public WeakRefHandler(IOnHandlerMessage activity) {
    mHandlerActivity = new WeakReference<>(activity);
  }

  @Override
  public void dispatchMessage(Message msg) {
    super.dispatchMessage(msg);
    IOnHandlerMessage activity = mHandlerActivity.get();
    if(activity == null)
      return;
    activity.dispatchMessage(msg);


  }
}
