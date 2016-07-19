package com.ait.db.model;

import java.math.BigInteger;

public class IMSIHelperObject {
	private BigInteger imsi;
	
	public IMSIHelperObject(BigInteger imsi){
		this.imsi = imsi;
	}

	public BigInteger getImsi() {
		return imsi;
	}

}
