package com.aoshine.demo.dao;

import com.aoshine.demo.entity.response.db.Item;
import com.aoshine.demo.entity.response.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

//往数据库填写favorite某个item的资料
@Repository
public class FavoriteDao {
    @Autowired
    private SessionFactory sessionFactory;

    // Insert a favorite record to the database
    public void setFavoriteItem(String userId, Item item){
        Session session = null;

        try {
            session = sessionFactory.openSession(); //通过这个api获得对象，对数据库进行操作
            User user = session.get(User.class, userId);
            user.getItemSet().add(item);

            session.beginTransaction();  //开启一个事务
            session.save(user);
            session.getTransaction().commit();

        } catch (Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback(); //如果出现异常，可以回滚，回到save之前的动作
;
        }finally {
            if (session != null) session.close();
        }

    }

// Remove a favorite record from the database
    public void unsetFavoriteItem(String userId, String itemId){
        Session session = null;

        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            Item item = session.get(Item.class, itemId);
            user.getItemSet().remove(item);
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (session != null) session.getTransaction().rollback();

        } finally {
            if (session != null) session.close();
        }
    }


    public Set<Item> getFavoriteItems(String userId){
        Session session = null;
        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            if (user != null){
                return user.getItemSet();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return new HashSet<>();
    }
}

