package com.shen.httpresult;

import com.alibaba.fastjson.JSONObject;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 供拦截器使用，校验不通过时返回json
 */
@Slf4j
public class HttpResultUtil {

    private HttpResultUtil () {}

    public static void response(HttpServletResponse response, HttpResultCodeEnum codeEnum) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("code", HttpResultCodeEnum.BIZ_ERROR.code());
        json.put("bizCode", codeEnum.code());
        json.put("msg", codeEnum.msg());
        json.put("data", null);
        json.put("timestamp", TimestampUtil.getTimestamp());
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(json.toJSONString());
        } catch (Exception e) {
            log.error("-->Response error: ", e);
        } finally {
            if(printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }
}
