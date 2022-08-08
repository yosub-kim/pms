package kr.co.kmac.pms.common.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JsonConvert {
	 /**
     * Json String -> Map
     * 
     * @param dto
     * @return
     */
    public static Map<String, Object> convJsonToMap( String json ) {
        ObjectMapper        objectMapper = new ObjectMapper();
        
        Map<String, Object> map          = null;
        try {
            map = objectMapper.readValue( json, new TypeReference<Map<String, Object>>() {} );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
        
        return map;
    }
    
    /**
     * Json String -> List
     * 
     * @param dto
     * @return
     */
    public static List<Object> convJsonToList( String json ) {
        ObjectMapper objectMapper = new ObjectMapper();
        
        List<Object> list         = null;
        try {
            list = objectMapper.readValue( json, new TypeReference<List<Object>>() {} );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
        
        return list;
    }

    /**
     * Object -> Json String
     * 
     * @param dto
     * @return
     */
    public static String convObjectToJson( Object dto ) {
        ObjectMapper objectMapper = new ObjectMapper();
        
        String       body         = null;
        try {
            body = objectMapper.writeValueAsString( dto );
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
        
        return body;
    }
    
    /**
     * Object -> Json String 예쁘게 찍기
     * 
     * @param dto
     * @return
     */
    public static void toBeauty( Object json ) {
        ObjectMapper objectMapper = new ObjectMapper();
        
        String       jStr         = null;
        try {
            jStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString( json );
        } catch ( JsonProcessingException e ) {
            throw new RuntimeException( e );
        }

    }

}
