package com.kmecpp.osmium.api.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;

public class WebUtil {

	public static JsonValue post(URL url, JsonValue json) throws IOException {
		HttpURLConnection connection = getConnection(url);
		postFast(connection, json.toString().getBytes());
		return Json.parse(new InputStreamReader(connection.getInputStream()));
	}

	public static String post(URL url, byte[] bytes) throws IOException {
		HttpURLConnection connection = getConnection(url);
		postFast(connection, bytes);
		return IOUtil.readString(connection.getInputStream());
	}

	public static void postFast(HttpURLConnection connection, byte[] bytes) throws IOException {
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		try (OutputStream os = connection.getOutputStream()) {
			os.write(bytes);
			os.flush();
		}
	}

	public static HttpURLConnection getConnection(URL url) throws IOException {
		return getHttpConnection(url, 3000, 3000);
	}

	/**
	 * Gets an {@link HttpURLConnection} for the given URL with the given
	 * connection timeout and read timeout. This method also creates the
	 * connection with a default User-Agent of Mozilla/5.0 to avoid being
	 * filtered by many sites.
	 * 
	 * @param url
	 *            the URL to connect to
	 * @param connectTimeout
	 *            the timeout to use when opening a communications link to the
	 *            resource referenced by this URLConnection
	 * @param readTimeout
	 *            the timeout to use when reading from an input stream when a
	 *            connection is established to a resource
	 * @return the HTTP URL connection
	 * @throws IOException
	 *             if an IOException occurs
	 */
	public static HttpURLConnection getHttpConnection(URL url, int connectTimeout, int readTimeout) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.setConnectTimeout(connectTimeout);
		connection.setReadTimeout(readTimeout);
		return connection;
	}

}
