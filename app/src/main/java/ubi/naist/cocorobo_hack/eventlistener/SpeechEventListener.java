package ubi.naist.cocorobo_hack.eventlistener;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import ubi.naist.cocorobo_hack.WebAPIClient;
import ubi.naist.cocorobo_hack.json_message.APIConfig;

/**
 * Created by naoki-ma on 2015/11/12.
 */
public class SpeechEventListener implements View.OnClickListener{
	private APIConfig					config;
	private Context						context;
	private EditText					speechText;
	private WebAPIClient				webAPIClient;
	private HashMap<String , String>	speechData;

	public SpeechEventListener(Context context , APIConfig config , EditText speechText){
		this.config = config;
		this.context = context;
		this.speechText = speechText;
	}

	@Override
	public void onClick(View view) {
		String msg = speechText.getText().toString();
		speechData = new HashMap<>();
		speechData.put("apikey_cocorobo" , config.APIKey);
		speechData.put("message", msg);
		webAPIClient = new WebAPIClient(context , config , "Speech");
		webAPIClient.execute(speechData);
		speechText.setText("");

		if(msg.length() > 0 && msg.length() <= 255){

		}
	}
}
