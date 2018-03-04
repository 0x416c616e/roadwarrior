import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButton extends PipeButton implements ActionListener {
    private ImageView imgView;
    private int x,y;
    public StartButton(){
        super();
        imgView = new ImageView(new Image("start.png"));
        setGraphic(imgView);
        super.setDirection(false,true,false,false);
    }

    public StartButton(int x, int y){
        super();
        imgView = new ImageView(new Image("start.png"));
        setGraphic(imgView);
        this.x = x;
        this.y = y;
        super.setDirection(false,true,false,false);
    }

    public ImageView getImgView(){
        return imgView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // nothing
    }
}
