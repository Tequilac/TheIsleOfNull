package controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.map.MapDirection;
import model.map.Vector2d;
import model.map.districts.District;
import view.textures.MapTextureRepository;

@Singleton
public class OptionsController extends Controller
{
    @FXML
    private VBox optionsBox;

    @FXML
    private Canvas  mapCanvas;

    private final MapTextureRepository mapTextureRepository;

    @Inject
    public OptionsController(MapTextureRepository mapTextureRepository)
    {
        this.mapTextureRepository = mapTextureRepository;
    }

    @Override
    public void initView()
    {
        super.initView();

        optionsBox.setStyle("-fx-background-color: #979797;");

        mapCanvas.setHeight(300);
        mapCanvas.setWidth(300);

        paintMap();
    }

    public void paintMap()
    {
        GraphicsContext context = mapCanvas.getGraphicsContext2D();
        context.setFill(new Color(0, 0, 0, 1));
        context.fillRect(0, 0, 300, 300);

        Vector2d position = mainController.getGame().getTeam().getPosition();
        MapDirection direction = mainController.getGame().getTeam().getMapDirection();
        District district = mainController.getMapSystem().getCurrentDistrict();
        Vector2d topLeft = position.add(new Vector2d(-7, -7));
        for(int i = 0; i < 15; i++)
        {
            for(int j = 0; j < 15; j++)
            {
                Vector2d currentTile = topLeft.add(new Vector2d(i, j));
                context.drawImage(mapTextureRepository.getImage(district.getTileType(currentTile)), i*20, j*20);
            }
        }
        ImageView imageView = new ImageView("/graphics/team.png");
        imageView.setRotate(direction.toAngle());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = imageView.snapshot(params, null);
        context.drawImage(rotatedImage, 7*20, 7*20);
    }
}
