package ubi.naist.cocorobo_hack.eventlistener;

import android.os.RemoteException;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import jp.co.sharp.openapi.cocorobo.CocoroboApi;
import ubi.naist.cocorobo_hack.R;

/**
 * Created by naoki-ma on 2015/11/12.
 */
public class DriverEventListener implements OnCheckedChangeListener{
	private String		apiKey;
	private	CocoroboApi	cocoroboApi;

	public DriverEventListener(String apiKey , CocoroboApi cocoroboApi){
		this.apiKey			= apiKey;
		this.cocoroboApi	= cocoroboApi;
	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
		if (b == false){
			cocoroboControl("stop");
		}else{
			switch (compoundButton.getId()){
				case R.id.driveButton :
					cocoroboControl("forward");
					break;
				case R.id.rightTurnButton :
					cocoroboControl("right");
					break;
				case R.id.leftTurnButton :
					cocoroboControl("left");
					break;
				case R.id.homeButton :
					cocoroboControl("gohome");
					break;
				default:
					cocoroboControl("stop");
					break;
			}
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
