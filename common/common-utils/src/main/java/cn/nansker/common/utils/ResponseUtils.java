package cn.nansker.common.utils;

import cn.nansker.common.utils.result.ResultData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Nansker
 * @date: 2023/10/24 23:50
 * @description: 响应信息工具类
 */
public class ResponseUtils {

    public static void out(HttpServletResponse response, ResultData result) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8");
        try {
            mapper.writeValue(response.getWriter(), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
