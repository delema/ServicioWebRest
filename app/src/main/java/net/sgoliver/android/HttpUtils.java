package net.sgoliver.android;

import android.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpUtils {

    public static final int HTTP_CONNECTION_TIMEOUT = 5 * 1000;
    public static final int HTTP_SOCKET_TIMEOUT = 10 * 1000;
    public static final int HTTP_TIMEOUT = 1 * 1000;

    public static String getBasicAuthentication(String user, String password) {
    	
    	StringBuffer buffer = new StringBuffer();
    	buffer.append(user).append(":").append(password);
    	String authentication = 
    	Base64.encodeToString(buffer.toString().getBytes(),Base64.NO_WRAP);
    	buffer.setLength(0);
    	buffer.append("Basic ").append(authentication);
    	return buffer.toString();
    }

	public static void writeToFile(
	InputStream in,
	String path,
	String name) throws IOException {

		OutputStream outputStream = null;
		try {
			int read = 0;
			byte[] bytes = new byte[10240];

			File file = new File(path, name);
			outputStream = new FileOutputStream(file);
			while ((read = in.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} 
		catch (IOException e) {
			throw e;
		}
		finally {
			if (outputStream != null) {
				outputStream.flush();
				outputStream.close();
			}
		}
	}
}
