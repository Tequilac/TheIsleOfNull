package saves;

import main.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface IDistrictParser
{
    void parseDistrict(Game game, File file) throws IOException;
}
