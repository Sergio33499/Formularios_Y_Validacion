package daw2.dwes.morty1.repository;

import daw2.dwes.morty1.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    // Ubicaciones con personajes humanos
    @Query("SELECT DISTINCT l FROM Location l JOIN l.characters c WHERE c.species = 'Humano'")
    List<Location> findLocationsWithHumans();
}