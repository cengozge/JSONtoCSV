package de.com.goeuro;
import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Finder {

	private static String city = "";
	public static void main(String[] args) {
		Finder f = new Finder();
		try {
			city = args[0];
			//city = "berlin";
			JSONArray array = Finder.readJsonArrayFromUrl(city);
			Finder.write(array);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public static JSONArray readJsonArrayFromUrl(String cityName) throws IOException, JSONException {
		URL url = new URL("http://api.goeuro.com/api/v2/position/suggest/en/".concat(cityName));
		try {
			Reader bd =  new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
			JSONArray array = new JSONArray(new JSONTokener(bd));
			return array;
		} finally {

		}
	}

	public static void write(JSONArray array){

		File file = new File("C:\\OZGE_GOKAY_DEVTEST\\fromJSON.csv");
		String csv;
		JSONArray newArray = new JSONArray();
		try {
			JSONObject newObj = new JSONObject();
			for (int i = 0; i < array.length(); i++) {
				JSONObject o = array.getJSONObject(i);
				newObj.put("_id", o.get("_id"));
				newObj.put("name", o.get("name"));
				newObj.put("type", o.get("type"));
				newObj.put("latitude", o.getJSONObject("geo_position").get("latitude"));
				newObj.put("longitude", o.getJSONObject("geo_position").get("longitude"));

				newArray.put(newObj);
			}
			csv = CDL.toString(newArray);
			try {
				FileUtils.writeStringToFile(file, csv);
			} catch (IOException e) {
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
