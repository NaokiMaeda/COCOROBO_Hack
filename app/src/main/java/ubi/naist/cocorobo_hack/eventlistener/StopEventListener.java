package ubi.naist.cocorobo_hack.eventlistener;

import android.os.RemoteException;
import android.view.View;

import jp.co.sharp.openapi.cocorobo.CocoroboApi;
import ubi.naist.cocorobo_hack.R;

/**
 * Created by naoki-ma on 2015/11/12.
 */
public class StopEventListener implements View.OnClickListener{
	private	String		apiKey;
	private CocoroboApi	cocoroboApi;

	public StopEventListener(String apiKey , CocoroboApi cocoroboApi){
		this.apiKey = apiKey;
		this.cocoroboApi = cocoroboApi;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.stopButton){
			cocoroboControl("stop");
		}
	}

	private void cocoroboControl(String mode){
		try {
			cocoroboApi.control(apiKey , mode);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
