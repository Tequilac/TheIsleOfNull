package model.races;

import com.google.inject.Singleton;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class EnemyRacesRepository
{
    private final Map<String, Race> knownEnemyRaces = new HashMap<>();

    public Race getEnemyRace(String raceName) throws FileNotFoundException
    {
        Race race = knownEnemyRaces.get(raceName);
        if(race == null)
        {
            race = RaceParser.parseRace(false, raceName);
            knownEnemyRaces.put(raceName, race);
        }
        return race;
    }
}
