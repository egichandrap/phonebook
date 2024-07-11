package com.example.phonebook.infrastructure.repository.nonSpring;

import com.example.phonebook.infrastructure.exception.ContactException;
import com.example.phonebook.infrastructure.configuration.ConnectionDb;
import com.example.phonebook.infrastructure.util.ContactHttpStatus;
import com.example.phonebook.infrastructure.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DsC {

    public void closeDBConnection(ResultSet resultSet){
        try {
            if(resultSet != null) resultSet.close();
        } catch (Exception e){
            String msg = "Failed To Close Connection " + e.getMessage();
            Util.debugLogger.error(msg,e);
        }
    }

    public ResultSet executeGet(PreparedStatement preparedStatement, String query) throws ContactException {
        try {
            return preparedStatement.executeQuery();
        } catch (Exception e){
            Util.debugLogger.debug("Failed Execute Query Get {} , {}, caused by {}",query,e.getMessage(),e.getCause());
            Util.debugLogger.fatal("Failed Execute Query Get {} , {}, caused by {}",query,e.getMessage(),e.getCause(),e);
            throw new ContactException(e.getMessage(), ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public int executeUpdateOrInsert(PreparedStatement preparedStatement, String query) throws ContactException {
        try {
            return preparedStatement.executeUpdate();
        } catch (Exception e){
            Util.debugLogger.debug("Failed Execute Update Or Insert Query {} , {}, caused by {}",query,e.getMessage(),e.getCause());
            Util.debugLogger.fatal("Failed Execute Update Or Insert Query {} , {}, caused by {}",query,e.getMessage(),e.getCause(),e);
            throw new ContactException(e.getMessage(), ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void clearMySqlConn(){
        ConnectionDb.setInstance(null);
    }
}
