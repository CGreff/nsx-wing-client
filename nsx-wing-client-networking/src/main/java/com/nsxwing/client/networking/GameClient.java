package com.nsxwing.client.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.nsxwing.common.networking.config.KryoNetwork;
import com.nsxwing.common.networking.io.event.ConnectionEvent;
import com.nsxwing.common.networking.io.response.ConnectionResponse;
import com.nsxwing.common.player.PlayerIdentifier;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.nsxwing.common.networking.config.KryoNetwork.PORT;

@Slf4j
public class GameClient {

	private Client client;
	private PlayerIdentifier playerIdentifier;

	public GameClient() {
		client = new Client();
		client.start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		KryoNetwork.register(client);

		client.addListener(new Listener() {
			public void connected(Connection connection) {
				client.sendTCP(new ConnectionEvent());
			}

			public void received(Connection connection, Object object) {
				if (object instanceof ConnectionResponse) {
					playerIdentifier = ((ConnectionResponse) object).getPlayerIdentifier();
					log.info("Connection Reciprocated. I am player: " + playerIdentifier);
				}
			}
		});

		try {
			client.connect(5000, "localhost", PORT);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

	}
}
