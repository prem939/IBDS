package com.example.ibds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ibds.Do.CommentDo;
import com.example.ibds.Do.IdDo;
import com.example.ibds.Do.LeadDo;
import com.example.ibds.Do.SignUpDo;
import com.example.ibds.Do.TaskDo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "salesman";
    private static final String TABLE_Users = "tblUsers";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";

    String USER_TABLE = "CREATE TABLE " + TABLE_Users + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
            + KEY_EMAIL + " TEXT,"
            + KEY_PASS + " TEXT" + ")";
    String TASK_TABLE = "CREATE TABLE IF NOT EXISTS tblTask ( _id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, isDone TEXT, createdTiming TEXT, allocatedTime TEXT, accuracy TEXT, endTiming TEXT )";
    String ID_TABLE = "CREATE TABLE IF NOT EXISTS tblId ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, password TEXT, status TEXT )";
    String LEAD_TABLE = "CREATE TABLE IF NOT EXISTS tblLead ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, phone TEXT, company TEXT, title TEXT, comment TEXT, createdTime TEXT)";
    String COMMENT_TABLE = "CREATE TABLE IF NOT EXISTS tblComment ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, comment TEXT)";
    SignUpDo signUpDo;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TASK_TABLE);
        sqLiteDatabase.execSQL(LEAD_TABLE);
        sqLiteDatabase.execSQL(COMMENT_TABLE);
        sqLiteDatabase.execSQL(USER_TABLE);
        sqLiteDatabase.execSQL(ID_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "tblTask");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "tblLead");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "tblComment");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "tblId");
        onCreate(sqLiteDatabase);
    }

    public boolean InsertTask(TaskDo taskDo) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("task", taskDo.getTask());
            if (taskDo.isCheck())
                values.put("isDone", "true");
            else
                values.put("isDone", "false");
            values.put("createdTiming", taskDo.getCreatedtiming());
            values.put("allocatedTime", taskDo.getAllocatedTime());
            values.put("accuracy", taskDo.getAccuracy());
            db.insert("tblTask", null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<TaskDo> getAllTaskList() {
        SQLiteDatabase db = null;
        List<TaskDo> taskList = new ArrayList<>();
        try {
            db = this.getWritableDatabase();
            String selectQuery = "SELECT  * FROM tblTask WHERE isDone='false'";
            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                do {
                    TaskDo task = new TaskDo();
                    task.setTask(c.getString(c.getColumnIndex("task")));
                    task.setCheck(false);
                    task.setCreatedtiming(c.getString(c.getColumnIndex("createdTiming")));
                    task.setAllocatedTime(c.getString(c.getColumnIndex("allocatedTime")));
                    task.setAccuracy(c.getString(c.getColumnIndex("accuracy")));
                    taskList.add(task);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskList;
    }

    public List<TaskDo> getAllIndexList() {
        SQLiteDatabase db = null;
        List<TaskDo> taskList = new ArrayList<>();
        try {
            db = this.getWritableDatabase();
            String selectQuery = "SELECT  * FROM tblTask WHERE isDone='true'";
            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                do {
                    TaskDo task = new TaskDo();
                    task.setTask(c.getString(c.getColumnIndex("task")));
                    task.setCheck(true);
                    task.setCreatedtiming(c.getString(c.getColumnIndex("createdTiming")));
                    task.setEndTiming(c.getString(c.getColumnIndex("endTiming")));
                    taskList.add(task);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskList;
    }

    public void UpdateTask(TaskDo task) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE tblTask SET endTiming =  " + "'" + task.getEndTiming() + "'" + "," + " isDone = 'true'" + " WHERE task = " + "'" + task.getTask() + "'" + "AND createdTiming = " + "'" + task.getCreatedtiming() + "'");
    }

    public List<LeadDo> getAllLeadDetails() {
        SQLiteDatabase db = null;
        List<LeadDo> leadList = new ArrayList<>();
        try {
            db = this.getWritableDatabase();
            String selectQuery = "SELECT  * FROM tblLead";
            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                do {
                    LeadDo lead = new LeadDo(c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("email")),
                            c.getString(c.getColumnIndex("company")), c.getString(c.getColumnIndex("phone")), c.getString(c.getColumnIndex("title")),
                            c.getString(c.getColumnIndex("comment")), c.getString(c.getColumnIndex("createdTime")));
                    leadList.add(lead);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return leadList;
    }

    public boolean insertLead(LeadDo leadDo) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", leadDo.getName());
            values.put("email", leadDo.getEamil());
            values.put("company", leadDo.getCompany());
            values.put("phone", leadDo.getPhone());
            values.put("title", leadDo.getTitle());
            values.put("comment", leadDo.getComment());
            values.put("createdTime", leadDo.getCreatedTime());
            db.insert("tblLead", null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertComment(CommentDo comment) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", comment.getName());
            values.put("comment", comment.getComment());
            db.insert("tblComment", null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<CommentDo> getCommentAsPerTheName(String name) {
        SQLiteDatabase db = null;
        List<CommentDo> commentList = new ArrayList<>();
        try {
            db = this.getWritableDatabase();
            String selectQuery = "SELECT  * FROM tblComment WHERE name='" + name + "'";
            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                do {
                    CommentDo comment = new CommentDo(c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("comment")));
                    commentList.add(comment);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentList;
    }

    public boolean InsertUserDetails(SignUpDo signUpDo) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, signUpDo.getName());
            values.put(KEY_EMAIL, signUpDo.getEmail());
            values.put(KEY_PASS, signUpDo.getPassword());
            db.insert(TABLE_Users, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isUserExist(String email, String password) {
        SQLiteDatabase db = null;
        boolean dataCount = false;
        try {
            db = this.getWritableDatabase();
            String selectQuery = "SELECT  * FROM tblUsers WHERE email ='" + email + "'  AND password = '" + password + "' ";
            Cursor c = db.rawQuery(selectQuery, null);
            if (c != null)
                c.moveToFirst();
            signUpDo = new SignUpDo(c.getString(c.getColumnIndex(KEY_NAME)), c.getString(c.getColumnIndex(KEY_EMAIL)), c.getString(c.getColumnIndex(KEY_PASS)));
            if (c.getCount() > 0) {
                dataCount = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return dataCount;
    }

    public SignUpDo getUserDetails() {
        return signUpDo;
    }

    public boolean InsertId(IdDo idDo) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", idDo.getName());
            values.put("password", idDo.getPassword());
            values.put("status", idDo.getStatus());
            db.insert("tblId", null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<IdDo> getIds() {
        SQLiteDatabase db = null;
        List<IdDo> idList = new ArrayList<>();
        try {
            db = this.getWritableDatabase();
            String selectQuery = "SELECT  * FROM tblId";
            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                do {
                    IdDo idDo = new IdDo(c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("password")), c.getString(c.getColumnIndex("status")));
                    idList.add(idDo);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return idList;
    }
}
