package pl.miklaszlukasz.spotifyplaylist.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Component;
import pl.miklaszlukasz.spotifyplaylist.entity.artists.SpotifyArtists;
import pl.miklaszlukasz.spotifyplaylist.entity.relatedartists.RelatedArtists;
import pl.miklaszlukasz.spotifyplaylist.entity.playlist.Playlist;
import pl.miklaszlukasz.spotifyplaylist.entity.toptracks.TopTracks;
import pl.miklaszlukasz.spotifyplaylist.request.SpotifyRequest;
import pl.miklaszlukasz.spotifyplaylist.service.ResponseDataService;

import java.util.List;
import java.util.Objects;

@Component
public class PlaylistRequestFacade {

    private final ResponseDataService responseDataService;
    private final SpotifyRequest spotifyRequest;
    private final SongFacade songFacade;

    public PlaylistRequestFacade(ResponseDataService responseDataService, SpotifyRequest spotifyRequest, SongFacade songFacade) {
        this.responseDataService = responseDataService;
        this.spotifyRequest = spotifyRequest;
        this.songFacade = songFacade;
    }

    public ResponseEntity<Playlist> createPlaylist(OAuth2AuthorizedClient client, String artistName) {
        String currentArtistId = getCurrentArtistId(client, artistName);
        List<String> currentAndRelatedArtistsId = getCurrentAndRelatedArtistId(client, currentArtistId);
        String playlistId = createPlaylistAndGetId(client, artistName);

        currentAndRelatedArtistsId
                .forEach(artistId -> {
                    ResponseEntity<TopTracks> topTracksFromArtist = spotifyRequest.getTopTracksFromArtist(client, artistId);
                    List<String> topTracks = responseDataService.getUrisFromTopTracks(topTracksFromArtist);
                    spotifyRequest.addTracksToPlaylist(client, playlistId, topTracks);
                    songFacade.saveAll(Objects.requireNonNull(topTracksFromArtist.getBody()).getTracks());
                });
        return spotifyRequest.getPlaylist(client, playlistId);
    }

    private String getCurrentArtistId(OAuth2AuthorizedClient client, String artistName) {
        ResponseEntity<SpotifyArtists> spotifyArtistsResponseEntity = spotifyRequest.searchArtist(client, artistName);
        return responseDataService.getArtistIdFromResponseEntity(spotifyArtistsResponseEntity);
    }

    private List<String> getCurrentAndRelatedArtistId(OAuth2AuthorizedClient client, String artistIdFromResponseEntity) {
        ResponseEntity<RelatedArtists> relatedArtists = spotifyRequest.getRelatedArtists(client, artistIdFromResponseEntity);
        List<String> relatedArtistsIdFromResponseEntity = responseDataService.getRelatedArtistsIdFromResponseEntity(relatedArtists);
        relatedArtistsIdFromResponseEntity.add(artistIdFromResponseEntity);
        return relatedArtistsIdFromResponseEntity;
    }

    private String createPlaylistAndGetId(OAuth2AuthorizedClient client, String artistName) {
        return responseDataService.getPlaylistId(spotifyRequest.createNewPlaylist(client, artistName));
    }
}
