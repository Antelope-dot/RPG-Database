package fxRpg;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import rpg.Rpg;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Ilari massa
 * @version 28.2.2019
 *
 *Pääohjelma Rpg-ohjelman käynnistämiseksi
 */
public class RpgMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("RpgGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
		    final RpgGUIController rpgCtrl = (RpgGUIController)ldr.getController();
			
			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("rpg.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Rpg");
			
			
            primaryStage.setOnCloseRequest((event) -> {
                   if ( !rpgCtrl.voikoSulkea() ) event.consume();
                });
			
			
			
			Rpg rpg = new Rpg();
			rpgCtrl.setRpg(rpg);
			primaryStage.show();
			
			Application.Parameters params = getParameters();
			if ( params.getRaw().size() > 0)
			    rpgCtrl.lueTiedosto(params.getRaw().get(0));
			else
			    if ( !rpgCtrl.avaa() ) Platform.exit();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Käynnistetään käyttöliittymä
	 * @param args komentorivin parametrit
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
