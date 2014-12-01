package com.palvair.tuto.java.ee.dao;

import com.palvair.tuto.java.ee.entity.J2eeEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author rpalvair
 */
@Stateless
public class J2eeEntityDao extends AbstractDao<J2eeEntity> implements J2eeEntityLocal {

    @PersistenceContext(unitName = "localhost")
    private EntityManager entityManager;

    public J2eeEntityDao() {
        super(J2eeEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
}
