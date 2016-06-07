package com.ait.db.data;

public enum NetworkEntityType {
	BASE_DATA("base_data"), 
	MCC_MNC("mcc_mnc"), 
	FAILURE_CLASS("failure_class"), 
	USER_EQUIPMENT("user_equipment"),
	EVENT_CAUSE("event_cause");
	
	private String networkEntityType;
	
	private NetworkEntityType(String networkEntityType) {
		this.setNetworkEntityType(networkEntityType);
	}

	public String getNetworkEntityType() {
		return networkEntityType;
	}

	public void setNetworkEntityType(String networkEntityType) {
		this.networkEntityType = networkEntityType;
	}
}
