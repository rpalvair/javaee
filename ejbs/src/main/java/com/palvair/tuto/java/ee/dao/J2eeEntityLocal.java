package com.palvair.tuto.java.ee.dao;

import com.palvair.tuto.java.ee.entity.J2eeEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by rpalvair on 01/12/2014.
 */
@Local
public interface J2eeEntityLocal {

    public void create(J2eeEntity entity);

    public void edit(J2eeEntity entity);

    public void remove(J2eeEntity entity);

    public J2eeEntity find(Object id);

    public List<J2eeEntity> findAll();
}
