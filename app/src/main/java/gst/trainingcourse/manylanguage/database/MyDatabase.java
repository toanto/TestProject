package gst.trainingcourse.manylanguage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import gst.trainingcourse.manylanguage.model.Account;

public class MyDatabase extends SQLiteOpenHelper {

    private Context mContext;
    private static final String NAME_DATABASE = "ManyLanguage";
    private static final int DB_VERSION = 3;
    private static final String TABLE = "Account";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "address";
    private static final String TELEPHONE_NUMBER = "telephone";
    private static final String IMAGE = "image";

    //Tạo bảng
    private static final String CREATE_TABLE = " CREATE TABLE " + TABLE + " ( " +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USERNAME + " varchar(255), " +
            PASSWORD + " varchar(255), " +
            EMAIL + " varchar(255), " +
            ADDRESS + " varchar(255), " +
            TELEPHONE_NUMBER + " varchar(255), " +
            IMAGE + " blob);";

    //Xóa Bảng
    private static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;

    public MyDatabase(Context context) {
        super(context, NAME_DATABASE, null, DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Thực thi lệnh
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Thực thi lệnh
        sqLiteDatabase.execSQL(DROP_TABLE);
        //Tạo lại db
        onCreate(sqLiteDatabase);
    }

    //Thêm 1 tài khoản mới
    public void addAccount(Account account) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, account.getUsername());
        contentValues.put(PASSWORD, account.getPassword());
        contentValues.put(EMAIL, account.getEmail());
        contentValues.put(ADDRESS, account.getAddress());
        contentValues.put(TELEPHONE_NUMBER, account.getTelephone());
        contentValues.put(IMAGE, account.getImage());

        sqLiteDatabase.insert(TABLE, null, contentValues);
        sqLiteDatabase.close();
    }

    public Account getAccount(int id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE, null, ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null)
            cursor.moveToNext();
        Account account = new Account(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getBlob(6));

        return account;
    }

    public List<Account> getAllAccount() {
        List<Account> accountList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE;

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.moveToNext();

        while (cursor.isAfterLast() == false) {
            Account account = new Account(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getBlob(6));
            accountList.add(account);
            cursor.moveToNext();
        }

        return accountList;
    }

    public boolean checkAccountLogin(String username, String password) {
        //select * from Account where username = 'trung' and password = '123';
        String query = "SELECT * FROM " + TABLE + " WHERE " + USERNAME + "= '" + username + "'AND " + PASSWORD + "= '" + password + "'";

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            sqLiteDatabase.close();
            return false;
        } else {
            cursor.close();
            sqLiteDatabase.close();
            return true;
        }
    }

    public boolean checkAccountRegister(String username) {
        String query = "SELECT * FROM " + TABLE + " WHERE " + USERNAME + "= '" + username + "'";

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            sqLiteDatabase.close();
            return false;
        } else {
            cursor.close();
            sqLiteDatabase.close();
            return true;
        }
    }

    public int getId(String username) {
        //select * from table where username = 'trung'
        String query = "SELECT * FROM " + TABLE + " WHERE " + USERNAME + "= '" + username + "'";

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        int id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(ID));
        }
        cursor.close();
        sqLiteDatabase.close();

        return id;
    }

    //Sửa 1 tài khoản
    public void updateAccountInfo(Account account) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL, account.getEmail());
        contentValues.put(ADDRESS, account.getAddress());
        contentValues.put(TELEPHONE_NUMBER, account.getTelephone());

        sqLiteDatabase.update(TABLE, contentValues, ID + " = ?", new String[]{String.valueOf(account.getId())});
        sqLiteDatabase.close();
    }

    public void updateAccountPass(Account account) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD, account.getPassword());

        sqLiteDatabase.update(TABLE, contentValues, ID + " = ?", new String[]{String.valueOf(account.getId())});
        sqLiteDatabase.close();
    }

    public void updateAccountImage(Account account) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE, account.getImage());

        sqLiteDatabase.update(TABLE, contentValues, ID + " = ?", new String[]{String.valueOf(account.getId())});
        sqLiteDatabase.close();
    }

    //Xóa 1 tài khoản
    public void deleteAccount(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE, ID + " = ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public void deleteDatabase() {
        mContext.deleteDatabase(NAME_DATABASE);
    }
}
