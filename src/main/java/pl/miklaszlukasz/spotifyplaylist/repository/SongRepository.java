package pl.miklaszlukasz.spotifyplaylist.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.miklaszlukasz.spotifyplaylist.model.Song;

@Repository
public interface SongRepository extends MongoRepository<Song, String> {
}
