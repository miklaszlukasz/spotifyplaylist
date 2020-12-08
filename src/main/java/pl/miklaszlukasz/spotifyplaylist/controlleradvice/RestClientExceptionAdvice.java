package pl.miklaszlukasz.spotifyplaylist.controlleradvice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
public class RestClientExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(RestClientException.class)
    public String restClientExceptionHandler(RestClientException restClientException) {
        return restClientException.getMessage();
    }
}
