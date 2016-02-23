package com.nsxwing.client.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.nsxwing.common.networking.config.KryoNetwork;
import com.nsxwing.common.networking.io.event.ActionEvent;
import com.nsxwing.common.networking.io.response.ActionResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.nsxwing.common.networking.config.KryoNetwork.PORT;
import static com.nsxwing.common.player.PlayerIdentifier.CHAMP;

@Slf4j
public class GameClient {

	Client client;
	String name;

	public GameClient() {
		client = new Client();
		client.start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		KryoNetwork.register(client);

		client.addListener(new Listener() {
			public void connected(Connection connection) {
				client.sendTCP(new ActionEvent());
			}

			public void received(Connection connection, Object object) {
				log.info("Received a thing.");
				if (object instanceof ActionEvent) {
					ActionEvent event = (ActionEvent) object;
					log.info("GOT A SUPER RAD ACTION EVENT FROM KRYO WHATUP THO: " + event.getEventType());
				}
			}
		});

		try {
			client.connect(5000, "localhost", PORT);
			// Server communication after connection can go here, or in Listener#connected().
			client.sendTCP(new ActionResponse(CHAMP));
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

	}
}
