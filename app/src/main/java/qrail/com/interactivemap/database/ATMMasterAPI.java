package qrail.com.interactivemap.database;

import android.database.Cursor;

public class ATMMasterAPI {

	public static final String TABLE_NAME = "ATMMaster";
	public static final String COLUMN_ID = "ATMId";
	public static final String COLUMN_BANK_NAME = "ATMBankName";
	public static final String COLUMN_BANK_IMAGE_NAME = "ATMBankImageName";

	public Cursor getAllATM(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);

	}

}
