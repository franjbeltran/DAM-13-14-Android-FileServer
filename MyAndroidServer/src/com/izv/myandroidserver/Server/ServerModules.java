package com.izv.myandroidserver.Server;

import java.util.ArrayList;

import MiFecha.MiFecha;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.izv.myandroidserver.pojo.Contact;
import com.izv.myandroidserver.pojo.SMS;
import com.izv.myserverandroid.R;

public class ServerModules {

	private Context context;

	public ServerModules(Context context) {
		this.context = context;
	}

	public String getIndex(String html) {
		return "<html>" + "<head>" + "<title>My Android Server</title>"+
				getStyle()+ "</head>" + "<body> <div id=\"div_main\">" + "<h3>My Android Server - Francisco J. Caracuel</h3>"
				+ "<a href=\"contacts\">Agenda</a>" + "<br/><br/>"
				+ "<a href=\"sms\">SMS</a><br/><br/>" + html + "</div></body>" + "</html>";
	}
	
	public String getStyle(){
		
		return "<style type=\"text/css\"> " +
						"body {" +
							"color: white;" +
							"background-color: #b8e1fc;" +
							"text-align:center; " +
						"}" +
						"a{" +
							"color:white;" +
						"}" +
						"#div_main{" +
							"width: 955px;" +
							"margin:0 auto;" +
							"background-color:#333;" +
							"opacity:0.9;" +
							"border-radius:15px;" +
							"padding: 20px;" +
						"}" +
						"#div_table{" +
							"margin:0 auto;" +
							"width:350px;" +
						"}" +
					"</style>";
		
	}

	public String getContacts() {

		ArrayList<Contact> listContacts = getArrayListContacts();

		String html = "<div id=\"div_table\">" + "<table border='1'>" + "<tr>" + "<th>"
				+ context.getResources().getString(R.string.name) + "</th>"
				+ "<th>" + context.getResources().getString(R.string.number)
				+ "</th>" + "</tr>";

		for (Contact c : listContacts) {

			html = html + "<tr>" + "<td>" + c.getName() + "</td>" + "<td>"
					+ c.getNumber() + "</td>" + "</tr>";

		}

		html = html + "</table>" + "</div>";

		return getIndex(html);

	}

	public String getSms() {

		ArrayList<SMS> listSms = getArrayListSms();

		String html = "<div>" + "<table border='1'>" + "<tr>" + "<th>"
				+ context.getResources().getString(R.string.number) + "</th>"
				+ "<th>" + context.getResources().getString(R.string.date)
				+ "</th>" + "<th>"
				+ context.getResources().getString(R.string.message) + "</th>"
				+ "</tr>";

		for (SMS sms : listSms) {

			html = html + "<tr>" + "<td>" + sms.getNumber() + "</td>" + "<td>"
					+ sms.getDate().getFechaCompletaFormateada() + "</td>"
					+ "<td>" + sms.getMessage() + "</td>" + "</tr>";

		}

		html = html + "</table>" + "</div>";

		return getIndex(html);

	}

	public ArrayList<Contact> getArrayListContacts() {

		Cursor cursor = null;

		ArrayList<Contact> listContacts = new ArrayList<Contact>();

		try {

			cursor = context.getContentResolver().query(Phone.CONTENT_URI,
					null, null, null, null);

			int columnId = cursor.getColumnIndex(Phone._ID);
			int columnName = cursor.getColumnIndex(Phone.DISPLAY_NAME);
			int columnNumber = cursor.getColumnIndex(Phone.NUMBER);

			cursor.moveToFirst();

			do {

				listContacts
						.add(new Contact(cursor.getString(columnId), cursor
								.getString(columnName), cursor
								.getString(columnNumber)));

			} while (cursor.moveToNext());

		} finally {

			if (cursor != null)
				cursor.close();

		}

		return listContacts;

	}

	public ArrayList<SMS> getArrayListSms() {

		ArrayList<SMS> listSms = new ArrayList<SMS>();

		Cursor c = context.getContentResolver().query(
				Uri.parse("content://sms/inbox"), null, null, null, null);

		while (c.moveToNext()) {

			listSms.add(new SMS(c.getString(13), c.getString(12), new MiFecha(c
					.getString(5))));

		}

		c.close();

		return listSms;

	}

}
