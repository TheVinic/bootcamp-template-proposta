package com.itau.proposta.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.netflix.hystrix.exception.HystrixBadRequestException;

import feign.codec.ErrorDecoder;

@Configuration
public class FeignSkipBadRequestsConfiguration {
	@Bean
	public ErrorDecoder errorDecoder() {
		return (methodKey, response) -> {
			int status = response.status();
			String body = response.body().toString();
			try {
				body = response.body().toString();
			} catch (Exception ignored) {
			}
			HttpHeaders httpHeaders = new HttpHeaders();
			return new FeignBadResponseWrapper(status, httpHeaders, body);
		};
	}

	public class FeignBadResponseWrapper extends HystrixBadRequestException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final int status;
		private final HttpHeaders headers;
		private final String body;

		public FeignBadResponseWrapper(int status, HttpHeaders headers, String body) {
			super(HttpStatus.valueOf(status).toString());
			this.status = status;
			this.headers = headers;
			this.body = body;
		}

		public int getStatus() {
			return status;
		}

		public HttpHeaders getHeaders() {
			return headers;
		}

		public String getBody() {
			return body;
		}
	}
}
