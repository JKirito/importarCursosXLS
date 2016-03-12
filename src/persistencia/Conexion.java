package persistencia;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Conexion {

	// IP localhost
	// private static final String URL = "http://10.0.2.2/guiame/";
	// ip Agus
	// private static final String URL = "http://181.16.65.149/guiame/";

	// IP hostinger
	private static final String URL = "http://simiungs.esy.es/guiame/altasXLS/";

	public static String getPHPResult(String phpName) {
		HttpClient cliente = new DefaultHttpClient();
		HttpContext contexto = new BasicHttpContext();
		HttpGet httpget = new HttpGet(URL + phpName);
		String resultado = null;
		try {
			HttpResponse response = cliente.execute(httpget, contexto);
			HttpEntity entity = response.getEntity();
			resultado = EntityUtils.toString(entity, "UTF-8");
			System.out.println("RESULTADO" + resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public static String enviarPost(Map<String, String> datos, String phpName) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(URL + phpName);
		System.out.println("url: " + URL + phpName);
		HttpResponse response = null;
		String resultado = "";
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>(3);
			for (String nombreVariable : datos.keySet()) {
				params.add(new BasicNameValuePair(nombreVariable, datos
						.get(nombreVariable)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			response = httpClient.execute(httpPost, localContext);
			HttpEntity entity = response.getEntity();
			resultado = EntityUtils.toString(entity, "UTF-8");
			System.out.println("RESULTADO" + resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}

}
