package controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.map.MapDirection;
import model.map.Vector2d;
import model.map.districts.District;
import model.map.tiles.TileType;
import view.textures.TextureRepository;
import view.textures.TextureType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class MainPaneController extends Controller
{
    @FXML
    private Canvas canvas;

    private final TextureRepository textureRepository;

    private final List<Vector2d> positions = new ArrayList<>(3);

    @Inject
    public MainPaneController(TextureRepository textureRepository)
    {
        this.textureRepository = textureRepository;
        positions.add(new Vector2d(0, 700));
        positions.add(new Vector2d(200, 590));
        positions.add(new Vector2d(400, 400));
    }

    @Override
    public void initView()
    {
        super.initView();

        canvas.setHeight(900);
        canvas.setWidth(900);

        paint();
    }

    public void paint()
    {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(new Color(57/255., 108/255., 142/255., 1));
        context.fillRect(0, 0, 900, 900);

        Vector2d position = mainController.getGame().getTeam().getPosition();
        MapDirection direction = mainController.getGame().getTeam().getMapDirection();
        District district = mainController.getMapSystem().getCurrentDistrict();

        for(int i = 0; i < 3; i++)
        {
            position = position.add(direction.toVector2d());
            Image image = textureRepository.getImage(district.getTileType(position), TextureType.values()[i], TextureType.values()[i+3]);
            context.drawImage(image, positions.get(i).getX(), positions.get(i).getY());
        }
    }
}
