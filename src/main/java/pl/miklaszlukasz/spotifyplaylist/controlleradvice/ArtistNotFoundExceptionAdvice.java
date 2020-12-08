package pl.miklaszlukasz.spotifyplaylist.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.miklaszlukasz.spotifyplaylist.exception.ArtistNotFoundException;

@ControllerAdvice
public class ArtistNotFoundExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ArtistNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String artistNotFoundExceptionHandler(ArtistNotFoundException artistNotFoundException) {
        return artistNotFoundException.getMessage();
    }
}
