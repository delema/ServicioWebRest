package net.sgoliver.android;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpAuthenticationManager {

	private static final String TAG = HttpAuthenticationManager.class.getSimpleName();
	
	protected static HttpAuthenticationManager instance;
	
	public static synchronized HttpAuthenticationManager getInstance() {
    	if (instance == null) {
    		instance = new HttpAuthenticationManager();
    	}
    	return instance;
    }
	
    public String connect(AsynTaskActivity activity,
    		String url,
    		Authentication authentication) throws InterruptedException, ExecutionException {
    	HttpTask<Authentication, String> httpTask = 
    	new HttpTask<Authentication, String>(activity, url, null, true) {
    		@Override
    		protected String doInBackground(Authentication... params) {
    			Authentication authentication = params[0];
    			String token = null;
    			try {
    				OkHttpClient httpClient = 
    				OkHttpSSLClientFactory.getInstance().getHttpClient(activity.getResources());
    				JSONObject object = authentication.toJSONObject();
    				MediaType mediaType = MediaType.parse("application/json");
    				RequestBody body = RequestBody.create(mediaType, object.toString());
    				Request request = new Request.Builder()
    						.post(body)
    				        .url(url)
    				        .build();
    				Response response = httpClient.newCall(request).execute();
    				try {
    					if (response.isSuccessful()) {
    						token = response.body().string();
    					}
    				}
    	        	finally {
    	        		response.close();
    	        	}
    	        }
    	        catch(Exception e) {
    	        	Log.e(TAG,"connect()", e);
    	        }
    			
    			return token;
            }
    		@Override
    		protected void onPostExecute(String result) {
    			progressDialog.hide();
    			Toast.makeText(activity, activity.getString(R.string.procesado), Toast.LENGTH_SHORT).show();
    		}
        };
        // Respuesta sincrona
        return httpTask.execute(authentication).get();
    }

    public Boolean disconnect(AsynTaskActivity activity,
    		String url,
    		Authentication authentication) throws InterruptedException, ExecutionException {
    	HttpTask<Authentication, Boolean> httpTask = 
    	new HttpTask<Authentication, Boolean>(activity, url, null, true) {
    		@Override
    		protected Boolean doInBackground(Authentication... params) {
    			Authentication authentication = params[0];
    			String result = null;
    			try {
    				OkHttpClient httpClient = 
    				OkHttpSSLClientFactory.getInstance().getHttpClient(activity.getResources());
    				JSONObject object = authentication.toJSONObject();
    				MediaType mediaType = MediaType.parse("application/json");
    				RequestBody body = RequestBody.create(mediaType, object.toString());
    				Request request = new Request.Builder()
    						.delete(body)
    				        .url(url)
    				        .build();
    				Response response = httpClient.newCall(request).execute();
    				try {
    					if (response.isSuccessful()) {
    						result = response.body().string();
    					}
    				}
    	        	finally {
    	        		response.close();
    	        	}
    	        }
    	        catch(Exception e) {
    	        	Log.e(TAG,"disconnect()", e);
    	        }
    			
    			return TextUtils.equals(result, Constants.OK);
            }
    		@Override
    		protected void onPostExecute(Boolean result) {
    			progressDialog.hide();
    			Toast.makeText(activity, activity.getString(R.string.procesado), Toast.LENGTH_SHORT).show();
    		}
        };
        // Respuesta sincrona
        return httpTask.execute(authentication).get();
    }
}
