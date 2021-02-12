package controller;

import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import javax.swing.*;

@Singleton
public class MainPaneController extends Controller
{
    @FXML
    private Canvas canvas;

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
        Image image = new Image("/graphics/mainPane/grass.png");
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(new Color(57/255., 108/255., 142/255., 1));
        context.fillRect(0, 0, 900, 900);
        context.drawImage(image, 0, 700);
        ImageView imageView = new ImageView(image);
        Image image2 = new Image("/graphics/mainPane/grass.png", 500, 111, true, true);
        context.drawImage(image2, 200, 590);

    }
}
