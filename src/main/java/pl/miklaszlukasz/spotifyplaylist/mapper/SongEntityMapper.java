package pl.miklaszlukasz.spotifyplaylist.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.miklaszlukasz.spotifyplaylist.entity.toptracks.Track;
import pl.miklaszlukasz.spotifyplaylist.model.Song;

@Component
public class SongEntityMapper {

    private final ModelMapper modelMapper;

    public SongEntityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Song map(Track track) {
        return modelMapper.map(track, Song.class);
    }
}
