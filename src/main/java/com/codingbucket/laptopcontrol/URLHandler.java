package com.codingbucket.laptopcontrol;

import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class URLHandler {
    @GetMapping(path="/openurl/{url}")
    public Boolean handleURL(@PathVariable String url){
        try {
            if(Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                try {
                    url=decryptURL(url);
                    printLog("Decrypted URL "+url);
                    printLog("Opening");
                    desktop.browse(new URI(url));
                    printLog("Copying to clipboard ");
                    setClipboard(url);
                } catch (IOException | URISyntaxException e) {
                    printLog("Exception while opening "+e.getMessage());
                }
            }
        } catch (Exception e) {
            printLog("Exception while opening "+e.getMessage());
        }
        return true;
    }

    @GetMapping(path="/retrieveurl/")
    public String retrieve(){
        try {
            printLog("Going to retrieve clipboard data");
            return getClipboardText();
        } catch (Exception e) {
            printLog("Exception while sending "+e.getMessage());
        }
        return "";
    }

    private String getClipboardText() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            String data = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
            printLog("Sending data: "+data);
            return encryptData(data);
        } catch (UnsupportedFlavorException e) {
            printLog("UnsupportedFlavorException while reading Clipboard "+e.getMessage());
        } catch (IOException e) {
            printLog("IOException while reading Clipboard "+e.getMessage());
        }
        return "";
    }

    public static void printLog(String s) {
        System.out.println(new java.util.Date()+" "+s);
    }

    private String encryptData(String data) {
        List<String> arr = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            arr.add(String.valueOf((int) data.charAt(i)));
        }
        return arr.toString().replace("[","").replace("]","").replace(" ","");
    }

    private void setClipboard(String url) {
        try {
            StringSelection stringSelection = new StringSelection(url);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }catch (Exception e){
            printLog("Exception while setting Clipboard "+e.getMessage());
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
