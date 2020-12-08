package pl.miklaszlukasz.spotifyplaylist.service;

import org.springframework.stereotype.Service;
import pl.miklaszlukasz.spotifyplaylist.model.Song;
import pl.miklaszlukasz.spotifyplaylist.repository.SongRepository;

import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public void saveAll(List<Song> songs) {
        songRepository.saveAll(songs);
    }

    public List<Song> getAll() {
        return songRepository.findAll();
    }
}
