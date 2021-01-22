package races;

import com.google.inject.Singleton;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class RacesRepository
{
    private final Map<String, Race> knownRaces = new HashMap<>();

    public Race getRace(String raceName) throws FileNotFoundException
    {
        Race race = knownRaces.get(raceName);
        if(race == null)
        {
            race = RaceParser.parseRace(true, raceName);
            knownRaces.put(raceName, race);
        }
        return race;
    }

    public Map<String, Race> getAllRaces()
    {
        return knownRaces;
    }
}
