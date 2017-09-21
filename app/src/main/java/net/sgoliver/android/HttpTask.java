package net.sgoliver.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.widget.Toast;

public abstract class HttpTask<Params, Result> 
extends AsyncTask<Params, Integer, Result> {

	protected ProgressDialog progressDialog = null;
	protected AsynTaskActivity activity = null;
	protected String url;
	protected String action;
	protected Boolean synchronize;

	public HttpTask(AsynTaskActivity activity, String url, String action, Boolean ... params) {
		super();
		this.activity = activity;
		this.url = url;
		this.action = action;
		this.synchronize = (params.length > 0)?params[0]:false;
	}
	
    protected abstract Result doInBackground(Params... params);

    protected abstract void onPostExecute(Result result);

	@Override
    protected void onPreExecute() {
		progressDialog = new ProgressDialog(activity);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(activity.getString(R.string.procesando));
		progressDialog.setCancelable(true);
		progressDialog.setMax(100);
		progressDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				HttpTask.this.cancel(true);
			}
		});
		
		progressDialog.setProgress(0);
		progressDialog.show();
    }

	@Override
    protected void onProgressUpdate(Integer... values) {
    	progressDialog.setProgress(values[0].intValue());
    }

	@Override
	protected void onCancelled() {
		progressDialog.hide();
		Toast.makeText(activity, activity.getString(R.string.cancelado), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.setAction(action);
		activity.onAsynTaskResult(Activity.RESULT_CANCELED, intent);
	}
}
