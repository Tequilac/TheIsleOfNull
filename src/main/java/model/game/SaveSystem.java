package model.game;

import com.google.inject.Singleton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Singleton
public class SaveSystem
{
    private File currentSave;

    public void saveGame()
    {

    }

    public void loadGame()
    {

    }

    public void createNewSave(String name)
    {
        File file = new File("saveGames/" + name);
        setCurrentSave(file);
    }

    public List<File> getSaves()
    {
        File folder = new File("saveGames");
        return List.of(Objects.requireNonNull(folder.listFiles()));
    }

    public boolean saveExists(String name)
    {
        File folder = new File("saveGames");
        for(File file : Objects.requireNonNull(folder.listFiles()))
        {
            if(file.getName().equals(name))
            {
                return true;
            }
        }
        return false;
    }

    public void setCurrentSave(File currentSave)
    {
        this.currentSave = currentSave;
    }
}
