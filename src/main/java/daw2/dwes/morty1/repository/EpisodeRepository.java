package daw2.dwes.morty1.repository;

import daw2.dwes.morty1.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    // Episodios posteriores a cierta fecha
    @Query("SELECT e FROM Episode e WHERE e.airDate > :date")
    List<Episode> findEpisodesAfterDate(@Param("date") LocalDate date);

    // Conteo total de episodios
    @Query("SELECT COUNT(e) FROM Episode e")
    long countTotalEpisodes();
}