/**
 * 
 */
package com.xinwei.util.mybatis;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**  
 *   
 * BooleanTypeHandler  
 * java中的boolean和jdbc中的char之间转换;true-Y;false-N
 */
public class BooleanTypeHandler implements TypeHandler {

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet, java.lang.String)
     */
    @Override
    public Object getResult(ResultSet arg0, String arg1) throws SQLException {
        String str = arg0.getString(arg1);
        Boolean rt = Boolean.TRUE;
        if (str.equalsIgnoreCase("0")){
            rt = Boolean.FALSE;
        }
        return rt; 
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.Callablestatement, int)
     */
    @Override
    public Object getResult(CallableStatement arg0, int arg1)
            throws SQLException {
        Boolean b = arg0.getBoolean(arg1);
        return b == true ? 1 : 0;
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.type.TypeHandler#setParameter(java.sql.Preparedxinweiement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
     */
    @Override
    public void setParameter(PreparedStatement arg0, int arg1, Object arg2,
            JdbcType arg3) throws SQLException {
        Boolean b = (Boolean) arg2;
        byte value = (byte) ((Boolean) b == true ? 1 : 0);
        arg0.setByte(arg1, value);
    }

    @Override
    public Object getResult(ResultSet arg0, int arg1) throws SQLException {
        return null;
    }

	
}
