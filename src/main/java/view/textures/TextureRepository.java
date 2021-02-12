package view.textures;

import javafx.scene.image.Image;
import model.map.tiles.TileType;

import java.util.ArrayList;
import java.util.List;

public class TextureRepository
{
    private static List<List<List<Image>>> images = new ArrayList<>(TileType.values().length);

    static
    {
        for(int i = 0; i < TileType.values().length; i++)
        {
            images.set(i, new ArrayList<>(3));
            for(int j = 0; j < 3; j++)
            {
                images.get(i).set(j, new ArrayList<>(3));
            }
        }
    }

    public Image getImage(TileType tileType, TextureType horizontalType, TextureType verticalType)
    {
        int tileNumber;
        switch(tileType)
        {
            default -> tileNumber = 0;
        }
        int horizontalNumber;
        switch(horizontalType)
        {
            default -> horizontalNumber = 0;
        }
        int verticalNumber;
        switch(verticalType)
        {
            default -> verticalNumber = 0;
        }
        return images.get(tileNumber).get(horizontalNumber).get(verticalNumber);
    }
}
