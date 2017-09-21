package net.sgoliver.android;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClientManager {

	private static final String TAG = HttpClientManager.class.getSimpleName();

	public static final String GET = "net.sgoliver.intent.action.get.client";
	public static final String GETS = "net.sgoliver.intent.action.get.clients";
	public static final String PUT = "net.sgoliver.intent.action.put.client";
	public static final String POST = "net.sgoliver.intent.action.post.client";
	public static final String DELETE = "net.sgoliver.intent.action.delete.client";

	protected static HttpClientManager instance;
	
	public static synchronized HttpClientManager getInstance() {
    	if (instance == null) {
    		instance = new HttpClientManager();
    	}
    	return instance;
    }
	
    public Client getClient(AsynTaskActivity activity, 
    		String url, 
    		String action, 
    		String identifier,
    		Boolean ... params) throws InterruptedException, ExecutionException {
    	HttpTask<String, Client> httpTask = 
    	new HttpTask<String, Client>(activity, url, action, params) {
    		@Override
    		protected Client doInBackground(String... params) {
    			String identifier = params[0];
    			Client client = null;
    			String result = null;
    			try {
    				OkHttpClient httpClient = 
    				OkHttpSSLClientFactory.getInstance().getHttpClient(activity.getResources());
    				Request request = new Request.Builder()
    						.get()
    				        .url(url + "/" + identifier)
    				        .addHeader("accept", "application/json; charset=utf-8")
    				        .build();
    				Response response = httpClient.newCall(request).execute();
    				try {
    					if (response.isSuccessful()) {
    						result = response.body().string();
    						JSONObject object = new JSONObject(result);
    			        	client = Client.valueOf(object);
    					}
    				}
    	        	finally {
    	        		response.close();
    	        	}
    	        }
    	        catch(Exception e) {
    	        	Log.e(TAG,"getClient()", e);
    	        }
    			
    			return client;
            }
    		@Override
    		protected void onPostExecute(Client result) {
    			progressDialog.hide();
    			Toast.makeText(activity, activity.getString(R.string.procesado), Toast.LENGTH_SHORT).show();
    			if (synchronize) return;
    			Intent intent = new Intent();
    			intent.setAction(action);
    			intent.putExtra("result", result);
    			activity.onAsynTaskResult(Activity.RESULT_OK, intent);
    		}
        };
        
        httpTask.execute(identifier);
        
        if (httpTask.synchronize) {
        	return httpTask.get();
        }
        
        return null;
    }

    public Boolean setClient(AsynTaskActivity activity, 
    		String url, 
    		String action, 
    		Client client,
    		Boolean ... params) throws InterruptedException, ExecutionException {
    	HttpTask<Client, Boolean> httpTask = 
    	new HttpTask<Client, Boolean>(activity, url, action, params) {
    		@Override
    		protected Boolean doInBackground(Client... params) {
    			Client client = params[0];
    			String result = null;
    			try {
    				OkHttpClient httpClient = 
    				OkHttpSSLClientFactory.getInstance().getHttpClient(activity.getResources());
    				JSONObject object = client.toJSONObject();
					MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    				RequestBody body = RequestBody.create(mediaType, object.toString());
    				Request request = new Request.Builder()
    						.post(body)
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
    	        	Log.e(TAG,"getClient()", e);
    	        }
    			
    			return TextUtils.equals(result, Constants.OK);
            }
    		@Override
    		protected void onPostExecute(Boolean result) {
    			progressDialog.hide();
    			Toast.makeText(activity, activity.getString(R.string.procesado), Toast.LENGTH_SHORT).show();
    			if (synchronize) return;
    			Intent intent = new Intent();
    			intent.setAction(action);
    			intent.putExtra("result", result);
    			activity.onAsynTaskResult(Activity.RESULT_OK, intent);
    		}
        };
        
        httpTask.execute(client);

        if (httpTask.synchronize) {
        	return httpTask.get();
        }
        
        return null;
    }

    public Boolean uploadDocument(AsynTaskActivity activity, 
    		String url, 
    		String action, 
    		Data data,
    		Boolean ... params) throws InterruptedException, ExecutionException {
    	HttpTask<Data, Boolean> httpTask = 
    	new HttpTask<Data, Boolean>(activity, url, action, params) {
    		@Override
    		protected Boolean doInBackground(Data... params) {
    			Data data = params[0];
    			String result = null;
    			try {
    				OkHttpClient httpClient = 
    				OkHttpSSLClientFactory.getInstance().getHttpClient(activity.getResources());
    				JSONObject client = data.getClient().toJSONObject();
    				MediaType mediaFileType = MediaType.parse("application/octet-stream");
    				RequestBody requestBodyFile = 
    				RequestBody.create(mediaFileType, new File(data.getPath(), data.getName()));
    				RequestBody requestBody = new MultipartBody.Builder()
    						.setType(MultipartBody.FORM)
    						.addFormDataPart("data", client.toString())
    						.addFormDataPart("file", data.getName(), requestBodyFile)
    						.build();
    				Request request = new Request.Builder()
    						.post(requestBody)
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
    	        	Log.e(TAG,"getClient()", e);
    	        }
    			
    			return TextUtils.equals(result, Constants.OK);
            }
    		@Override
    		protected void onPostExecute(Boolean result) {
    			progressDialog.hide();
    			Toast.makeText(activity, activity.getString(R.string.procesado), Toast.LENGTH_SHORT).show();
    			if (synchronize) return;
    			Intent intent = new Intent();
    			intent.setAction(action);
    			intent.putExtra("result", result);
    			activity.onAsynTaskResult(Activity.RESULT_OK, intent);
    		}
        };
        
        httpTask.execute(data);

        if (httpTask.synchronize) {
        	return httpTask.get();
        }
        
        return null;
    }

    public Boolean uploadFile(AsynTaskActivity activity, 
    		String url, 
    		String action, 
    		Data data,
    		Boolean ... params) throws InterruptedException, ExecutionException {
    	HttpTask<Data, Boolean> httpTask = 
    	new HttpTask<Data, Boolean>(activity, url, action, params) {
    		@Override
    		protected Boolean doInBackground(Data... params) {
    			Data data = params[0];
    			String result = null;
    			try {
    				OkHttpClient httpClient = 
    				OkHttpSSLClientFactory.getInstance().getHttpClient(activity.getResources());
    				MediaType mediaFileType = MediaType.parse("application/octet-stream");
    				RequestBody requestBodyFile = 
    				RequestBody.create(mediaFileType, new File(data.getPath(), data.getName()));
    				Request request = new Request.Builder()
    						.put(requestBodyFile)
    				        .url(url + "?name=" + data.getName())
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
    	        	Log.e(TAG,"getClient()", e);
    	        }
    			
    			return TextUtils.equals(result, Constants.OK);
            }
    		@Override
    		protected void onPostExecute(Boolean result) {
    			progressDialog.hide();
    			Toast.makeText(activity, activity.getString(R.string.procesado), Toast.LENGTH_SHORT).show();
    			if (synchronize) return;
    			Intent intent = new Intent();
    			intent.setAction(action);
    			intent.putExtra("result", result);
    			activity.onAsynTaskResult(Activity.RESULT_OK, intent);
    		}
        };
        
        httpTask.execute(data);

        if (httpTask.synchronize) {
        	return httpTask.get();
        }
        
        return null;
    }

    public Boolean downloadFile(AsynTaskActivity activity, 
    		String url, 
    		String action, 
    		Data data,
    		Boolean ... params) throws InterruptedException, ExecutionException {
    	HttpTask<Data, Boolean> httpTask = 
    	new HttpTask<Data, Boolean>(activity, url, action, params) {
    		@Override
    		protected Boolean doInBackground(Data... params) {
    			Data data = params[0];
    			Boolean result = false;
    			try {
    				OkHttpClient httpClient = 
    				OkHttpSSLClientFactory.getInstance().getHttpClient(activity.getResources());
    				Request request = new Request.Builder()
    						.get()
    				        .url(url + "?name=" + data.getName())
    				        .addHeader("accept", "application/octet-stream")
    				        .build();
    				Response response = httpClient.newCall(request).execute();
    				try {
    					if (response.isSuccessful()) {
    		        		InputStream inputStream = response.body().byteStream();
    		        		HttpUtils.writeToFile(inputStream, data.getPath(), data.getName());
    		        		result = true;
    					}
    				}
    	        	finally {
    	        		response.close();
    	        	}
    	        }
    	        catch(Exception e) {
    	        	Log.e(TAG,"getClient()", e);
    	        }
    			
    			return result;
            }
    		@Override
    		protected void onPostExecute(Boolean result) {
    			progressDialog.hide();
    			Toast.makeText(activity, activity.getString(R.string.procesado), Toast.LENGTH_SHORT).show();
    			if (synchronize) return;
    			Intent intent = new Intent();
    			intent.setAction(action);
    			intent.putExtra("result", result);
    			activity.onAsynTaskResult(Activity.RESULT_OK, intent);
    		}
        };
        
        httpTask.execute(data);

        if (httpTask.synchronize) {
        	return httpTask.get();
        }
        
        return null;
    }
}
