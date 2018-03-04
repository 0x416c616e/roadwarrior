import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndButton extends PipeButton implements ActionListener {
    private ImageView imgView;
    private int x,y;



    public EndButton(){
        super();
        imgView = new ImageView(new Image("finish.png"));
        setGraphic(imgView);
        super.setDirection(true ,false,false,false);
    }

    public EndButton(int x, int y){
        super();
        imgView = new ImageView(new Image("finish.png"));
        setGraphic(imgView);
        this.x = x;
        this.y = y;
        super.setDirection(true,false,false,false);
    }

    public ImageView getImgView(){
        return imgView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // nothing
    }
}
