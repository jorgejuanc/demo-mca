package com.mca.demo.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class OkHttpClientConfig {

	/**
	 * Configura el bean que se usará para las conexiones con las API REST
	 * 
	 * @return objecto OkHttpClient para la conexion
	 */
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
        		.connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }
}
