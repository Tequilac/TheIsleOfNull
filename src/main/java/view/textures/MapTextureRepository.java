package view.textures;

import com.google.inject.Singleton;
import javafx.scene.image.Image;
import model.map.tiles.TileType;

import java.util.ArrayList;
import java.util.List;

import static model.map.tiles.TileType.GRASS;

@Singleton
public class MapTextureRepository
{
    private final List<Image> images = new ArrayList<>(TileType.values().length);

    public MapTextureRepository()
    {
        for(int i = 0; i < TileType.values().length; i++)
        {
            images.add(null);
        }
    }

    public Image getImage(TileType tileType)
    {
        int tileNumber;
        if(tileType == null)
        {
            tileType = GRASS;
        }
        switch(tileType)
        {
            case GRASS -> tileNumber = 0;
            case SAND -> tileNumber = 1;
            case WATER -> tileNumber = 2;
            default -> tileNumber = -1;
        }
        Image image = images.get(tileNumber);
        if(image == null)
        {
            image = new Image("/graphics/worldGraphics/" + tileType.toString().toLowerCase() + ".png");
            images.set(tileNumber, image);
        }
        return image;
    }
}
