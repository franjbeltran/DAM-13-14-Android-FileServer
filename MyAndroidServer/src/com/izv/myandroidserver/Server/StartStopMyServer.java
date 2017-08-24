package com.izv.myandroidserver.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;

public class StartStopMyServer {

	private ServerSocket service;
	private boolean started = false;
	private int port;
	private Context context;

	public StartStopMyServer(Context context, int port) {
		this.context = context;
		this.port = port;
	}

	public void start() {

		if (started)
			return;

		started = true;

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					service = new ServerSocket(port);

					while (started) {
						new MyServer(context, service.accept()).start();
					}

					service.close();

				} catch (IOException e) {
			
				}

			}

		}).start();

	}

	public void stop() {

		started = false;

		// Hay que lanzar una peticion para que pueda parar
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					new Socket("localhost", port);
				} catch (UnknownHostException e) {
					
				} catch (IOException e) {
					
				}

			}
		}).start();

	}

}
