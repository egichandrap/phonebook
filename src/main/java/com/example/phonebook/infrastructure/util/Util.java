package com.example.phonebook.infrastructure.util;

import com.example.phonebook.infrastructure.configuration.ConstantConfig;
import com.example.phonebook.infrastructure.mappers.ContactMapper;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.mapstruct.factory.Mappers;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

public class Util {

    public static final Logger tdrLogger = LogManager.getLogger("tdr");
    public static final Logger debugLogger = LogManager.getLogger("debugger");

    public static final ContactMapper INSTANCE_MAPPER = Mappers.getMapper(ContactMapper.class);

    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isNotEmptyOrNull(Object obj) {
        if (obj == null) return false;
        if (obj instanceof String)
            return !((String) obj).isEmpty();
        else if (obj instanceof Collection)
            return !((Collection<?>) obj).isEmpty();
        return true;
    }

    public static boolean isEmptyOrNull(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String)
            return ((String) obj).isEmpty();
        else if (obj instanceof Collection)
            return ((Collection<?>) obj).isEmpty();
        return false;
    }

    public static Throwable lastThrowable(@NonNull Throwable throwable) {
        Throwable result = throwable;

        while ((throwable = throwable.getCause()) != null) {
            result = throwable;
        }

        return result;
    }

    public static String formatDateStr(Date date) {
        if (Util.isNotEmptyOrNull(date)){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(date);
        }
        return null;
    }

    public static boolean isPhoneNumber(String data) {
        // Regex pattern to check if the string is a phone number (digits only)
        Pattern pattern = Pattern.compile("\\d+");
        return pattern.matcher(data).matches();
    }
}
