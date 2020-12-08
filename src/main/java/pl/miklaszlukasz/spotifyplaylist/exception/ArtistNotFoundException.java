package pl.miklaszlukasz.spotifyplaylist.exception;

public class ArtistNotFoundException extends RuntimeException {
    public ArtistNotFoundException(String artistName) {
        super("Not found artist with name: " + artistName);
    }
}
