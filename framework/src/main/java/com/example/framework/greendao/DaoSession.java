package com.example.framework.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.framework.bean.AddressBarBean;
import com.example.framework.bean.MessageBean;
import com.example.framework.bean.ResultBean;
import com.example.framework.bean.ShopStepTimeRealBean;

import com.example.framework.greendao.AddressBarBeanDao;
import com.example.framework.greendao.MessageBeanDao;
import com.example.framework.greendao.ResultBeanDao;
import com.example.framework.greendao.ShopStepTimeRealBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig addressBarBeanDaoConfig;
    private final DaoConfig messageBeanDaoConfig;
    private final DaoConfig resultBeanDaoConfig;
    private final DaoConfig shopStepTimeRealBeanDaoConfig;

    private final AddressBarBeanDao addressBarBeanDao;
    private final MessageBeanDao messageBeanDao;
    private final ResultBeanDao resultBeanDao;
    private final ShopStepTimeRealBeanDao shopStepTimeRealBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        addressBarBeanDaoConfig = daoConfigMap.get(AddressBarBeanDao.class).clone();
        addressBarBeanDaoConfig.initIdentityScope(type);

        messageBeanDaoConfig = daoConfigMap.get(MessageBeanDao.class).clone();
        messageBeanDaoConfig.initIdentityScope(type);

        resultBeanDaoConfig = daoConfigMap.get(ResultBeanDao.class).clone();
        resultBeanDaoConfig.initIdentityScope(type);

        shopStepTimeRealBeanDaoConfig = daoConfigMap.get(ShopStepTimeRealBeanDao.class).clone();
        shopStepTimeRealBeanDaoConfig.initIdentityScope(type);

        addressBarBeanDao = new AddressBarBeanDao(addressBarBeanDaoConfig, this);
        messageBeanDao = new MessageBeanDao(messageBeanDaoConfig, this);
        resultBeanDao = new ResultBeanDao(resultBeanDaoConfig, this);
        shopStepTimeRealBeanDao = new ShopStepTimeRealBeanDao(shopStepTimeRealBeanDaoConfig, this);

        registerDao(AddressBarBean.class, addressBarBeanDao);
        registerDao(MessageBean.class, messageBeanDao);
        registerDao(ResultBean.class, resultBeanDao);
        registerDao(ShopStepTimeRealBean.class, shopStepTimeRealBeanDao);
    }
    
    public void clear() {
        addressBarBeanDaoConfig.clearIdentityScope();
        messageBeanDaoConfig.clearIdentityScope();
        resultBeanDaoConfig.clearIdentityScope();
        shopStepTimeRealBeanDaoConfig.clearIdentityScope();
    }

    public AddressBarBeanDao getAddressBarBeanDao() {
        return addressBarBeanDao;
    }

    public MessageBeanDao getMessageBeanDao() {
        return messageBeanDao;
    }

    public ResultBeanDao getResultBeanDao() {
        return resultBeanDao;
    }

    public ShopStepTimeRealBeanDao getShopStepTimeRealBeanDao() {
        return shopStepTimeRealBeanDao;
    }

}
