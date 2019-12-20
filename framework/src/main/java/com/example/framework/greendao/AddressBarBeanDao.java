package com.example.framework.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.framework.bean.AddressBarBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ADDRESS_BAR_BEAN".
*/
public class AddressBarBeanDao extends AbstractDao<AddressBarBean, Long> {

    public static final String TABLENAME = "ADDRESS_BAR_BEAN";

    /**
     * Properties of entity AddressBarBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Consignee = new Property(2, String.class, "consignee", false, "CONSIGNEE");
        public final static Property Cell_phone_number = new Property(3, String.class, "cell_phone_number", false, "CELL_PHONE_NUMBER");
        public final static Property Location = new Property(4, String.class, "location", false, "LOCATION");
        public final static Property Detailed_address = new Property(5, String.class, "detailed_address", false, "DETAILED_ADDRESS");
        public final static Property Tag = new Property(6, String.class, "tag", false, "TAG");
    }


    public AddressBarBeanDao(DaoConfig config) {
        super(config);
    }
    
    public AddressBarBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ADDRESS_BAR_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"CONSIGNEE\" TEXT," + // 2: consignee
                "\"CELL_PHONE_NUMBER\" TEXT," + // 3: cell_phone_number
                "\"LOCATION\" TEXT," + // 4: location
                "\"DETAILED_ADDRESS\" TEXT," + // 5: detailed_address
                "\"TAG\" TEXT);"); // 6: tag
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ADDRESS_BAR_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AddressBarBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String consignee = entity.getConsignee();
        if (consignee != null) {
            stmt.bindString(3, consignee);
        }
 
        String cell_phone_number = entity.getCell_phone_number();
        if (cell_phone_number != null) {
            stmt.bindString(4, cell_phone_number);
        }
 
        String location = entity.getLocation();
        if (location != null) {
            stmt.bindString(5, location);
        }
 
        String detailed_address = entity.getDetailed_address();
        if (detailed_address != null) {
            stmt.bindString(6, detailed_address);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(7, tag);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AddressBarBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String consignee = entity.getConsignee();
        if (consignee != null) {
            stmt.bindString(3, consignee);
        }
 
        String cell_phone_number = entity.getCell_phone_number();
        if (cell_phone_number != null) {
            stmt.bindString(4, cell_phone_number);
        }
 
        String location = entity.getLocation();
        if (location != null) {
            stmt.bindString(5, location);
        }
 
        String detailed_address = entity.getDetailed_address();
        if (detailed_address != null) {
            stmt.bindString(6, detailed_address);
        }
 
        String tag = entity.getTag();
        if (tag != null) {
            stmt.bindString(7, tag);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AddressBarBean readEntity(Cursor cursor, int offset) {
        AddressBarBean entity = new AddressBarBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // consignee
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // cell_phone_number
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // location
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // detailed_address
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // tag
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AddressBarBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setConsignee(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCell_phone_number(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLocation(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDetailed_address(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTag(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AddressBarBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AddressBarBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AddressBarBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
