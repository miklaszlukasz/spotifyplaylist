package pl.miklaszlukasz.spotifyplaylist.dto;

import lombok.Getter;
import lombok.Setter;
import pl.miklaszlukasz.spotifyplaylist.model.Artist;

import java.util.List;

@Getter
@Setter
public class SongDto {

    private String id;
    private String name;
    private List<Artist> artists;
    private String uri;
}
