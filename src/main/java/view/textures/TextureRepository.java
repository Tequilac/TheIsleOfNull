package view.textures;

import com.google.inject.Singleton;
import javafx.scene.image.Image;
import model.map.tiles.TileType;

import java.util.ArrayList;
import java.util.List;

import static model.map.tiles.TileType.GRASS;

@Singleton
public class TextureRepository
{
    private final List<List<List<Image>>> images = new ArrayList<>(TileType.values().length);

    public TextureRepository()
    {
        for(int i = 0; i < TileType.values().length; i++)
        {
            images.add(new ArrayList<>(3));
            for(int j = 0; j < 3; j++)
            {
                images.get(i).add(new ArrayList<>(3));
                for(int k = 0; k < 3; k++)
                {
                    images.get(i).get(j).add(null);
                }
            }
        }
    }

    public Image getImage(TileType tileType, TextureType horizontalType, TextureType verticalType)
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
        int horizontalNumber;
        switch(horizontalType)
        {
            case LEFT -> horizontalNumber = 0;
            case MIDDLE -> horizontalNumber = 1;
            case RIGHT -> horizontalNumber = 2;
            default -> horizontalNumber = -1;
        }
        int verticalNumber;
        switch(verticalType)
        {
            case FAR -> verticalNumber = 0;
            case CENTER -> verticalNumber = 1;
            case CLOSE -> verticalNumber = 2;
            default -> verticalNumber = -1;
        }
        Image image = images.get(tileNumber).get(horizontalNumber).get(verticalNumber);
        if(image == null)
        {
            image = loadImage(tileType, horizontalType, verticalType);
            images.get(tileNumber).get(horizontalNumber).set(verticalNumber, image);
        }
        return image;
    }

    private Image loadImage(TileType tileType, TextureType horizontalType, TextureType verticalType)
    {
        String url = switch(horizontalType)
                {
                    case LEFT, RIGHT -> "/graphics/mainPane/" + tileType.toString().toLowerCase() + "_rotated.png";
                    case MIDDLE -> "/graphics/mainPane/" + tileType.toString().toLowerCase() + ".png";
                    default -> throw new IllegalArgumentException("Unexpected value: " + horizontalType);
                };
        return switch(verticalType)
                {
                    case FAR -> new Image(url, 278, 62, true, true);
                    case CENTER -> new Image(url, 500, 111, true, true);
                    case CLOSE -> new Image(url);
                    default -> throw new IllegalArgumentException("Unexpected value: " + verticalType);
                };
    }
}
