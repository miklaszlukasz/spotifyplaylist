package pl.miklaszlukasz.spotifyplaylist.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.miklaszlukasz.spotifyplaylist.entity.artists.SpotifyArtists;
import pl.miklaszlukasz.spotifyplaylist.entity.relatedartists.Artist;
import pl.miklaszlukasz.spotifyplaylist.entity.relatedartists.RelatedArtists;
import pl.miklaszlukasz.spotifyplaylist.entity.playlist.Playlist;
import pl.miklaszlukasz.spotifyplaylist.entity.toptracks.TopTracks;
import pl.miklaszlukasz.spotifyplaylist.entity.toptracks.Track;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResponseDataService {

    public String getArtistIdFromResponseEntity(ResponseEntity<SpotifyArtists> spotifyArtists) {
        return Objects.requireNonNull(spotifyArtists.getBody())
                .getArtists()
                .getItems()
                .stream()
                .max(Comparator.comparingInt(item -> item.getFollowers().getTotal()))
                .orElseThrow()
                .getId();
    }

    public List<String> getRelatedArtistsIdFromResponseEntity(ResponseEntity<RelatedArtists> relatedArtists) {
        return Objects.requireNonNull(relatedArtists.getBody())
                .getArtists()
                .stream()
                .map(Artist::getId)
                .collect(Collectors.toList());
    }

    public List<String> getUrisFromTopTracks(ResponseEntity<TopTracks> topTracksResponseEntity) {
        return Objects.requireNonNull(topTracksResponseEntity.getBody())
                .getTracks()
                .stream()
                .map(Track::getUri)
                .collect(Collectors.toList());
    }

    public String getPlaylistId(ResponseEntity<Playlist> playlist) {
        return Objects.requireNonNull(playlist.getBody()).getId();
    }
}
