package ubi.naist.cocorobo_hack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import jp.co.sharp.openapi.cocorobo.CocoroboApi;
import ubi.naist.cocorobo_hack.eventlistener.DriverEventListener;
import ubi.naist.cocorobo_hack.eventlistener.SpeechEventListener;
import ubi.naist.cocorobo_hack.eventlistener.StopEventListener;
import ubi.naist.cocorobo_hack.json_message.APIConfig;

public class MainActivity extends Activity {
	private final	String			APIConfigFile = "APIConfig.json";

	private			APIConfig					config;
	private			CocoroboApi					cocoroboApi;
	private			WebAPIClient				webAPIClient;
	private 		HashMap<String , String>	webAPIData;

	private 		ToggleButton	driveButton;
	private			ToggleButton	rightTurnButton;
	private 		ToggleButton	leftTurnButton;
	private			ToggleButton	homeButton;
	private			Button 			stopButton;
	private 		Button			speechButton;
	private 		EditText		speechText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		cocoroboApi		= new CocoroboApi(getApplicationContext());
		importAPIInfo(APIConfigFile);

		driveButton		= (ToggleButton)findViewById(R.id.driveButton);
		rightTurnButton = (ToggleButton)findViewById(R.id.rightTurnButton);
		leftTurnButton	= (ToggleButton)findViewById(R.id.leftTurnButton);
		homeButton		= (ToggleButton)findViewById(R.id.homeButton);
		stopButton		= (Button)findViewById(R.id.stopButton);
		speechButton	= (Button)findViewById(R.id.speechButton);
		speechText		= (EditText)findViewById(R.id.speechText);

		driveButton.setOnCheckedChangeListener(new DriverEventListener(config.APIKey , cocoroboApi));
		rightTurnButton.setOnCheckedChangeListener(new DriverEventListener(config.APIKey , cocoroboApi));
		leftTurnButton.setOnCheckedChangeListener(new DriverEventListener(config.APIKey , cocoroboApi));
		homeButton.setOnCheckedChangeListener(new DriverEventListener(config.APIKey , cocoroboApi));
		stopButton.setOnClickListener(new StopEventListener(config.APIKey, cocoroboApi));
		speechButton.setOnClickListener(new SpeechEventListener(getApplicationContext() , config , speechText));
	}

	@Override
	protected void onResume(){
		super.onResume();
		webAPIClient = new WebAPIClient(getApplicationContext() , config , "Authentication");
		webAPIData = new HashMap<>();
		webAPIData.put("apikey_cocorobo" , config.APIKey);
		webAPIClient.execute(webAPIData);
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(cocoroboApi != null)	cocoroboApi.destroy();
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
