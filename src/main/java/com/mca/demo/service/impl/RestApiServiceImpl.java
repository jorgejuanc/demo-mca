package com.mca.demo.service.impl;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.mca.demo.service.RestApiService;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@Service
public class RestApiServiceImpl implements RestApiService {
	
	@Autowired
	private OkHttpClient client;

	/**
	 * Metodo generico parametrizado para realizar llamadas a APIs REST
	 * 
	 * @param url         la URL de la API a la cual se va a realizar la llamada
	 * @param method      el método HTTP a utilizar para la llamada (GET, POST, PUT, DELETE...)
	 * @param requestBody el cuerpo de la solicitud HTTP en formato cadena de texto
	 * 
	 * @return la respuesta de la API en formato cadena de texto
	 * @throws IOException si ocurre algún error durante la ejecución de la llamada a la API.
	 */
    public String callRestApi(URI url, HttpMethod method, String requestBody) throws IOException {
        RequestBody body = null;
        if (requestBody != null) {
            MediaType mediaType = MediaType.parse("application/json");
            body = RequestBody.create(mediaType, requestBody);
        }

        Request request = new Request.Builder()
                .url(url.toString())
                .method(method.toString(), body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Error en callRestApi: HTTP code -> " + response.code() + " URL -> " + url);
            }
        }
    }
}
