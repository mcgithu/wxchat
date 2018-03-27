package com.zixue.test;

import java.sql.SQLException;
import java.util.Map;

import org.junit.Test;

import com.zixue.enty.Token;
import com.zixue.util.CommonUtil;
import com.zixue.util.DBUtility;
import com.zixue.util.TokenUtil;

public class TestDBUtility {

    /**
    * 方法名：testgetConnection</br>
    * 详述：测试是否连接</br>
    * @throws
     */
    @Test
    public void testgetConnection() throws SQLException {
        DBUtility db = new DBUtility();
        System.out.println(db.getConnection());
    }
    @Test
    public void testGetToken3() {
        Map<String, Object> token=TokenUtil.getToken();
        System.out.println(token.get("access_token"));
        System.out.println(token.get("expires_in"));
    }
    
    
    @Test
    public void testSaveToken4() {
        Token token=CommonUtil.getToken("wxbfa48c1bbf21efa0", "4535178da3cb77c5036a990e577ccc1a");
        TokenUtil.saveToken(token);
    }
   
}