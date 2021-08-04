package primo.musicdb.service.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import primo.musicdb.model.entities.ArtistEntity;
import primo.musicdb.repository.ArtistRepository;
import primo.musicdb.service.ArtistService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final Resource artistsFile;
    private final Gson gson;
    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(
            @Value("classpath:init/artists.json") Resource artistsFile
            , Gson gson
            , ArtistRepository artistRepository) {
        this.artistsFile = artistsFile;
        this.gson = gson;
        this.artistRepository = artistRepository;
    }

    @Override
    public void seedArtists() {
        if (artistRepository.count() == 0) {
            try {
                ArtistEntity[] artistEntities =
                        gson.fromJson(Files
                                        .readString(Path
                                                .of(artistsFile.getURI()))
                                , ArtistEntity[].class);

                Arrays.stream(artistEntities)
                        .forEach(artistRepository::saveAndFlush);
            } catch (IOException e) {
                throw new IllegalStateException("Cannot seed artists");
            }
        }
    }
}
