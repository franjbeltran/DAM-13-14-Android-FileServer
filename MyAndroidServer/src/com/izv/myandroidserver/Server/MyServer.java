package com.izv.myandroidserver.Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.StringTokenizer;

import android.content.Context;

public class MyServer extends Thread {

	private Socket socket;
	private BufferedReader in;
	private DataOutputStream out;
	private ServerModules serverModules;

	public MyServer(Context context, Socket socket) {
		this.socket = socket;
		serverModules = new ServerModules(context);
	}

	@Override
	public void run() {

		try {

			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			String petition = in.readLine();

			String archive = "";

			if (petition != null) {

				StringTokenizer token = new StringTokenizer(petition);
				token.nextToken();
				archive = token.nextToken();

				archive = archive.replaceFirst("/", "");
				archive = URLDecoder.decode(archive, "utf-8");

			}

			out = new DataOutputStream(socket.getOutputStream());
			out.writeBytes("HTTP/1.1 200 OK\r\n");
			out.writeBytes("Server: Android Caracuel HTTP Server");
			out.writeBytes("Content-Type: text/html\r\n");

			String response = "";

			if (archive.equals("")) {
				response = serverModules.getIndex("");
			} else if (archive.equals("contacts")) {
				response = serverModules.getContacts();
			} else if (archive.equals("sms")) {
				response = serverModules.getSms();
			}

			out.writeBytes("Content-Length: " + response.length() + "\r\n");
			out.writeBytes("Connection: close\r\n");
			out.writeBytes("\r\n");
			out.writeBytes(response);

			// Se cierran todas las conexiones
			in.close();
			out.flush();
			out.close();
			socket.close();

		} catch (IOException e) {
			// append(tvTexto, "" + e.toString());
		}

	}

}
