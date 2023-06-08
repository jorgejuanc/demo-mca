package com.mca.demo.service;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpMethod;

public interface RestApiService {
    public String callRestApi(URI url, HttpMethod method, String requestBody) throws IOException;
}
