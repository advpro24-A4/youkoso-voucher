package id.ac.ui.cs.advprog.youkosoproduct.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
public class HomePageController {
    public record Greeting(String name) {
    }

    @GetMapping(value = "/")
    public Map<String, String> greeting(@RequestParam(value= "name") String name){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello " + name);
        return response;
    }

    @PostMapping("/")
    public Map<String, String> greetingPost(@RequestBody(required = true) Greeting name){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello " + name.name);
        return response;
    }
}
