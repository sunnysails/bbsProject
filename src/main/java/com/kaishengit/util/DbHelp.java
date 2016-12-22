package com.kaishengit.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.kaishengit.exception.DataAccessException;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbHelp {

    private static Logger logger = LoggerFactory.getLogger(DbHelp.class);
    private static QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());

    private static Connection getConnection() {
        return ConnectionManager.getConnection();
    }

    public static void update(String sql, Object... params) throws DataAccessException {
        try {
            queryRunner.update(sql, params);

            logger.debug("SQL: {}", sql);
        } catch (SQLException e) {

            logger.error("执行{}异常", sql);
            throw new DataAccessException("执行" + sql + "异常", e);
        }
    }

    public static <T> T query(String sql, ResultSetHandler<T> handler, Object... params) throws DataAccessException {
        try {
            T t = queryRunner.query(sql, handler, params);

            logger.debug("SQL: {}", sql);
            return t;
        } catch (SQLException e) {

            logger.error("执行{}异常", sql);
            throw new DataAccessException("执行" + sql + "异常", e);
        }
    }

    public static Integer insert(String sql, Object... params) throws DataAccessException {
        try {

            logger.debug("SQL: {}", sql);
            return queryRunner.insert(sql, new ScalarHandler<Long>(), params).intValue();
        } catch (SQLException e) {

            logger.error("执行{}异常", sql);
            throw new DataAccessException("执行" + sql + "异常", e);
        }
    }

    private static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {

                logger.error("关闭Connection异常");
                throw new DataAccessException("关闭Connection异常", e);
            }
        }

    }


}
