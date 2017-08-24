package com.izv.myandroidserver;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.izv.myandroidserver.Server.StartStopMyServer;
import com.izv.myserverandroid.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView tvIp;
	private Switch swWifi;

	private int port = 8888;
	private StartStopMyServer startStopMyServer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inicio();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		startStopMyServer.stop();
		super.onPause();
	};

	@Override
	protected void onRestart() {
		actionServer();
		super.onRestart();
	}

	private void inicio() {

		tvIp = (TextView) findViewById(R.id.tvIp);
		swWifi = (Switch) findViewById(R.id.swWifi);
		swWifi.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				actionServer();
			}
		});

		startStopMyServer = new StartStopMyServer(this, port);

	}

	public void wifi(View v) {

		swWifi.setChecked(!swWifi.isChecked());

		actionServer();

	}

	private void actionServer() {

		if (isWifi()) {

			if (swWifi.isChecked()) {

				startServer();

			} else {

				stopServer();

			}

		} else {
			swWifi.setChecked(false);
			Toast.makeText(this, getResources().getString(R.string.no_wifi),
					Toast.LENGTH_SHORT).show();
		}

	}

	private void startServer() {

		startStopMyServer.start();

		tvIp.setText(getRed());

	}

	private void stopServer() {

		startStopMyServer.stop();

		tvIp.setText("");

	}

	public boolean isWifi() {

		ConnectivityManager gesCon = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		if (gesCon == null) {
			return false;
		}

		NetworkInfo redWifi = gesCon
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (redWifi == null) {
			return false;
		}

		return redWifi.getState() == NetworkInfo.State.CONNECTED;

	}

	public String getRed() {

		// Se crea el objeto que gestiona el wifi
		WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
		// Se obtiene la informacion de la informacion
		WifiInfo wifiInfo = wm.getConnectionInfo();

		// getIpAddress devuelve un entero, hay que obtener la ip
		int ipInt = wifiInfo.getIpAddress();

		// Se guardara en el array ipAddress los valores de la ip
		byte[] ipAddress = BigInteger.valueOf(ipInt).toByteArray();

		try {

			// Se obtiene la direccion de la red a traves del array de byte que
			// tiene la ip
			InetAddress miDireccion = InetAddress.getByAddress(ipAddress);
			// Se devuelve la ip que nos devuelve el metodo getHostAddress, pero
			// la
			// devuelve al reves --> 10.1.168.192
			String miIp = miDireccion.getHostAddress();

			int pos1 = miIp.indexOf(".");
			int pos2 = miIp.indexOf(".", pos1 + 1);

			return "192.168.1." + miIp.substring(pos1 + 1, pos2) + ""
					+ miIp.substring(1, pos1) + ":" + port;

		} catch (UnknownHostException e) {
			return "";
		}

	}

}
