package com.example.framework.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MESSAGE_BEAN".
*/
public class MessageBeanDao extends AbstractDao<MessageBean, Long> {

    public static final String TABLENAME = "MESSAGE_BEAN";

    /**
     * Properties of entity MessageBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Message_img = new Property(1, Integer.class, "message_img", false, "MESSAGE_IMG");
        public final static Property Message_title = new Property(2, String.class, "message_title", false, "MESSAGE_TITLE");
        public final static Property Message_message = new Property(3, String.class, "message_message", false, "MESSAGE_MESSAGE");
        public final static Property Message_date = new Property(4, String.class, "message_date", false, "MESSAGE_DATE");
    }


    public MessageBeanDao(DaoConfig config) {
        super(config);
    }
    
    public MessageBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MESSAGE_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"MESSAGE_IMG\" INTEGER NOT NULL ," + // 1: message_img
                "\"MESSAGE_TITLE\" TEXT NOT NULL ," + // 2: message_title
                "\"MESSAGE_MESSAGE\" TEXT NOT NULL ," + // 3: message_message
                "\"MESSAGE_DATE\" TEXT NOT NULL );"); // 4: message_date
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MESSAGE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MessageBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMessage_img());
        stmt.bindString(3, entity.getMessage_title());
        stmt.bindString(4, entity.getMessage_message());
        stmt.bindString(5, entity.getMessage_date());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MessageBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMessage_img());
        stmt.bindString(3, entity.getMessage_title());
        stmt.bindString(4, entity.getMessage_message());
        stmt.bindString(5, entity.getMessage_date());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MessageBean readEntity(Cursor cursor, int offset) {
        MessageBean entity = new MessageBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // message_img
            cursor.getString(offset + 2), // message_title
            cursor.getString(offset + 3), // message_message
            cursor.getString(offset + 4) // message_date
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MessageBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMessage_img(cursor.getInt(offset + 1));
        entity.setMessage_title(cursor.getString(offset + 2));
        entity.setMessage_message(cursor.getString(offset + 3));
        entity.setMessage_date(cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MessageBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MessageBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MessageBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
