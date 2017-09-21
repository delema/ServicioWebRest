package net.sgoliver.android;

import android.content.res.Resources;
import android.util.Log;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class OkHttpSSLClientFactory {

	private static final String TAG = ServicioWebRest.class.getSimpleName();

    protected static final String TLS   = "TLS";
    protected static OkHttpSSLClientFactory instance;
    protected static OkHttpClient httpClient;
    
    protected OkHttpSSLClientFactory() {
		super();
	}

	public static synchronized OkHttpSSLClientFactory getInstance() {
    	if (instance == null) {
    		instance = new OkHttpSSLClientFactory();
    	}
    	return instance;
    }
    
    public OkHttpClient getHttpClient(Resources resource) 
    throws Exception {
    	
    	synchronized(instance) {
    		try {
		    	if (httpClient != null) {
		    		return httpClient;
		    	}
		        KeyStore trustStore = KeyStore.getInstance("BKS");
		        InputStream in = null;
		        
		        try {
		        	in = resource.openRawResource(R.raw.keystore);
		            trustStore.load(in, "changeit".toCharArray());
		        }
		        finally {
		            if (in != null) in.close();
		        }
		        
		        TrustManagerFactory trustManagerFactory = 
		        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		        trustManagerFactory.init(trustStore);
		        
	    	    TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
	    	    X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
		        	   
		        SSLContext sslContext = SSLContext.getInstance(TLS);
		        sslContext.init(null, trustManagers, null);
		        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

		        httpClient = new OkHttpClient.Builder()
		            .sslSocketFactory(sslSocketFactory, trustManager)
		            .hostnameVerifier(new HostnameVerifier() {
				    	@Override
		    	        public boolean verify(String hostname, SSLSession sslSession) {
				    		Log.d(TAG, "HostnameVerifier() : " + hostname);
		    	            return true;
		    	        }
		    	    })
		            .retryOnConnectionFailure(true)
		            .connectTimeout(resource.getInteger(R.integer.http_connection_timeout), TimeUnit.MILLISECONDS)
		            .readTimeout(resource.getInteger(R.integer.http_read_timeout), TimeUnit.MILLISECONDS)
		            .writeTimeout(resource.getInteger(R.integer.http_write_timeout), TimeUnit.MILLISECONDS)
		            .build();
		        
		        return httpClient;
		    } 
		    catch (Exception e) {
		    	Log.e(TAG, "Error: ", e);
		        throw e;
		    }
    	}
    }
}

