package pl.miklaszlukasz.spotifyplaylist.request;

import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.miklaszlukasz.spotifyplaylist.entity.artists.SpotifyArtists;
import pl.miklaszlukasz.spotifyplaylist.entity.relatedartists.RelatedArtists;
import pl.miklaszlukasz.spotifyplaylist.entity.playlistrequestbody.PlaylistRequestBody;
import pl.miklaszlukasz.spotifyplaylist.entity.playlist.Playlist;
import pl.miklaszlukasz.spotifyplaylist.entity.toptracks.TopTracks;

import java.util.List;

@Component
public class SpotifyRequest {

    private final RestTemplate restTemplate;

    public SpotifyRequest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<SpotifyArtists> searchArtist(OAuth2AuthorizedClient client, String artistName) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.spotify.com/v1/search")
                .queryParam("q", artistName)
                .queryParam("type", "artist")
                .queryParam("market", "US")
                .queryParam("limit", "10")
                .queryParam("offset", "5")
                .toUriString();

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                createHttpEntityWithAccessToken(client),
                SpotifyArtists.class);
    }

    public ResponseEntity<RelatedArtists> getRelatedArtists(OAuth2AuthorizedClient client, String artistId) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.spotify.com/v1/artists/{artist_id}/related-artists")
                .buildAndExpand(artistId)
                .toUriString();

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                createHttpEntityWithAccessToken(client),
                RelatedArtists.class
        );
    }

    public ResponseEntity<Playlist> createNewPlaylist(OAuth2AuthorizedClient client, String newPlaylistName) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.spotify.com/v1/users/{user_id}/playlists")
                .queryParam("market", "US")
                .buildAndExpand(client.getPrincipalName())
                .toUriString();

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                createHttpEntityWithPlaylistRequestBody(client, newPlaylistName),
                Playlist.class
        );
    }

    public ResponseEntity<TopTracks> getTopTracksFromArtist(OAuth2AuthorizedClient client, String artistId) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.spotify.com/v1/artists/{id}/top-tracks")
                .queryParam("market", "US")
                .buildAndExpand(artistId)
                .toUriString();

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                createHttpEntityWithAccessToken(client),
                TopTracks.class
        );
    }

    public ResponseEntity<String> addTracksToPlaylist(OAuth2AuthorizedClient client, String playlistId, List<String> topTracks) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.spotify.com/v1/playlists/{playlist_id}/tracks")
                .queryParam("uris", String.join(",", topTracks))
                .buildAndExpand(playlistId)
                .toUriString();

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                createHttpEntityWithAccessToken(client),
                String.class
        );
    }

    public ResponseEntity<Playlist> getPlaylist(OAuth2AuthorizedClient client, String playlistName) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.spotify.com/v1/playlists/{playlist_id}")
                .buildAndExpand(playlistName)
                .toUriString();

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                createHttpEntityWithAccessToken(client),
                Playlist.class
        );
    }

    private HttpEntity<String> createHttpEntityWithAccessToken(OAuth2AuthorizedClient client) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(client.getAccessToken().getTokenValue());
        return new HttpEntity<>(httpHeaders);
    }

    private HttpEntity<PlaylistRequestBody> createHttpEntityWithPlaylistRequestBody(OAuth2AuthorizedClient client, String newPlaylistName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(client.getAccessToken().getTokenValue());
        PlaylistRequestBody playlistRequestBody = new PlaylistRequestBody(newPlaylistName, "test description", false);
        return new HttpEntity<>(playlistRequestBody, httpHeaders);
    }
}
