package com.codingbucket.laptopcontrol;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.codingbucket.laptopcontrol.URLHandler.printLog;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://192.168.0.104:3000","https://localhost:3000","https://192.168.0.104:3000"})
public class LaptopControls {
//    private static String location="E:\\Personal Projects\\remotecontrol";
    private static String volumeFileName="SetVol.exe";
    private static String sleepFileName="psshutdown.exe";

    @GetMapping(path="/laptopcontrol/{value}")
    public static String executeCommands(@PathVariable String value){
        //runs the command here and returns the response
        switch (value){
            case "mute":
                //mute the system
                printLog("Mute System Called");
                executeRuntimeCommand(volumeFileName+" mute");
                break;
            case "unmute":
                //unmutes the system
                printLog("Unmute System Called");
                executeRuntimeCommand(volumeFileName+" unmute");
                break;
            case "increase":
                //increase the system volume
                printLog("Increase Volume Called");
                executeRuntimeCommand(volumeFileName+" +20");
                break;
            case "decrease":
                //decrease the system volume
                printLog("Decrease Volume Called");
                executeRuntimeCommand(volumeFileName+" -20");
                break;
            case "sleep":
                //puts the system to sleep
                printLog("Sleep Called");
                executeRuntimeCommand(sleepFileName+"  -d -t 0 -accepteula");
                break;
            case "lock":
                //puts the system to sleep
                printLog("Lock Called");
                executeRuntimeCommand("rundll32.exe user32.dll,LockWorkStation");
                break;
            case "incomingCall":
                //puts the system to sleep
                printLog("IncomingCall Called");
                if(isCurrentlyMuted()){
                    return "Already mute";
                }
                printLog("Mute System Called");
                executeRuntimeCommand(volumeFileName+" mute");
                new Thread(()-> {
                    JOptionPane optionPane = new JOptionPane("Call on progress Boss!! Muting system", JOptionPane.INFORMATION_MESSAGE);
                    JDialog dialog = optionPane.createDialog("Coding Bucket");
                    new Thread(()->{
                        try {
                            Thread.sleep(3000);
                            dialog.setAlwaysOnTop(false); // to show top of all other application
                            dialog.setVisible(false); // to visible the dialog
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    dialog.setAlwaysOnTop(true); // to show top of all other application
                    dialog.setVisible(true); // to visible the dialog
                }).start();
                break;
            case "incomingCallDisconnected":
                //puts the system to sleep
                printLog("IncomingCall Disconnected Called");
                printLog("UnMute System Called");
                executeRuntimeCommand(volumeFileName+" unmute");
                new Thread(()->{
                    JOptionPane optionPane2 = new JOptionPane("Yay!! Call disconnected. Un-muting System.",JOptionPane.INFORMATION_MESSAGE);
                    JDialog dialog2 = optionPane2.createDialog("Coding Bucket");
                    dialog2.setAlwaysOnTop(true); // to show top of all other application
                    dialog2.setVisible(false); // to visible the dialog
                    new Thread(()->{
                        try {
                            Thread.sleep(3000);
                            dialog2.setAlwaysOnTop(false); // to show top of all other application
                            dialog2.setVisible(false); // to visible the dialog
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    dialog2.setVisible(true); // to visible the dialog
                }).start();

                break;
            default:
                return "Wrong Selection";
        }
        return "success";
    }
    private static boolean executeRuntimeCommand(String command){
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            printLog("Exception while executing command "+command+" Ex: "+e.getLocalizedMessage());
            return false;
        }
        return true;
    }
    private static Boolean isCurrentlyMuted(){
        try {
            Process resp = Runtime.getRuntime().exec(volumeFileName+" report");
            BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getInputStream()));
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line;
            }
            rd.close();
            System.out.println(content);
            if(content.contains("Muted")){
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            printLog("Exception while executing command "+volumeFileName+" report"+" Ex: "+e.getLocalizedMessage());
            return false;
        }
    }
}
//https://drive.google.com/file/d/1xOsYJvklmN9Ob-uW1Tuzy6mBzuFYdi7t/view?usp=sharing