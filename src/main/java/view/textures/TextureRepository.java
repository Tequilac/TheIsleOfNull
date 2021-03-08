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
                int amount;
                if(j == 0)
                {
                    amount = 5;
                }
                else
                {
                    amount = 3;
                }
                images.get(i).add(new ArrayList<>(amount));
                for(int k = 0; k < amount; k++)
                {
                    images.get(i).get(j).add(null);
                }
            }
        }
    }

    public Image getImage(TileType tileType, HorizontalTextureType horizontalType, VerticalTextureType verticalType)
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
            case FAR_LEFT -> horizontalNumber = 3;
            case FAR_RIGHT -> horizontalNumber = 4;
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
        System.out.println(tileType);
        System.out.println(horizontalType);
        System.out.println(verticalType);
        Image image = images.get(tileNumber).get(verticalNumber).get(horizontalNumber);
        if(image == null)
        {
            image = loadImage(tileType, horizontalType, verticalType);
            images.get(tileNumber).get(verticalNumber).set(horizontalNumber, image);
        }
        return image;
    }

    private Image loadImage(TileType tileType, HorizontalTextureType horizontalType, VerticalTextureType verticalType)
    {
        String url = switch(horizontalType)
                {
                    case FAR_LEFT, LEFT, RIGHT, FAR_RIGHT -> "/graphics/mainPane/" + tileType.toString().toLowerCase() + "_rotated.png";
                    case MIDDLE -> "/graphics/mainPane/" + tileType.toString().toLowerCase() + ".png";
                };
        return switch(verticalType)
                {
                    case FAR -> new Image(url, 278, 62, true, true);
                    case CENTER -> new Image(url, 500, 111, true, true);
                    case CLOSE -> new Image(url);
                };
    }
}
