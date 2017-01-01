package com.kaishengit.dao;

import com.kaishengit.entity.Fav;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by sunny on 2016/12/25.
 */
public class FavDao {
    public Fav findById(Integer userId, Integer topicId) {
        String sql = "SELECT *\n" +
                "FROM t_fav\n" +
                "WHERE userid = ? AND topicid = ?";
        return DbHelp.query(sql, new BeanHandler<Fav>(Fav.class), userId, topicId);
    }

    public void addFav(Fav fav) {
        String sql = "INSERT INTO t_fav (userid, topicid) VALUES (?, ?)";
        DbHelp.update(sql, fav.getUserId(), fav.getTopicId());
    }

    public void deleteFavById(Integer userId, Integer topicId) {
        String sql = "DELETE FROM t_fav\n" +
                "WHERE userid = ? AND topicid = ?";
        DbHelp.update(sql, userId, topicId);
    }
}
