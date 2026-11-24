package daw2.dwes.morty1.repository;

import daw2.dwes.morty1.dto.CharacterStatsDto;
import daw2.dwes.morty1.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    List<Character> findByHeightGreaterThan(Float height);

    // Consulta JPQL con DTO de estadísticas (Personaje, Ubicación y Nº Episodios)
    @Query("SELECT new daw2.dwes.morty1.dto.CharacterStatsDto(" +
            "c.name, " +
            "l.name, " +
            "COUNT(e)) " +
            "FROM Character c " +
            "LEFT JOIN c.location l " +
            "LEFT JOIN c.episodes e " +
            "GROUP BY c.id, c.name, l.name")
    List<CharacterStatsDto> findCharacterStats();
}