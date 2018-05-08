package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import database.userAccount;
 
public class DBUtils {
 
  public static userAccount findUser(Connection conn, String userName, String password) throws SQLException {
 
      String sql = "Select a.email, a.Password from User_Account a "
              + " where a.email = ? and a.password= ?";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setString(1, userName);
      pstm.setString(2, password);
      ResultSet rs = pstm.executeQuery();
 
      if (rs.next()) {
          userAccount user = new userAccount();
          user.setUserName(userName);
          user.setPassword(password);
          return user;
      }
      return null;
  }
 
  public static userAccount findUser(Connection conn, String userName) throws SQLException {
 
      String sql = "Select a.email from User_Account a " + " where a.User_Name = ? ";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setString(1, userName);
 
      ResultSet rs = pstm.executeQuery();
 
      if (rs.next()) {
          String password = rs.getString("Password");
          userAccount user = new userAccount();
          user.setUserName(userName);
          user.setPassword(password);
          return user;
      }
      return null;
  }
}