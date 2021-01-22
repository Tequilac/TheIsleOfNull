package saves;

import game.Game;

import java.io.File;
import java.io.IOException;

public interface IDistrictParser
{
    void parseDistrict(Game game, File file) throws IOException;
}
