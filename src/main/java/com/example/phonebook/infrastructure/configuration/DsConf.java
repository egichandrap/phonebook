package com.example.phonebook.infrastructure.configuration;

import com.example.phonebook.infrastructure.util.Util;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class DsConf {
   @Setter
   private static Connection queueConn;
   @Setter
   private static Connection pesConn;

   public static DataSource dbConfPes() {
      return ConnectionDb.getInstance().getDataSourceMySql();
   }
   public static Connection dbConnPes(){
      try {
         if (Util.isEmptyOrNull(pesConn)){
            DataSource dataSource = dbConfPes();
            pesConn = dataSource.getConnection();
         }
      } catch (Exception e) {
         Util.debugLogger.error("Failed Created MySql Pes Connection {}, caused by {}",e.getMessage(),e.getMessage());
      }
      return pesConn;
   }
}
