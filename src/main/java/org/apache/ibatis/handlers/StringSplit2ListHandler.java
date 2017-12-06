package org.apache.ibatis.handlers;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by xiaowu on 2016/10/13.
 */
public class StringSplit2ListHandler implements TypeHandler<List<String>> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
    }

    @Override
    public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
        String result = resultSet.getString(s);
        if (Strings.isNullOrEmpty(result)) return Lists.newArrayList();
        return Splitter.on(",").splitToList(result);
    }

    @Override
    public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
        String result = resultSet.getString(i);
        if (Strings.isNullOrEmpty(result)) return Lists.newArrayList();
        return Splitter.on(",").splitToList(result);
    }

    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String result = callableStatement.getString(i);
        if (Strings.isNullOrEmpty(result)) return Lists.newArrayList();
        return Splitter.on(",").splitToList(result);
    }
}
