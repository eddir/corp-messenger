package com.example.backend.controller;


import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api/echo"})
public class HelloWorld
{
    List<String> messages = new ArrayList<String>(){
        {
            add(new String("String1"));
            add(new String("String2"));
            add(new String("String3"));
        }
    };

    @PostMapping
    public String echo(@RequestBody Map<String,String> date)
    {
        String value = new String(date.get("date"));
        messages.add(value);
        return value;
    }

    @GetMapping
    public List<String> list()
    {
        return messages;
    }

    @GetMapping(value = {"/{id}"})
    public String getById(@PathVariable(value = "id") String id)
    {
        return messages.get(Integer.parseInt(id));
    }

    @DeleteMapping(value = {"/{id}"})
    public String delete(@PathVariable(value = "id") String id)
    {
        return messages.remove(Integer.parseInt(id));
    }

}
