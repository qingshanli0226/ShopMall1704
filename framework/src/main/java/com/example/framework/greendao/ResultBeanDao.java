package com.example.framework.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.framework.bean.ResultBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RESULT_BEAN".
*/
public class ResultBeanDao extends AbstractDao<ResultBean, String> {

    public static final String TABLENAME = "RESULT_BEAN";

    /**
     * Properties of entity ResultBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Password = new Property(2, String.class, "password", false, "PASSWORD");
        public final static Property Email = new Property(3, String.class, "email", false, "EMAIL");
        public final static Property Phone = new Property(4, String.class, "phone", false, "PHONE");
        public final static Property Point = new Property(5, String.class, "point", false, "POINT");
        public final static Property Address = new Property(6, String.class, "address", false, "ADDRESS");
        public final static Property Money = new Property(7, int.class, "money", false, "MONEY");
        public final static Property Avatar = new Property(8, String.class, "avatar", false, "AVATAR");
        public final static Property Token = new Property(9, String.class, "token", false, "TOKEN");
    }


    public ResultBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ResultBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RESULT_BEAN\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"PASSWORD\" TEXT," + // 2: password
                "\"EMAIL\" TEXT," + // 3: email
                "\"PHONE\" TEXT," + // 4: phone
                "\"POINT\" TEXT," + // 5: point
                "\"ADDRESS\" TEXT," + // 6: address
                "\"MONEY\" INTEGER NOT NULL ," + // 7: money
                "\"AVATAR\" TEXT," + // 8: avatar
                "\"TOKEN\" TEXT);"); // 9: token
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RESULT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ResultBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(4, email);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
 
        String point = entity.getPoint();
        if (point != null) {
            stmt.bindString(6, point);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(7, address);
        }
        stmt.bindLong(8, entity.getMoney());
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(9, avatar);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(10, token);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ResultBean entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(3, password);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(4, email);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
 
        String point = entity.getPoint();
        if (point != null) {
            stmt.bindString(6, point);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(7, address);
        }
        stmt.bindLong(8, entity.getMoney());
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(9, avatar);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(10, token);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ResultBean readEntity(Cursor cursor, int offset) {
        ResultBean entity = new ResultBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // password
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // email
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // phone
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // point
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // address
            cursor.getInt(offset + 7), // money
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // avatar
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // token
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ResultBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPassword(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEmail(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPhone(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPoint(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAddress(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMoney(cursor.getInt(offset + 7));
        entity.setAvatar(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setToken(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ResultBean entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(ResultBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ResultBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
