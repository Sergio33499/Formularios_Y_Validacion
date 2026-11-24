package daw2.dwes.morty1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CharacterStatsDto {

    private String characterName;
    private String locationName;
    private Long episodeCount;

    // Constructor secundario de soporte
    public CharacterStatsDto(String characterName, String locationName, Integer episodeCount) {
        this.characterName = characterName;
        this.locationName = locationName;
        this.episodeCount = episodeCount != null ? episodeCount.longValue() : 0L;
    }
}