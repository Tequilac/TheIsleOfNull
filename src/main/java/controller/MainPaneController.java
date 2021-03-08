package controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.entities.Team;
import model.map.MapDirection;
import model.map.Vector2d;
import model.map.districts.District;
import view.textures.HorizontalTextureType;
import view.textures.TextureRepository;
import view.textures.VerticalTextureType;

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
        positions.add(new Vector2d(-700, 700));
        positions.add(new Vector2d(0, 700));
        positions.add(new Vector2d(700, 700));
        positions.add(new Vector2d(-189, 589));
        positions.add(new Vector2d(200, 589));
        positions.add(new Vector2d(587, 589));
        positions.add(new Vector2d(95, 527));
        positions.add(new Vector2d(311, 527));
        positions.add(new Vector2d(523, 527));
        positions.add(new Vector2d(-60, 527));
        positions.add(new Vector2d(680, 527));
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
        context.setFill(new Color(84/255., 149/255., 172/255., 1));
        context.fillRect(0, 0, 900, 900);

        Team team = mainController.getGame().getTeam();
        MapDirection direction = team.getMapDirection();
        District district = mainController.getMapSystem().getCurrentDistrict();
        Vector2d position = team.getPosition().add(direction.toVector2d().multiply(4));
        Vector2d left = position.add(direction.getPrevious().toVector2d());
        Vector2d right = position.add(direction.getNext().toVector2d());
        Vector2d farLeft = position.add(direction.getPrevious().toVector2d().multiply(2));
        Vector2d farRight = position.add(direction.getNext().toVector2d().multiply(2));

        for(int i = 0; i < 3; i++)
        {
            left = left.subtract(direction.toVector2d());
            position = position.subtract(direction.toVector2d());
            right = right.subtract(direction.toVector2d());
            farLeft = farLeft.subtract(direction.toVector2d());
            farRight = farRight.subtract(direction.toVector2d());

            if(i == 0)
            {
                List<Vector2d> positionsList = List.of(left, position, right, farLeft, farRight);
                for(int j = 0; j < 5; j++)
                {
                    Image image = textureRepository.getImage(district.getTileType(positionsList.get(j)), HorizontalTextureType.values()[j], VerticalTextureType.values()[i]);
                    context.drawImage(image, positions.get(6+j).getX(), positions.get(6+j).getY());
                }
            }
            else
            {
                List<Vector2d> positionsList = List.of(left, position, right);
                for(int j = 0; j < 3; j++)
                {
                    Image image = textureRepository.getImage(district.getTileType(positionsList.get(j)), HorizontalTextureType.values()[j], VerticalTextureType.values()[i]);
                    context.drawImage(image, positions.get(3*(2-i)+j).getX(), positions.get(3*(2-i)+j).getY());
                }
            }
        }
    }
}
