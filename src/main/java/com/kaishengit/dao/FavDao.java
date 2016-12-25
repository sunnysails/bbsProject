package com.kaishengit.dao;

import com.kaishengit.entity.Fav;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by sunny on 2016/12/25.
 */
public class FavDao {
    public Fav findById(Integer userId, Integer topicId) {
        String sql = "select * from t_fav where userid = ? and topicid = ?";
        return DbHelp.query(sql, new BeanHandler<Fav>(Fav.class), userId, topicId);
    }

    public void addFav(Fav fav) {
        String sql = "insert into t_fav (userid,topicid)values (?,?)";
        DbHelp.update(sql, fav.getUserId(), fav.getTopicId());
    }

    public void deleteFavById(Integer userId, Integer topicId) {
        String sql = "delete from t_fav where userid = ? and topicid = ?";
        DbHelp.update(sql, userId, topicId);
    }
}
