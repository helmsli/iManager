package com.xinwei.util.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class DateTypeHandler implements TypeHandler {
	private static DateFormat df = new SimpleDateFormat("yyyyMMdd hh:mm:ss");

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		Date d=null;
		   try {
			d = df.parse(parameter.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  java.sql.Date sdate = new java.sql.Date(d.getTime());
		  ps.setDate(i, sdate);
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		return tranferType(rs.getDate(columnName));
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		 return tranferType(rs.getDate(columnIndex));
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return tranferType(cs.getDate(columnIndex));
	}

	private String tranferType(Date d) {
		System.out.println("*******");
		Date dte = new Date(d.getTime());
		return df.format(dte);
	}

}
