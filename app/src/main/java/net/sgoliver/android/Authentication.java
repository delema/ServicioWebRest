package net.sgoliver.android;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class Authentication implements Parcelable {
	
	protected String user = null;
	protected String password = null;
	protected String token = null;
	
	public Authentication() {
		super();
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public static Client valueOf(JSONObject object) {
		
		Client client = new Client();
		
		try {
			if (!object.isNull("identifier")) {
				client.setIdentifier(object.getInt("identifier"));
			}
			if (!object.isNull("name")) {
				client.setName(object.getString("name"));
			}
			if (!object.isNull("address")) {
				client.setAddress(object.getString("address"));
			}
			if (!object.isNull("telephone")) {
				client.setTelephone(object.getString("telephone"));
			}
		} 
		catch (final JSONException e) {
			return null;
		}
		
		return client;
	}
	
	public JSONObject toJSONObject() {
    	
        JSONObject object = new JSONObject();

		try {
	        if (this.user != null) {
	            object.put("user", this.user);
	        }
	        if (!TextUtils.isEmpty(this.password)) {
	            object.put("password", this.password);
	        }
	        if (!TextUtils.isEmpty(this.token)) {
	            object.put("token", this.token);
	        }
        }
		catch (final JSONException e) {
			return null;
		}

        return object;
    }

	// Parcelable funcionality
	
    public static final Parcelable.Creator<Authentication> CREATOR = new
    Parcelable.Creator<Authentication>() {
    	
        public Authentication createFromParcel(Parcel in) {
            return new Authentication(in);
        }

        public Authentication[] newArray(int size) {
            return new Authentication[size];
        }
    };
    private Authentication(Parcel in) {
        readFromParcel(in);
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(user);
        out.writeString(password);
        out.writeString(token);
    }
    public void readFromParcel(Parcel in) {
    	user = in.readString();
    	password = in.readString();
    	token = in.readString();
    }
    public int describeContents() {
      return Parcelable.PARCELABLE_WRITE_RETURN_VALUE;
    }
}
