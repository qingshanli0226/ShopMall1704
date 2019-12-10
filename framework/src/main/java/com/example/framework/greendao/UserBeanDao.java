package com.example.framework.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.framework.bean.UserBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_BEAN".
*/
public class UserBeanDao extends AbstractDao<UserBean, Long> {

    public static final String TABLENAME = "USER_BEAN";

    /**
     * Properties of entity UserBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Imgsrc = new Property(1, String.class, "imgsrc", false, "IMGSRC");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Password = new Property(3, String.class, "password", false, "PASSWORD");
        public final static Property Phone = new Property(4, int.class, "phone", false, "PHONE");
        public final static Property Integral = new Property(5, String.class, "integral", false, "INTEGRAL");
    }


    public UserBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"IMGSRC\" TEXT," + // 1: imgsrc
                "\"NAME\" TEXT," + // 2: name
                "\"PASSWORD\" TEXT," + // 3: password
                "\"PHONE\" INTEGER NOT NULL ," + // 4: phone
                "\"INTEGRAL\" TEXT);"); // 5: integral
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String imgsrc = entity.getImgsrc();
        if (imgsrc != null) {
            stmt.bindString(2, imgsrc);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
        stmt.bindLong(5, entity.getPhone());
 
        String integral = entity.getIntegral();
        if (integral != null) {
            stmt.bindString(6, integral);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String imgsrc = entity.getImgsrc();
        if (imgsrc != null) {
            stmt.bindString(2, imgsrc);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
        stmt.bindLong(5, entity.getPhone());
 
        String integral = entity.getIntegral();
        if (integral != null) {
            stmt.bindString(6, integral);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserBean readEntity(Cursor cursor, int offset) {
        UserBean entity = new UserBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // imgsrc
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // password
            cursor.getInt(offset + 4), // phone
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // integral
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setImgsrc(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPassword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPhone(cursor.getInt(offset + 4));
        entity.setIntegral(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
