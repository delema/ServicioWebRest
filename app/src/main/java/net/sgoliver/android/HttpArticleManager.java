package net.sgoliver.android;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpArticleManager {

	private static final String TAG = HttpArticleManager.class.getSimpleName();
	
	protected static HttpArticleManager instance;
	
	public static synchronized HttpArticleManager getInstance() {
    	if (instance == null) {
    		instance = new HttpArticleManager();
    	}
    	return instance;
    }
	
    // Llamadas a las funciones lambda AWS 
    
    public Boolean postArticle(AsynTaskActivity activity, 
    		String url, 
    		String action, 
    		Article article,
    		Boolean ... params) throws InterruptedException, ExecutionException {
    	HttpTask<Article, Boolean> httpTask = 
    	new HttpTask<Article, Boolean>(activity, url, action, params) {
    		@Override
    		protected Boolean doInBackground(Article... params) {
    			Article article = params[0];
    			String result = null;
    			try {
    				OkHttpClient httpClient = 
    				OkHttpSSLClientFactory.getInstance().getHttpClient(activity.getResources());
    				JSONObject object = article.toJSONObject();
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
        
        httpTask.execute(article);

        if (httpTask.synchronize) {
        	return httpTask.get();
        }
        
        return null;
    }

    public Boolean putArticle(AsynTaskActivity activity, 
    		String url, 
    		String action, 
    		Article article,
    		Boolean ... params) throws InterruptedException, ExecutionException {
    	HttpTask<Article, Boolean> httpTask = 
    	new HttpTask<Article, Boolean>(activity, url, action, params) {
    		@Override
    		protected Boolean doInBackground(Article... params) {
    			Article article = params[0];
    			String result = null;
    			try {
    				OkHttpClient httpClient = 
    				OkHttpSSLClientFactory.getInstance().getHttpClient(activity.getResources());
    				JSONObject object = article.toJSONObject();
    				MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    				RequestBody body = RequestBody.create(mediaType, object.toString());
    				Request request = new Request.Builder()
    						.put(body)
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
        
        httpTask.execute(article);

        if (httpTask.synchronize) {
        	return httpTask.get();
        }
        
        return null;
    }

    public Article getArticle(AsynTaskActivity activity, 
    		String url, 
    		String action, 
    		String identifier,
    		Boolean ... params) throws InterruptedException, ExecutionException {
    	HttpTask<String, Article> httpTask = 
    	new HttpTask<String, Article>(activity, url, action, params) {
    		@Override
    		protected Article doInBackground(String... params) {
    			String identifier = params[0];
    			Article article = null;
    			String result = null;
    			try {
    				OkHttpClient httpClient = 
    				OkHttpSSLClientFactory.getInstance().getHttpClient(activity.getResources());
    				Request request = new Request.Builder()
    						.get()
    				        .url(url + "?id=" + identifier)
    				        .addHeader("accept", "application/json; charset=utf-8")
    				        .build();
    				Response response = httpClient.newCall(request).execute();
    				try {
    					if (response.isSuccessful()) {
    						result = response.body().string();
    						JSONObject object = new JSONObject(result);
    			        	article = Article.valueOf(object);
    					}
    				}
    	        	finally {
    	        		response.close();
    	        	}
    	        }
    	        catch(Exception e) {
    	        	Log.e(TAG,"getClient()", e);
    	        }
    			
    			return article;
            }
    		@Override
    		protected void onPostExecute(Article result) {
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
}
