package com.codingbucket.laptopcontrol;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import java.net.*;

@SpringBootApplication
public class LaptopcontrolApplication {

	public static void main(String[] args)
	{
		new SpringApplicationBuilder(LaptopcontrolApplication.class).headless(false).run(args);
		new Thread(()-> {
			JOptionPane optionPane = new JOptionPane("Use the following IP in phone: \n "+getLocalhostIP(), JOptionPane.INFORMATION_MESSAGE);
			JDialog dialog = optionPane.createDialog("Coding Bucket");
			dialog.setAlwaysOnTop(true); // to show top of all other application
			dialog.setVisible(true); // to visible the dialog
		}).start();
	}
	public static String getLocalhostIP() {
		String address = null;
		try {
			try (final DatagramSocket socket = new DatagramSocket()) {
				InetAddress addressName = InetAddress.getByName("8." + "8.8.8");
				socket.connect(addressName, 10002);
				InetAddress inetAddress = socket.getLocalAddress();
				if (inetAddress instanceof Inet4Address) {
					address = inetAddress.getHostAddress();
				}else{
					address = java.net.InetAddress.getLocalHost().getHostAddress();
				}
			} catch (SocketException e) {
				e.printStackTrace();
				if (address == null) {
					address = java.net.InetAddress.getLocalHost().getHostAddress();
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return address;
	}

}
