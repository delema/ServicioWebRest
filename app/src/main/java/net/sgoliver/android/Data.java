package net.sgoliver.android;

public class Data {
	
	protected Client client = null;
	protected String name = null;
	protected String path = null;
	
    public Data() {
    	super();
    }
    public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
