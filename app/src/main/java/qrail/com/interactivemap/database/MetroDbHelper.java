package qrail.com.interactivemap.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// BuildConfig import removed - using hardcoded application ID

public class MetroDbHelper extends SQLiteOpenHelper {
    private static MetroDbHelper sInstance;

    /**
     * Name of the Database
     */
    //private static final String DATABASE_NAME = "DelhiMetroDB.sqlite";
    private static final String DATABASE_NAME = "QatarRailDB.sqlite";
    /**
     * Database Version
     */
    private static final int DATABASE_VERSION = 56;

    private static Context context;

    private SQLiteDatabase mDb;
    private static String myPath;

    public static synchronized MetroDbHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MetroDbHelper(context.getApplicationContext());
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("co.qrail.iteractive.map", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("isDbCopied", false))
            myPath = sharedPreferences.getString("dbPath", null);

        return sInstance;
    }

    private MetroDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() {

        if (!checkDataBase()) {

            this.getReadableDatabase();
            copyAssets();
        }
        if (checkDataBase()) {
            SQLiteDatabase db_Read = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
            int versionOfActiveDatabase = db_Read.getVersion();
            db_Read.close();

            if (DATABASE_VERSION > versionOfActiveDatabase) {
                context.deleteDatabase(DATABASE_NAME);
                this.getReadableDatabase();
                copyAssets();
            }
        }

    }


    /**
     * This Function will weather Database Already Exists or Not
     *
     * @return Boolean true -IF DB Exists Else false
     */
    private static boolean checkDataBase() {

        SQLiteDatabase checkDB = null;
        File dbFile = null;
        if (!TextUtils.isEmpty(myPath))
            dbFile = new File(myPath);
        try {
            if (TextUtils.isEmpty(myPath)) {
                copyAssets();
            } else if (dbFile != null && dbFile.exists()) {
                checkDB = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.OPEN_READWRITE);
            }
        } catch (SQLiteException e) {
            // Log.v("error", "database does't exist yet");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * OPEN DataBase in ReadOnly Mode
     *
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
        // Open the database
        if (checkDataBase() || mDb != null) {
            mDb = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }

    }

    /**
     * Close Database
     */
    @Override
    public synchronized void close() {
        if (mDb != null)
            mDb.close();
        super.close();
    }

    /**
     * This Function is Used to Retrieve Station Data From Database
     *
     * @return Cursor Object - StationID , StationName
     */

    public Cursor fetchData(String tablename, String[] columns, String selection) {

        if (!checkDataBase() || mDb == null)
            openDataBase();

        return mDb.query(tablename, columns, selection, null, null, null, null);

    }

    public long updateData(String tablename, ContentValues values, String row, String[] whereArgs) {
        if (!checkDataBase() || mDb == null)
            openDataBase();

        return mDb.update(tablename, values, row, whereArgs);

    }

    public void updateColumn(String execQuery) {
        mDb.execSQL(execQuery);
    }

    public Cursor fetchData(String tableName, String selection, String[] selectionArgs, String orderBy) {
        if (!checkDataBase() || mDb == null)
            openDataBase();

        return mDb.query(tableName, null, selection, selectionArgs, null, null, orderBy);
    }

    private static void copyAssets() {

        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) {


            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(DATABASE_NAME);
                File outFile = new File(context.getExternalFilesDir(null), DATABASE_NAME);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
                myPath = outFile.getAbsolutePath();

                SharedPreferences prefs = context.getSharedPreferences("co.qrail.iteractive.map", Context.MODE_PRIVATE);
                prefs.edit().putString("dbPath", myPath).apply();
                prefs.edit().putBoolean("isDbCopied", true).apply();

            } catch (IOException e) {
                Log.e("tag", "Failed to copy asset file: " + DATABASE_NAME, e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

}