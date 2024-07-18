package com.example.phonebook.infrastructure.repository.nonSpring.repository;

import com.example.phonebook.infrastructure.configuration.DsConf;
import com.example.phonebook.infrastructure.entity.Contact;
import com.example.phonebook.infrastructure.exception.ContactException;
import com.example.phonebook.infrastructure.repository.nonSpring.DsC;
import com.example.phonebook.infrastructure.util.ContactHttpStatus;
import com.example.phonebook.infrastructure.util.Util;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class ContactRepository extends DsC {

    public int saveContact(Contact contact) throws ContactException {
        String query = "INSERT INTO Contact (name, phone_number, email, created_data, last_update) VALUES(?, ?, ?, ?, ?)";
        int result = 0;
        try (Connection cUpdateAndInsert = DsConf.dbConfPes().getConnection();
             PreparedStatement pUpdateAndInsert = cUpdateAndInsert.prepareStatement(query)) {
            pUpdateAndInsert.setString(1, contact.getName());
            pUpdateAndInsert.setString(2, contact.getPhone_number());
            pUpdateAndInsert.setString(3, contact.getEmail());
            pUpdateAndInsert.setString(4, Util.formatDateStr(contact.getCreatedDate()));
            pUpdateAndInsert.setString(5, Util.formatDateStr(contact.getLastUpdate()));
            result = executeUpdateOrInsert(pUpdateAndInsert, query);
        } catch (Exception e) {
            clearMySqlConn();
            Util.debugLogger.debug("Save By Sharding Error {}, caused by {}", e.getMessage(), e.getCause());
            throw new ContactException(ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    public Contact findAllByParam(String data) throws ContactException {
        String query;
        boolean isPhoneNumber = Util.isPhoneNumber(data);
        if (isPhoneNumber) {
            query = "SELECT * FROM Contact WHERE phone_number = ?";
        } else {
            query = "SELECT * FROM Contact WHERE name = ?";
        }
        ResultSet rPTFindAllByParam = null;
        Contact contact = null;
        try (Connection cCtFindAllByParam = DsConf.dbConfPes().getConnection();
             PreparedStatement pPtFindAllByParam = cCtFindAllByParam.prepareStatement(query)) {
            pPtFindAllByParam.setString(1, data);
            rPTFindAllByParam = executeGet(pPtFindAllByParam, query);
            if (rPTFindAllByParam.next()) {
                contact = setResultSetToContact(rPTFindAllByParam);
            }
        } catch (Exception e) {
            closeDBConnection(rPTFindAllByParam);
            clearMySqlConn();
            Util.debugLogger.debug("Find Data With {} Error {}, caused by {}", data, e.getMessage(), e.getCause());
            throw new ContactException(ContactHttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            closeDBConnection(rPTFindAllByParam);
        }
        return contact;
    }

    public int updateDataPhoneBook(Contact contact) throws ContactException {
        boolean isFirst = true;
        int result = 0;
        StringBuilder queryBuilder = new StringBuilder("UPDATE Contact SET ");
        if (contact.getName() != null) {
            queryBuilder.append("name = ?");
            isFirst = false;
        }
        if (contact.getPhone_number() != null) {
            if (!isFirst) queryBuilder.append(", ");
            queryBuilder.append("phone_number = ?");
            isFirst = false;
        }
        if (contact.getEmail() != null) {
            if (!isFirst) queryBuilder.append(", ");
            queryBuilder.append("email = ?");
        }
        queryBuilder.append(", last_update = ?");
        queryBuilder.append(" WHERE Id = ?");
        String query = queryBuilder.toString();

        try (Connection cUpdateData = DsConf.dbConfPes().getConnection();
             PreparedStatement pUpdateData = cUpdateData.prepareStatement(query)) {
            int parameterIndex = 1;

            if (contact.getName() != null) {
                pUpdateData.setString(parameterIndex++, contact.getName());
            }
            if (contact.getPhone_number() != null) {
                pUpdateData.setString(parameterIndex++, contact.getPhone_number());
            }
            if (contact.getEmail() != null) {
                pUpdateData.setString(parameterIndex++, contact.getEmail());
            }
            pUpdateData.setString(parameterIndex++, Util.formatDateStr(contact.getLastUpdate()));
            pUpdateData.setInt(parameterIndex, contact.getId());
            result = executeUpdateOrInsert(pUpdateData, query);
        } catch (Exception e) {
            clearMySqlConn();
            Util.debugLogger.debug("{} Error {}, caused by {}", "Update Invalid", e.getMessage(), e.getCause());
            throw new ContactException(ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    public int deleteContactById(int contactId) throws ContactException {
        String query = "DELETE FROM Contact WHERE Id = ?";
        int result = 0;
        try (Connection connection = DsConf.dbConfPes().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, contactId);
            result = executeUpdateOrInsert(preparedStatement, query);
        } catch (Exception e) {
            clearMySqlConn();
            Util.debugLogger.debug("Delete Contact Error {}, caused by {}", e.getMessage(), e.getCause());
            throw new ContactException(ContactHttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    public Contact setResultSetToContact(ResultSet resultSet) throws Exception {
        Contact contact = new Contact();
        contact.setId(resultSet.getInt("id"));
        contact.setName(resultSet.getString("name"));
        contact.setPhone_number(resultSet.getString("phone_number"));
        return contact;
    }
}
