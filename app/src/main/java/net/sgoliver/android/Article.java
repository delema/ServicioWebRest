package net.sgoliver.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class Article implements Parcelable {
	
	protected String identifier = null;
	protected String name = null;
	protected String description = null;
	protected String authorization = null;
	
    public Article() {
    	super();
    }
    public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAuthorization() {
		return authorization;
	}
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	public static Article valueOf(JSONObject object) {
		
		Article article = new Article();
		
		try {
			if (!object.isNull("identifier")) {
				article.setIdentifier(object.getString("identifier"));
			}
			if (!object.isNull("name")) {
				article.setName(object.getString("name"));
			}
			if (!object.isNull("description")) {
				article.setDescription(object.getString("description"));
			}
		} 
		catch (final JSONException e) {
			return null;
		}
		
		return article;
	}
	
	public static ArrayList<Article> valuesOf(JSONArray object) {
		
		ArrayList<Article> clientList = new ArrayList<Article>();
		
		try {
    		for (int i = 0; i < object.length(); i++) {
         		JSONObject clientJSON = object.getJSONObject(i);
        		Article client = valueOf(clientJSON);
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
	        if (!TextUtils.isEmpty(this.description)) {
	            object.put("description", this.description);
	        }
        }
		catch (final JSONException e) {
			return null;
		}

        return object;
    }

	// Parcelable funcionality
	
    public static final Parcelable.Creator<Article> CREATOR = new
    Parcelable.Creator<Article>() {
    	
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
    private Article(Parcel in) {
        readFromParcel(in);
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(identifier);
        out.writeString(name);
        out.writeString(description);
     }
    public void readFromParcel(Parcel in) {
    	identifier = in.readString();
    	name = in.readString();
    	description = in.readString();
    }
    public int describeContents() {
      return Parcelable.PARCELABLE_WRITE_RETURN_VALUE;
    }
}
