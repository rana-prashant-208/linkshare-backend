package com.codingbucket.laptopcontrol;

import org.springframework.web.bind.annotation.*;
import sun.plugin2.os.windows.Windows;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://192.168.0.104:3000","https://localhost:3000","https://192.168.0.104:3000"})
public class URLHandler {
    @GetMapping(path="/openurl/{url}")
    public Boolean handleURL(@PathVariable String url){
        try {
            if(Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                try {
                    System.out.println("there");
                    url=decryptURL(url);
                    System.out.println("dec "+url);
                    setClipboard(url);
                    desktop.browse(new URI(url));
                } catch (IOException | URISyntaxException e) {
                    System.out.println("Exception while opening "+e.getMessage());
                }
            }else{
                Runtime runtime = Runtime.getRuntime();
                try {
                    System.out.println("here");
                    url=decryptURL(url);
                    System.out.println("dec "+url);
                    setClipboard(url);
                    runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Exception while opening "+e.getMessage());
        }
        return true;
    }

    private void setClipboard(String url) {
        try {
            StringSelection stringSelection = new StringSelection(url);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String decryptURL(String urlBase){
        StringBuilder str=new StringBuilder();
        String[] splits = urlBase.replace("[", "").replace("]", "").replace(" ","").split(",");
        for (String character:splits
        ) {
            str.append((char)(Integer.parseInt(character)));
        }
        return str.toString();
    }

}
