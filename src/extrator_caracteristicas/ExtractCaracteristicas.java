package extrator_caracteristicas;

import java.io.File;
import java.io.FileOutputStream;
import org.opencv.core.Mat;
//import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ExtractCaracteristicas {

	public static double[] extraiCaracteristicas(File f) {
		
		double[] caracteristicas = new double[7];
		
		double marromCabeloEdna = 0;
		double verdeColeteEdna = 0;
		double azulSaiaEdna = 0;
		double azulCabeloMilhouse = 0;
		double violetaCamisaMilhouse = 0;
		double vermelhoShortMilhouse = 0; 
		
		Image img = new Image(f.toURI().toString());
		PixelReader pr = img.getPixelReader();
		
		Mat imagemOriginal = Imgcodecs.imread(f.getPath());
        Mat imagemProcessada = imagemOriginal.clone();
		
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				
				Color cor = pr.getColor(j,i);
				
				double r = cor.getRed()*255; 
				double g = cor.getGreen()*255;
				double b = cor.getBlue()*255;
				
				if(isMarronCabeloEdna(r, g, b)) {
					marromCabeloEdna++;
					imagemProcessada.put(i, j, new double[]{250, 5, 255});
				}
				if(isVerdeColeteEdna(r, g, b)) {
					verdeColeteEdna++;
					imagemProcessada.put(i, j, new double[]{250, 5, 255});
				}
				if (isAzulSaiaEdna(r, g, b)) {
					azulSaiaEdna++;
					imagemProcessada.put(i, j, new double[]{250, 5, 255});
				}
				if(isAzulCabeloMilhouse(r, g, b)) {
					azulCabeloMilhouse++;
					imagemProcessada.put(i, j, new double[]{250, 200, 5});
				}
				if(isVioletaCamisaMilhouse(r, g, b)) {
					violetaCamisaMilhouse++;
					imagemProcessada.put(i, j, new double[]{250, 200, 5});
				}
				if (isVermelhoShortMilhouse(r, g, b)) {
					vermelhoShortMilhouse++;
					imagemProcessada.put(i, j, new double[]{250, 200, 5});
				}
			}
		}
		
		// Normaliza as características pelo número de pixels totais da imagem para %
		marromCabeloEdna = (marromCabeloEdna / (w * h)) * 100;
		verdeColeteEdna = (verdeColeteEdna / (w * h)) * 100;
		azulSaiaEdna = (azulSaiaEdna / (w * h)) * 100;
		azulCabeloMilhouse = (azulCabeloMilhouse / (w * h)) * 100;
		violetaCamisaMilhouse = (violetaCamisaMilhouse / (w * h)) * 100;
		vermelhoShortMilhouse = (vermelhoShortMilhouse / (w * h)) * 100;
        
        caracteristicas[0] = marromCabeloEdna;
        caracteristicas[1] = verdeColeteEdna;
        caracteristicas[2] = azulSaiaEdna;
        caracteristicas[3] = azulCabeloMilhouse;
        caracteristicas[4] = violetaCamisaMilhouse;
        caracteristicas[5] = vermelhoShortMilhouse;
        //APRENDIZADO SUPERVISIONADO - JÁ SABE QUAL A CLASSE NAS IMAGENS DE TREINAMENTO
        //caracteristicas[6] = f.getName().charAt(0)=='e'?0:1;
        
        if(f.getName().charAt(0)=='e')
        	caracteristicas[6] = 0;
        else if(f.getName().charAt(0)=='m')
        	caracteristicas[6] = 1;
        else
        	caracteristicas[6] = 2;
        
