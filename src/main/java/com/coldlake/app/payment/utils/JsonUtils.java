package com.coldlake.app.payment.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import static com.coldlake.app.payment.service.payment.LogService.cm;

/**
 * @Author: <a href="https://github.com/zoowayss">zoowayss</a>
 * @Date: 2024/4/12 14:31
 */

@Slf4j
public class JsonUtils {


    private static ObjectMapper objectMapper = SpringUtils.getBean(ObjectMapper.class);


    public static String toJson(Object obj) {
        String cm = "toJson@JsonUtils";
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error(cm + " parse to json failed", e);
            throw new RuntimeException(e);
        }
    }


    public static <T> T toObject(String json, Class<T> clazz) {
        String cm = "toObject@JsonUtils";
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error(cm + " unable to parse json:{}", json);
            throw new RuntimeException(e);
        }
    }

    public static String logJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("{} write to json failed", cm(), e);
        }
        return "";
    }

}
