package ubi.naist.cocorobo_hack;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import ubi.naist.cocorobo_hack.json_message.APIConfig;
import ubi.naist.cocorobo_hack.json_message.WebAPIResponse;

/**
 * Created by naoki-ma on 2015/11/10.
 */
public class WebAPIClient extends AsyncTask<HashMap<String , String>, Void , WebAPIResponse>{
	private	APIConfig	apiConfig;
	private	Context		context;
	private Gson		gson;

	private	String		url;

	public WebAPIClient(Context context, APIConfig config , String mode){
		this.context	= context;
		this.apiConfig	= config;

		switch (mode){
			case "Authentication" :
				url = config.AuthenticationURL;
				break;
			case "Speech" :
				url = config.SpeechURL;
				break;
			default:
				break;
		}
	}

	@Override
	protected WebAPIResponse doInBackground(HashMap<String , String>... data) {
		WebAPIResponse response = null;
		gson = new Gson();
		try {
			JSONObject json = new JSONObject();

			for(Map.Entry<String , String> d : data[0].entrySet()){
				json.put(d.getKey() , d.getValue());
			}

			String jsonParam = json.toString();
			URLConnection connection = new URL(url).openConnection();
			HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
			httpsConnection.setRequestProperty("Content-Type", "application/json");
			httpsConnection.setRequestMethod("POST");
			httpsConnection.setDoOutput(true);
			PrintStream ps = new PrintStream(httpsConnection.getOutputStream());
			ps.print(jsonParam);
			ps.close();
			//エラー処理
			if (httpsConnection.getResponseCode() == 200) {
				InputStream inputStream = httpsConnection.getInputStream();
				JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
				response = gson.fromJson(jsonReader, WebAPIResponse.class);
				inputStream.close();
			} else {
				return null;
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	protected void	onPostExecute(WebAPIResponse response){
		if(response == null)	return;
		String message = null;
		switch (Integer.parseInt(response.resultCode)){
			case 0 :
				//message = response.data.expireDate;
				message = "成功";
				break;
			case 1 :
				message = response.errorCode;
				break;
			default:
				message = "謎のエラー";
				break;
		}
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

}