//		HighGui.imshow("Imagem original", imagemOriginal);
//        HighGui.imshow("Imagem processada", imagemProcessada);
//        
//        HighGui.waitKey(0);
		
		return caracteristicas;
	}

	public  static  boolean  isMarronCabeloEdna ( double  r , double  g , double  b ) {
		 if (b >=  30  && b <=  100  &&   g >=  60  && g <=  135  &&   r >=  130  && r <=  200 ) {                       
      	return  true ;
      }
		 return  false ;
	}
	
	public  static  boolean  isVerdeColeteEdna ( double  r , double  g , double  b ) {
		if (b >=  7  && b <=  35  &&   g >=  120  && g <=  245  &&   r >=  35  && r <=  110 ) {                       
			return  true ;
		}
		return  false ;
	}
	
	public static boolean isAzulSaiaEdna(double r, double g, double b) {
		if (b >= 85 && b <= 165 &&  g >= 100 && g <= 190 &&  r >= 0 && r <= 50) {                       
			return true;
		}
		return false;
	}
	
	public  static  boolean  isAzulCabeloMilhouse ( double  r , double  g , double  b ) {
		if (b >=  160  && b <=  230  &&   g >=  35  && g <=  130  &&   r >=  35  && r <=  130 ) {  
			return  true ;
		}
		return  false ;
	}
	
	public  static  boolean  isVioletaCamisaMilhouse ( double  r , double  g , double  b ) {
		if (b >=  180  && b <=  205  &&   g >=  120  && g <=  160  &&   r >=  175  && r <=  200 ) {                       
			return  true ;
		}
		return  false ;
	}
	
	public  static  boolean  isVermelhoShortMilhouse ( double  r , double  g , double  b ) {
		if (b >=  3  && b <=  15  &&   g >=  3  && g <=  15  &&   r >=  145  && r <=  240 ) {                       
			return  true ;
		}
		return  false ;
	}

	public static void extrair() {
				
	    // Cabeçalho do arquivo Weka
		String exportacao = "@relation caracteristicas\n\n";
		exportacao += "@attribute marrom_cabelo_edna real\n";
		exportacao += "@attribute verde_colete_edna real\n";
		exportacao += "@attribute azul_saia_edna real\n";
		exportacao += "@attribute azul_cabelo_milhouse real\n";
		exportacao += "@attribute violeta_camisa_milhouse real\n";
		exportacao += "@attribute vermelho_short_milhouse real\n";
		exportacao += "@attribute classe {Edna, Milhouse}\n\n";
		exportacao += "@data\n";
	        
	    // Diretório onde estão armazenadas as imagens
	    File diretorio = new File("src\\imagens");
	    File[] arquivos = diretorio.listFiles();
	    
        // Definição do vetor de características
        double[][] caracteristicas = new double[1536][7];
        
        // Percorre todas as imagens do diretório
        int cont = -1;
        for (File imagem : arquivos) {
        	cont++;
        	caracteristicas[cont] = extraiCaracteristicas(imagem);
        	
        	String classe = caracteristicas[cont][6] == 0 ?"Edna":"Milhouse";
        	
        	System.out.println("Marron cabelo Edna: " + caracteristicas[cont][0] 
            		+ " - Verde colete Edna: " + caracteristicas[cont][1] 
            		+ " - Azul saia Edna: " + caracteristicas[cont][2] 
            		+ " - Azul cabelo Milhouse: " + caracteristicas[cont][3] 
            		+ " - Violeta camisa Milhouse: " + caracteristicas[cont][4] 
            		+ " - Vermelho short Milhouse: " + caracteristicas[cont][5] 
            		+ " - Classe: " + classe);
        	
        	exportacao += caracteristicas[cont][0] + "," 
                    + caracteristicas[cont][1] + "," 
        		    + caracteristicas[cont][2] + "," 
                    + caracteristicas[cont][3] + "," 
        		    + caracteristicas[cont][4] + "," 
                    + caracteristicas[cont][5] + "," 
                    + classe + "\n";
        }
        
     // Grava o arquivo ARFF no disco
        try {
        	File arquivo = new File("caracteristicas.arff");
        	FileOutputStream f = new FileOutputStream(arquivo);
        	f.write(exportacao.getBytes());
        	f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
