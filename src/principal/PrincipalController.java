package principal;

import java.io.File;
import java.text.DecimalFormat;

import algoritmos.AprendizagemBayesiana;
import extrator_caracteristicas.ExtractCaracteristicas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class PrincipalController {
	
	@FXML private ImageView imageView;
	
	@FXML Label marronEdna;
	@FXML Label verdeEdna;
	@FXML Label azulEdna;
	@FXML Label azulMilhouse;
	@FXML Label violetaMilhouse;
	@FXML Label vermelhoMilhouse;
	@FXML Label classe;
	@FXML Label prEdna;
	@FXML Label prMilhouse;
	
	private double [] c = {0,0,0,0,0,0};
	
	private DecimalFormat df = new DecimalFormat("##0.0000");
	
	@FXML
	public void extrairCaracteristicas() {
		ExtractCaracteristicas.extrair();
	}
	
	@FXML
	public void selecionaImagem() {
		File f = buscaImg();
		if(f != null) {
			Image img = new Image(f.toURI().toString());
			imageView.setImage(img);
			imageView.setFitWidth(img.getWidth());
			imageView.setFitHeight(img.getHeight());
			double[] caracteristicas = ExtractCaracteristicas.extraiCaracteristicas(f);

			marronEdna.setText(String.valueOf(df.format(caracteristicas[0])));
			verdeEdna.setText(String.valueOf(df.format(caracteristicas[1])));
			azulEdna.setText(String.valueOf(df.format(caracteristicas[2])));
			azulMilhouse.setText(String.valueOf(df.format(caracteristicas[3])));
			violetaMilhouse.setText(String.valueOf(df.format(caracteristicas[4])));
			vermelhoMilhouse.setText(String.valueOf(df.format(caracteristicas[5])));
			
			if(caracteristicas[6] == 0) 
				classe.setText("Edna");
			else if (caracteristicas[6] == 1)
				classe.setText("Milhouse");
			else 
				classe.setText("Desconhecida");
			
			c = caracteristicas;
		}
	}
	
	@FXML 
	public void classifica() {
		
		double[] nb = AprendizagemBayesiana.naiveBayes(c);
		prEdna.setText("Probabilidade de ser a Edna: " + df.format(nb[0]*100)+ "%");
		prMilhouse.setText("Probabilidade de ser o Milhouse: " + df.format(nb[1]*100)+ "%");
		
		if (nb[0] > nb[1]) 
			classe.setText("Edna");
		else if (nb[0] < nb[1])
			classe.setText("Milhouse");
		else 
			classe.setText("Desconhecida");
	}
	
	private File buscaImg() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		 fileChooser.setInitialDirectory(new File("src/imagens"));
		 File imgSelec = fileChooser.showOpenDialog(null);
		 try {
			 if (imgSelec != null) {
			    return imgSelec;
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 return null;
	}
	
}
