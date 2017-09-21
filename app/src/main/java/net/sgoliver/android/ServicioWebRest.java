package net.sgoliver.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class ServicioWebRest extends AsynTaskActivity {
	
	private static final String TAG = ServicioWebRest.class.getSimpleName();
	
	private static final String CLIENT = "http://10.0.2.2:8080/jaxrs/rest/client";	
	private static final String FILE = "http://10.0.2.2:8080/jaxrs/rest/file";	
	private static final String user = "lema";
	private static final String password = "elboalo";
	private static String path = null;
	private Button btnInsertar;
	private Button btnActualizar;
	private Button btnEliminar;
	private Button btnObtener;
	private Button btnListar;
	private Button btnGetFile;
	private Button btnPutFile;
	private Button btnPostFile;
	
	private EditText txtId;
	private EditText txtNombre;
	private EditText txtTelefono;
	
	private TextView lblResultado;
	private ListView lstClientes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
//    	StrictMode.ThreadPolicy policy = 
//    	new StrictMode.ThreadPolicy.Builder().permitAll().build();
//    	StrictMode.setThreadPolicy(policy);        
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
	   	File storage = getExternalFilesDir(null);
    	path = storage.getAbsolutePath();

        btnInsertar = (Button)findViewById(R.id.btnInsertar);
        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnObtener = (Button)findViewById(R.id.btnObtener);
        btnListar = (Button)findViewById(R.id.btnListar);
        btnGetFile = (Button)findViewById(R.id.btnGetFile);
        btnPutFile = (Button)findViewById(R.id.btnPutFile);
        btnPostFile = (Button)findViewById(R.id.btnPostFile);
        
        txtId = (EditText)findViewById(R.id.txtId);
        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtTelefono = (EditText)findViewById(R.id.txtTelefono);
        
        lblResultado = (TextView)findViewById(R.id.lblResultado);
        lstClientes = (ListView)findViewById(R.id.lstClientes);
        
        btnInsertar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Client client = new Client();
				client.setIdentifier( 1024);
				client.setName("nombre");
				client.setTelephone("teléfono");
				client.setAddress("dirección");

				String url = "https://10.0.2.2:8443/jaxrs/rest/client";
				String action = "net.sgoliver.intent.action.post.client";
				try {
					Boolean result =
					HttpClientManager.getInstance().setClient(ServicioWebRest.this, url, action, client, true);
					Log.d(TAG, "setClient(): " + result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}

			}
		});
        
        btnActualizar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
        
        btnEliminar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
        
        btnObtener.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				//String url = "http://10.0.2.2:8080/jaxrs/rest/client";
				String url = "https://10.0.2.2:8443/jaxrs/rest/client";
				String action = "net.sgoliver.intent.action.get.client";
				try {
					Client client =
					HttpClientManager.getInstance().getClient(ServicioWebRest.this, url, action, "1024", true);
					Log.d(TAG, "getClient(): " + client.toString());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		});
        
        btnListar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
    
        btnGetFile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Data data = new Data();
				data.setName("mano.png");
				data.setPath(path);
				String url = "http://10.0.2.2:8080/jaxrs/rest/file";
				String action = "net.sgoliver.intent.action.get.file";
				try {
					Boolean result = 
					HttpClientManager.getInstance().downloadFile(ServicioWebRest.this,url,action,data,true);
					Log.d(TAG, "downloadFile(): " + result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		});
    
        btnPutFile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Data data = new Data();
				data.setName("whatsapp.png");
				data.setPath(path);
				
				String url = "http://10.0.2.2:8080/jaxrs/rest/file";
				String action = "net.sgoliver.intent.action.put.file";
				try {
					Boolean result = 
					HttpClientManager.getInstance().uploadFile(ServicioWebRest.this,url,action,data,true);
					Log.d(TAG, "uploadFile(): " + result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		});

	    btnPostFile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Data data = new Data();
				data.setName("whatsapp.png");
				Client client = new Client();
				client.setIdentifier( 1024);
				client.setName("nombre");
				client.setTelephone("teléfono");
				client.setAddress("dirección");
				data.setClient(client);
				data.setPath(path);
				
				
				String url = "http://10.0.2.2:8080/jaxrs/rest/file";
				String action = "net.sgoliver.intent.action.post.file";
				try {
					Boolean result = 
					HttpClientManager.getInstance().uploadDocument(ServicioWebRest.this,url,action,data,true);
					Log.d(TAG, "uploadDocument(): " + result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				
//				HttpPostFileAsyncTask httpPostFileAsyncTask = 
//				new HttpPostFileAsyncTask(ServicioWebRest.this,FILE,path,user,password);
//
//				httpPostFileAsyncTask.execute(data);
			}
		});
	}
    
    @Override
    public void onAsynTaskResult(int resultCode, Intent data) {
    	if (resultCode == RESULT_OK) {
	        if(data.getAction().equals(HttpClientManager.GET)) {
	            Client result = data.getParcelableExtra("result");
	            if (result != null) {
	            	Log.d(TAG,data.getAction() + ": " + result.getIdentifier());
	            }
	        }
	        else if(data.getAction().equals(HttpClientManager.GETS)) {
	            List<Client> result = data.getParcelableArrayListExtra("result");
	            if (result != null) {
		            for (Client client : result) {
		            	Log.d(TAG,data.getAction() + ": " + client.getIdentifier());
		            }
	            }
	        }
			else  {
				Boolean result = data.getBooleanExtra("result", false);
				Log.d(TAG, data.getAction() + ": " + result.toString());
			}
    	}
    	else if (resultCode == RESULT_CANCELED) {
    	}
    }
    
    /*
     * Basic authentication header.
     */
    public String getBasicAuthentication(String user, String password) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append(user).append(":").append(password);
    	String authentication = 
    	Base64.encodeToString(buffer.toString().getBytes(),Base64.NO_WRAP);
    	buffer.setLength(0);
    	buffer.append("Basic ").append(authentication);
    	return buffer.toString();
    }

    /**
     * Computes an RFC 2104-compliant HMAC signature for an array of bytes and
     * returns the result as a Base64 encoded string.
     */
    protected String sign(
    		byte[] data, String key,
            String algorithm) throws Exception {
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm));
            byte[] signature = mac.doFinal(data);
	        return Base64.encodeToString(signature,Base64.NO_WRAP | Base64.NO_PADDING);
        } catch (Exception e) {
            throw e;
        }
    }
}