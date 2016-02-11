package com.nsxwing.client.main;

import com.nsxwing.client.networking.GameClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

	public static void main(String[] args) {
		try {
			GameClient gameClient = new GameClient();

			while(true) {
				Thread.sleep(5000);
				log.info("Sleepin'");
			}
		} catch (Exception e) {
			log.debug("Something has gone awry.", e);
		}
	}

}
