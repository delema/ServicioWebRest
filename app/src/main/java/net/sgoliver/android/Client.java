package net.sgoliver.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class Client implements Parcelable {
	
	protected Integer identifier = null;
	protected String name = null;
	protected String address = null;
	protected String telephone = null;
	
    public Client() {
    	super();
    }
    public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	
	public static ArrayList<Client> valuesOf(JSONArray object) {
		
		ArrayList<Client> clientList = new ArrayList<Client>();
		
		try {
    		for (int i = 0; i < object.length(); i++) {
         		JSONObject clientJSON = object.getJSONObject(i);
        		Client client = valueOf(clientJSON);
	        	clientList.add(client);
    		}
		}
		catch (final JSONException e) {
			return null;
		}
		
		return clientList;
	}
	public JSONObject toJSONObject() {
    	
        JSONObject object = new JSONObject();

		try {
	        if (this.identifier != null) {
	            object.put("identifier", this.identifier);
	        }
	        if (!TextUtils.isEmpty(this.name)) {
	            object.put("name", this.name);
	        }
	        if (!TextUtils.isEmpty(this.address)) {
	            object.put("address", this.address);
	        }
	        if (!TextUtils.isEmpty(this.telephone)) {
	            object.put("telephone", this.telephone);
	        }
        }
		catch (final JSONException e) {
			return null;
		}

        return object;
    }

	// Parcelable funcionality
	
    public static final Parcelable.Creator<Client> CREATOR = new
    Parcelable.Creator<Client>() {
    	
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
    private Client(Parcel in) {
        readFromParcel(in);
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(identifier);
        out.writeString(name);
        out.writeString(address);
        out.writeString(telephone);
    }
    public void readFromParcel(Parcel in) {
    	identifier = in.readInt();
    	name = in.readString();
    	address = in.readString();
    	telephone = in.readString();
    }
    public int describeContents() {
      return Parcelable.PARCELABLE_WRITE_RETURN_VALUE;
    }
}
