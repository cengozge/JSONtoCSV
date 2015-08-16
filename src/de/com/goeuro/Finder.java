package de.com.goeuro;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Finder {

	private static final Logger LOGGER = Logger.getLogger(Finder.class.getName());
	private static String city = "";
	
	public static void main(String[] args) throws IOException,JSONException{
		Finder f = new Finder();
		try {
			city = args[0];
			//city = "berlin";
			JSONArray array = Finder.readJsonArrayFromUrl(city);
			Finder.write(array);
		} catch (Exception e ) {
			LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} 
	}
	
	public static JSONArray readJsonArrayFromUrl(String cityName) throws IOException, JSONException {
		URL url = new URL("http://api.goeuro.com/api/v2/position/suggest/en/".concat(cityName));
		JSONArray array = null;
		try {
			Reader bd =  new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
			array = new JSONArray(new JSONTokener(bd));
			return array;
		} catch(IOException e) {
			LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new IOException(e);
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
				LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
			}
		} catch (JSONException e) {
			LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}

	}

}
