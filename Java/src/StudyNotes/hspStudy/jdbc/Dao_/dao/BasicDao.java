package StudyNotes.hspStudy.jdbc.Dao_.dao;

import StudyNotes.hspStudy.jdbc.Dao_.utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @version 1.0
 * @coding: utf-8
 * 开发 BasicDAO , 是其他 DAO 的父类, 使用到 apache-dbutils
 */
public class BasicDao<T> { //泛型指定具体类型

    private QueryRunner qr = new QueryRunner();

    //开发通用的 dml 方法, 针对任意的表
    public int update(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            int update = qr.update(connection, sql, parameters);
            return update;
        } catch (SQLException e) {
            throw new RuntimeException(e); //将编译异常->运行异常 ,抛出
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }


    //返回多个对象(即查询的结果是多行), 针对任意表
    public List<T> queryMulti(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e); //将编译异常->运行异常 ,抛出
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }


    //查询单行结果 的通用方法
    public T querySingle(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e); //将编译异常->运行异常 ,抛出
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }


    //查询单行单列的方法,即返回单值的方法
    public Object queryScalar(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new ScalarHandler(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e); //将编译异常->运行异常 ,抛出
        } finally {
            JDBCUtilsByDruid.close(null, null, connection);
        }
    }
}