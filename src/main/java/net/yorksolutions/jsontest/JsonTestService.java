package net.yorksolutions.jsontest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@Service
public class JsonTestService {
    public HashMap<String, String> getIp(HttpServletRequest request) {
        HashMap<String, String> ipMap = new HashMap();
        ipMap.put("ip", request.getRemoteAddr());
        return ipMap;
    }

    public HashMap<String, String> getHeaders(HashMap<String, String> request) {
        return request;
    }

    public HashMap getDateAndTime() {
        HashMap timeAndDate = new HashMap<String, String>();
        Date time = new Date();
        timeAndDate.put("time", time.toString().split(" ")[3] + ' ' + time.toString().split(" ")[4]);
        timeAndDate.put("milliseconds_since_epoch", time.getTime());
        timeAndDate.put("date", time.toString().split(" ")[0] + ' ' + time.toString().split(" ")[1] + ' ' + time.toString().split(" ")[2] + ", " + time.toString().split(" ")[5]);

        return timeAndDate;
    }

    public HashMap<String, String> getEcho(String path) {
        String[] values = path.split("/");
        HashMap<String, String> echoMap = new HashMap<>();
        for (int i = 2; i < values.length; i += 2) {
            if (i + 1 >= values.length) echoMap.put(values[i], "");
            else echoMap.put(values[i], values[i + 1]);
        }

        return echoMap;
    }

    public HashMap validate(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            //TODO add Array functionality & nano secs
            HashMap map = new HashMap<>();
            if (json.startsWith("[")) map.put("object_or_array", "array");
            else map.put("object_or_array", "object");
            if (jsonObject.isEmpty()) map.put("empty", true);
            else map.put("empty", false);
            map.put("size", jsonObject.length());
            map.put("validate", true);
            return map;
        } catch (JSONException e) {
            HashMap map = new HashMap<>();
            map.put("error", e.toString());
            if (json.startsWith("[")) map.put("object_or_array", "array");
            else map.put("object_or_array", "object");
            map.put("error_info", "This error came from the org.json reference parser.");
            map.put("validate", false);
            return map;
        }
    }

    public HashMap<String, String> setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("json-cookie-test", Instant.now().toString());
        response.addCookie(cookie);

        HashMap map = new HashMap<String, String>();
        map.put("cookie: ", "just added a cookie :)");

        return map;
    }

    public HashMap<String, String> toMd5(String text) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(text.getBytes());

        HashMap<String, String> map = new HashMap<>();
        map.put("original: ", text);
        map.put("md5: ", DatatypeConverter.printHexBinary(md5.digest()).toLowerCase());


        return map;
    }
}
