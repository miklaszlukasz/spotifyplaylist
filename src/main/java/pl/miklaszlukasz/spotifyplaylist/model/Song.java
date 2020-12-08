package pl.miklaszlukasz.spotifyplaylist.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
public class Song {

    @Id
    private String id;
    private String name;
    private List<Artist> artists;
    private String uri;
}
