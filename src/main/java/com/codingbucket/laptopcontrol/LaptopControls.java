package com.codingbucket.laptopcontrol;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://192.168.0.104:3000","https://localhost:3000","https://192.168.0.104:3000"})
public class LaptopControls {
    private static String location="E:\\Personal Projects\\remotecontrol";
    private static String volumeFileName="SetVol.exe";
    private static String sleepFileName="psshutdown.exe";

    @GetMapping(path="/laptopcontrol/{value}")
    public static String executeCommands(@PathVariable String value){
        //runs the command here and returns the response
        switch (value){
            case "mute":
                //mute the system
                executeRuntimeCommand(location+ File.separator+volumeFileName+" mute");
                break;
            case "unmute":
                //unmutes the system
                executeRuntimeCommand(location+ File.separator+volumeFileName+" unmute");
                break;
            case "increase":
                //increase the system volume
                executeRuntimeCommand(location+ File.separator+volumeFileName+" +20");
                break;
            case "decrease":
                //decrease the system volume
                executeRuntimeCommand(location+ File.separator+volumeFileName+" -20");
                break;
            case "sleep":
                //puts the system to sleep
                executeRuntimeCommand(location+ File.separator+sleepFileName+"  -d -t 0 -accepteula");
                break;
            case "lock":
                //puts the system to sleep
                executeRuntimeCommand("rundll32.exe user32.dll,LockWorkStation");
                break;
        }
        return "success";
    }
    private static boolean executeRuntimeCommand(String command){
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
//https://drive.google.com/file/d/1xOsYJvklmN9Ob-uW1Tuzy6mBzuFYdi7t/view?usp=sharing