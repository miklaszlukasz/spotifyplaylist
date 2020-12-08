package pl.miklaszlukasz.spotifyplaylist.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.miklaszlukasz.spotifyplaylist.dto.SongDto;
import pl.miklaszlukasz.spotifyplaylist.model.Song;

@Component
public class SongDtoMapper {

    private final ModelMapper modelMapper;

    public SongDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SongDto map(Song song) {
        return modelMapper.map(song, SongDto.class);
    }
}
