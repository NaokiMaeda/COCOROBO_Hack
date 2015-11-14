package ubi.naist.cocorobo_hack;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by lucky on 15/11/15.
 */
public class BluetoothServerThread extends Thread{
	private	final	BluetoothServerSocket serverSocket;
	private	final 	UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	private Context				context;
	private	BluetoothAdapter	bluetoothAdapter;
	private String				myNum;

	public BluetoothServerThread(Context context , String myNum , BluetoothAdapter bluetoothAdapter){
		this.context			= context;
		this.myNum				= myNum;
		this.bluetoothAdapter	= bluetoothAdapter;

		BluetoothServerSocket tmp = null;
		try {
			tmp = this.bluetoothAdapter.listenUsingRfcommWithServiceRecord("COCOROBO_Hack" , uuid);
		}catch (IOException e){
			e.printStackTrace();
		}
		serverSocket = tmp;
	}

	@Override
	public void run(){
		BluetoothSocket receivedSocket = null;
		NoticeThread notice = null;

		while(true){
			try {
				receivedSocket = serverSocket.accept();
			} catch (IOException e) {
				break;
			}

			if(receivedSocket != null){
				notice = new NoticeThread(context , receivedSocket);
				notice.start();
				try {
					serverSocket.close();
				}catch (IOException e){
					e.printStackTrace();
				}
				break;
			}
		}
	}

	public void cancel(){
		try {
			serverSocket.close();
		}catch (IOException e){}
	}

}
