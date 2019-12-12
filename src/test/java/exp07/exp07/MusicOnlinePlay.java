package exp07.exp07;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class MusicOnlinePlay {
	private static final int SUCCESS = 200;

	public static void musicOnlinePlay(String url, String MD5) {
		HttpClient client = new HttpClient();
		GetMethod get = null;
		FileOutputStream output = null;
		String filename = null;

		try {
			get = new GetMethod(url + MD5);
			int i = client.executeMethod(get);

			if (SUCCESS == i) {
				System.out.println("[The file name getting from HTTP HEADER] " + filename);
				//ServletOutputStream
				
			} else {
				System.out.println("DownLoad file failed with error code: " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			get.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
	}

}
