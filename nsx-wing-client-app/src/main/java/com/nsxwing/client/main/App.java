package com.nsxwing.client.main;

import com.nsxwing.common.event.GameEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.net.Socket;

@Slf4j
public class App {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 5413);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			while (true) {
				log.info("Currently the input stream has : " + inputStream.available() + " available.");
				GameEvent event = (GameEvent) inputStream.readObject();
				log.info("Received event:" + event);
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			log.debug("Something has gone awry.", e);
		}
	}

}
