package net.yorksolutions.jsontest;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@RestController
public class JsonTestController {
    JsonTestService jsonTestService;

    public JsonTestController(JsonTestService jsonTestService) {
        this.jsonTestService = jsonTestService;
    }

    @GetMapping("/ip")
    public HashMap<String, String> ip(HttpServletRequest request) {
        return this.jsonTestService.getIp(request);
    }

    @GetMapping("/headers")
    public HashMap<String, String> headers(@RequestHeader HashMap<String, String> request) {
        return this.jsonTestService.getHeaders(request);
    }

    @GetMapping({"/date", "/time"})
    public HashMap date() {
        return this.jsonTestService.getDateAndTime();
    }

    @GetMapping("/echo/**")
    public HashMap echo(HttpServletRequest path) {
        return this.jsonTestService.getEcho(path.getServletPath());
    }

    @GetMapping("/code")
    public String code() {
        return "alert(\"This can be ran in JS\");";
    }

    @GetMapping("/validate")
    public HashMap validate(@RequestParam String json) {
        return this.jsonTestService.validate(json);
    }
    @GetMapping("/cookie")
    public HashMap<String, String> cookie(HttpServletResponse response) {
        return this.jsonTestService.setCookie(response);
    }

    @GetMapping("/md5")
    public HashMap<String, String> md5(@RequestParam String text) throws NoSuchAlgorithmException {
        return this.jsonTestService.toMd5(text);
    }
}
