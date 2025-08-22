package qrail.com.interactivemap.dataobjects;

public class ATMMaster {
	private String ATMId;
	private String ATMBankName;
	private String ATMBankImageName;

	public String getATMId() {
		return ATMId;
	}

	public void setATMId(String aTMId) {
		ATMId = aTMId;
	}

	public ATMMaster(String aTMId, String aTMBankName, String aTMBankImageName) {
		super();
		ATMId = aTMId;
		ATMBankName = aTMBankName;
		ATMBankImageName = aTMBankImageName;
	}

	public String getATMBankName() {
		return ATMBankName;
	}

	public void setATMBankName(String aTMBankName) {
		ATMBankName = aTMBankName;
	}

	public String getATMBankImageName() {
		return ATMBankImageName;
	}

	public void setATMBankImageName(String aTMBankImageName) {
		ATMBankImageName = aTMBankImageName;
	}

}
