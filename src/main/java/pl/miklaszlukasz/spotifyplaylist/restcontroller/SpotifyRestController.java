package pl.miklaszlukasz.spotifyplaylist.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.miklaszlukasz.spotifyplaylist.dto.SongDto;
import pl.miklaszlukasz.spotifyplaylist.entity.playlist.Playlist;
import pl.miklaszlukasz.spotifyplaylist.facade.PlaylistRequestFacade;
import pl.miklaszlukasz.spotifyplaylist.facade.SongFacade;

import java.util.List;

@RestController
public class SpotifyRestController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    private final PlaylistRequestFacade playlistRequestFacade;
    private final SongFacade songFacade;

    public SpotifyRestController(OAuth2AuthorizedClientService authorizedClientService, PlaylistRequestFacade playlistRequestFacade, SongFacade songFacade) {
        this.authorizedClientService = authorizedClientService;
        this.playlistRequestFacade = playlistRequestFacade;
        this.songFacade = songFacade;
    }

    @GetMapping("/user-info")
    public OAuth2AuthorizedClient getUser(OAuth2AuthenticationToken authentication) {
        return authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
    }

    @GetMapping("/create-playlist/{artistName}")
    public ResponseEntity<Playlist> createArtist(OAuth2AuthenticationToken authentication, @PathVariable String artistName) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        return playlistRequestFacade.createPlaylist(client, artistName);
    }

    @GetMapping("/get-all-songs")
    public List<SongDto> getAllSongs() {
        return songFacade.getAllSongs();
    }
}
