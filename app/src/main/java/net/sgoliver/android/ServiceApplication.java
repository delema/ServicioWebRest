package net.sgoliver.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Application;

public class ServiceApplication extends Application {
	
    @Override
	public void onCreate()
	{
		super.onCreate();
        File files = getExternalFilesDir(null);
        String path = files.getAbsolutePath();
        
	    copy(path, "whatsapp.png");
	}
    
    protected void copy(String path, String name)
    {
    	int size = 10240;
    	int bytes = 0;
        byte[] data = new byte[size];

        InputStream in = null;
        OutputStream out = null;
        try
        {
        	in = getAssets().open(name);
        	File file = new File(path, name);
        	out = new FileOutputStream(file);
            while ((bytes = in.read(data)) != -1)
            	out.write(data, 0, bytes);
        }
        catch (IOException e)
        {}
        finally
        {
            try
            {if (in != null) in.close();
            }catch(IOException e)
            {}
            try
            {if (out != null) out.close();}
            catch(IOException e)
            {}
        }
    }
}
