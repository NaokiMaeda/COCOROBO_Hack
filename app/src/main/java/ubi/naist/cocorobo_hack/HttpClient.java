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

import javax.net.ssl.HttpsURLConnection;

import ubi.naist.cocorobo_hack.json_message.APIConfig;
import ubi.naist.cocorobo_hack.json_message.AuthenticationResponse;

/**
 * Created by naoki-ma on 2015/11/10.
 */
public class HttpClient extends AsyncTask<Object , Void , AuthenticationResponse>{
	private	APIConfig	apiConfig;
	private	Context		context;
	private Gson		gson;

	public HttpClient(Context context , APIConfig config){
		this.context	= context;
		this.apiConfig	= config;
	}

	@Override
	protected AuthenticationResponse doInBackground(Object[] objects) {
		AuthenticationResponse response = null;
		gson = new Gson();
		try {
			JSONObject json = new JSONObject();
			json.put("apikey_cocorobo", apiConfig.APIKey);
			String jsonParam = json.toString();

			URLConnection connection = new URL(apiConfig.AuthenticationURL).openConnection();
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
				response = gson.fromJson(jsonReader, AuthenticationResponse.class);
				inputStream.close();
			} else {
				return null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	protected void	onPostExecute(AuthenticationResponse response){
		if(response == null)	return;
		if(Integer.parseInt(response.resultCode) == 0){
			Toast.makeText(context , response.data.expireData , Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(context , response.errorCode , Toast.LENGTH_SHORT).show();
		}
	}

}
