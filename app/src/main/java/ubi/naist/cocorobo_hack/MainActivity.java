package ubi.naist.cocorobo_hack;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import ubi.naist.cocorobo_hack.json_message.APIConfig;

public class MainActivity extends Activity {
	private			APIConfig		config;
	private final	String			APIConfig = "APIConfig.json";
	private			WebAPIClient	webAPIClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		importAPIInfo(APIConfig);

		webAPIClient = new WebAPIClient(getApplicationContext() , config);
		HashMap<String , String > authentication = new HashMap<>();
		authentication.put("apikey_cocorobo" , config.APIKey);
		webAPIClient.execute(authentication);
	}

	private void importAPIInfo(String configFile){
		try {
			InputStream inputStream = getAssets().open(configFile);
			JsonReader jsonReader	= new JsonReader(new InputStreamReader(inputStream));
			config = new Gson().fromJson(jsonReader, APIConfig.class);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
