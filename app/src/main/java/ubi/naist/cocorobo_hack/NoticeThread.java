package ubi.naist.cocorobo_hack;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by lucky on 15/11/15.
 */
public class NoticeThread extends Thread{
	private BluetoothSocket bluetoothSocket;
	private Context			context;
	private InputStream		inputStream;

	public NoticeThread(Context context , BluetoothSocket socket){
		this.bluetoothSocket = socket;
		this.context = context;

		try {
			inputStream = bluetoothSocket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run(){
		byte[] buffer = new byte[1024];
		int	bufNum = 0;
		String	receiverData;

		while (true){
			try {
				bufNum = inputStream.read(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(bufNum != 0){
				try {
					receiverData = new String(buffer , "UTF-8");
					Log.d("受信データ", receiverData);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
