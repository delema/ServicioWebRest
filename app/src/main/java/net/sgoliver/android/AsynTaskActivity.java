package net.sgoliver.android;

import android.app.Activity;
import android.content.Intent;

public abstract class AsynTaskActivity extends Activity {
    public abstract void onAsynTaskResult(int resultCode, Intent data);
}
