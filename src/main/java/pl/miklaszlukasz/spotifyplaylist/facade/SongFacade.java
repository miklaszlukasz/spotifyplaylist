package pl.miklaszlukasz.spotifyplaylist.facade;

import org.springframework.stereotype.Component;
import pl.miklaszlukasz.spotifyplaylist.dto.SongDto;
import pl.miklaszlukasz.spotifyplaylist.entity.toptracks.Track;
import pl.miklaszlukasz.spotifyplaylist.mapper.SongDtoMapper;
import pl.miklaszlukasz.spotifyplaylist.mapper.SongEntityMapper;
import pl.miklaszlukasz.spotifyplaylist.service.SongService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SongFacade {

    private final SongEntityMapper songEntityMapper;
    private final SongService songService;
    private final SongDtoMapper songDtoMapper;

    public SongFacade(SongEntityMapper songEntityMapper, SongService songService, SongDtoMapper songDtoMapper) {
        this.songEntityMapper = songEntityMapper;
        this.songService = songService;
        this.songDtoMapper = songDtoMapper;
    }

    public void saveAll(List<Track> tracks) {
        songService.saveAll(tracks
                .stream()
                .map(songEntityMapper::map)
                .collect(Collectors.toList()));
    }

    public List<SongDto> getAllSongs() {
        return songService.getAll().stream()
                .map(songDtoMapper::map)
                .collect(Collectors.toList());
    }
}
