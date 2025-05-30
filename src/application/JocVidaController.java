package application;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class JocVidaController implements Initializable{
	//				   colors
	//
	//				mort: black
	//				 viu: white
	//   acabat de morir: rgb(37, 37, 37)
	//  acabat de naixer: rgb(202, 202, 202)
	
	@FXML private VBox principal;
	@FXML private GridPane joc;
	
	@FXML private Label labelCelules;
	@FXML private Label labelTotalCreades;
	@FXML private Label labelTotalMortes;
	
	@FXML private Button pausar;
	@FXML private Button reanudar;
	@FXML private Button parar;
	@FXML private Button velocitatMenys;
	@FXML private Button velocitatMes;
	
	private Timeline timeline;
	private Label[][] caselles;
	
	private String tamanyString;
	private int tamany;
	private int celules;
	private int totalCreades;
	private int totalMortes;
	private int totalGeneracions;
	private int velocitat = 250;
	
	public void pausarClick(ActionEvent e)
	{
		timeline.pause();
		
		pausar.setDisable(true);
		pausar.setStyle("-fx-background-color: #a5d6a7; -fx-text-fill: #757575; -fx-font-weight: bold;");
		
		reanudar.setDisable(false);
		reanudar.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;");

	}
	public void reanudarClick(ActionEvent e)
	{
		timeline.play();
		
		pausar.setDisable(false);
		pausar.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;");
		
		reanudar.setDisable(true);
		reanudar.setStyle("-fx-background-color: #a5d6a7; -fx-text-fill: #757575; -fx-font-weight: bold;");
	}
	public void velocitatMenysClick(ActionEvent e)
	{
		if (velocitat>10)
		{
			this.velocitat-=10;
			if(velocitat<=10)
			{
				velocitatMenys.setDisable(true);
				velocitatMenys.setStyle("-fx-background-color: #a5d6a7; -fx-text-fill: #757575; -fx-font-weight: bold;");
			}
			iniciarTimeline();
		}
	}
	public void velocitatMesClick(ActionEvent e)
	{
		this.velocitat+=10;
		velocitatMenys.setDisable(false);
		velocitatMenys.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;");
		iniciarTimeline();
	}
	public void pararClick(ActionEvent e)
	{
		try {
			VBox registre = FXMLLoader.load(getClass().getResource("JocVidaFinal.fxml"));
			Scene escenaRegistre = new Scene(registre);
			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			window.setUserData(new DadesJocVida(tamanyString, tamany, celules, totalCreades, totalMortes, totalGeneracions));
			window.setScene(escenaRegistre);
			window.setTitle("Dades del Joc de la vida");
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	
	private void iniciarTimeline()
	{
	    if (timeline != null)
	    {
	        timeline.stop();
	    }
	    timeline = new Timeline(
	        new KeyFrame(Duration.millis(this.velocitat), e -> {
	            Label[][] novesCaselles = iteracio(caselles);

	            Platform.runLater(() -> {
	                for (int i = 0; i < tamany; i++)
	                {
	                    for (int j = 0; j < tamany; j++)
	                    {
	                        caselles[i][j].setStyle(novesCaselles[i][j].getStyle());
	                    }
	                }
	            });
	            labelCelules.setText("Cel·lules actuals: " + this.celules);
	            labelTotalCreades.setText("Cel·lules creades en total: " + this.totalCreades);
	            labelTotalMortes.setText("Cel·lules mortes en total: " + this.totalMortes);
	            totalGeneracions++;
	            if (celules == 0)
	            {
	                timeline.stop();
	                pararClick(e);
	            }
	        })
	    );
	    timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.play();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->{
			//detectar tamany seleccionat
			Window window = (Stage) principal.getScene().getWindow();
			this.tamanyString = (String) window.getUserData();
			switch(tamanyString) {
				case "S" :{
					this.tamany = 15;
					break;
				}
				case "M" :{
					this.tamany = 25;
					break;
				}
				case "L" :{
					this.tamany = 35;
					break;
				}
			}
			this.celules = tamany*tamany/4;
			joc.setMaxHeight(tamany*20);
			joc.setMaxWidth(tamany*20);
			joc.setPrefHeight(tamany*20);
			joc.setPrefWidth(tamany*20);
			joc.setMinHeight(tamany*20);
			joc.setMinWidth(tamany*20);
			//crear i emplenar matriu
			this.caselles = new Label[tamany][tamany];
			for (int i=0; i<tamany; i++)
			{
				for (int j=0; j<tamany;j++)
				{
					Label casella = new Label();
					casella.setMaxHeight(20);
					casella.setMaxWidth(20);
					casella.setPrefHeight(20);
					casella.setPrefWidth(20);
					casella.setMinHeight(20);
					casella.setMinWidth(20);
					casella.setStyle("-fx-background-color:black;");

					caselles[i][j] = casella;
					joc.add(caselles[i][j], j, i);
				}
			}
			//inici del joc
			Random aleatori = new Random();
			for (int i=0; i<celules; i++)
			{
				int x = aleatori.nextInt(tamany);
				int y = aleatori.nextInt(tamany);
				if (this.caselles[x][y].getStyle().equals("-fx-background-color:white;"))
				{
					i--;
				}
				else
				{
					this.caselles[x][y].setStyle("-fx-background-color:white;");
				}
			}
			//funcionament del joc
			iniciarTimeline();
		});
	}
	
	public Label[][] iteracio(Label[][] casellesAbans)
	{
		this.celules = 0;
		Label[][] casellesDespres = new Label[casellesAbans.length][casellesAbans[0].length];
		for (int i = 0; i < casellesAbans.length; i++) {
		    for (int j = 0; j < casellesAbans[i].length; j++) {
		        casellesDespres[i][j] = new Label();
		        casellesDespres[i][j].setStyle(casellesAbans[i][j].getStyle());
		    }
		}
		for (int x=0; x<casellesDespres.length; x++)
		{
			for (int y=0; y<casellesDespres[x].length; y++)
			{
				switch(posicio(x,y,casellesDespres))
				{
					case "no" ->{casellesDespres = no(casellesAbans, casellesDespres);}
					case "ne" ->{casellesDespres = ne(casellesAbans, casellesDespres);}
					case "so" ->{casellesDespres = so(casellesAbans, casellesDespres);}
					case "se" ->{casellesDespres = se(casellesAbans, casellesDespres);}
					case "n" ->{casellesDespres = n(casellesAbans, casellesDespres, x);}
					case "o" ->{casellesDespres = o(casellesAbans, casellesDespres, y);}
					case "e" ->{casellesDespres = e(casellesAbans, casellesDespres, y);}
					case "s" ->{casellesDespres = s(casellesAbans, casellesDespres, x);}
					case "c" ->{casellesDespres = c(casellesAbans, casellesDespres, x, y);}
				}

			}
			
		}
		return casellesDespres;
	}
	
	private String posicio(int x, int y, Label[][] casellesDespres)
	{
		if (x==0 && y==0)
		{
			return "no";
		}
		else if (x==casellesDespres.length-1 && y==0)
		{
			return "ne";
		}
		else if (x==0 && y==casellesDespres[x].length-1)
		{
			return "so";
		}
		else if (x==casellesDespres.length-1 && y==casellesDespres[x].length-1)
		{
			return "se";
		}
		else if (y==0)
		{
			return "n";
		}
		else if (x==0)
		{
			return "o";
		}
		else if (x==casellesDespres.length-1)
		{
			return "e";
		}
		else if (y==casellesDespres[x].length-1)
		{
			return "s";
		}
		else
		{
			return "c";
		}
	}

	private Label[][] no(Label[][] casellesAbans, Label[][] casellesDespres) {
		String dreta = casellesAbans[1][0].getStyle();
		String baix = casellesAbans[0][1].getStyle();
		String baixDreta = casellesAbans[1][1].getStyle();
		
		String actual = casellesAbans[0][0].getStyle();
		
		int veines = 0;
		if (baix.equals("-fx-background-color:white;") || baix.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (dreta.equals("-fx-background-color:white;") || dreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (baixDreta.equals("-fx-background-color:white;") || baixDreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		
		if(actual.equals("-fx-background-color:white;") || actual.equals("-fx-background-color:rgb(202, 202, 202);"))
		{
			if(veines < 2 || veines > 3)
			{
				casellesDespres[0][0].setStyle("-fx-background-color:rgb(37, 37, 37);");
				totalMortes++;
			}
			else
			{
				casellesDespres[0][0].setStyle("-fx-background-color:white;");
				celules++;
			}
		}
		else
		{
			if(veines == 3)
			{
				casellesDespres[0][0].setStyle("-fx-background-color:rgb(202, 202, 202);");
				celules++;
				totalCreades++;
			}
			else
			{
				casellesDespres[0][0].setStyle("-fx-background-color:black;");
			}
		}
		
		return casellesDespres;
	}
	private Label[][] ne(Label[][] casellesAbans, Label[][] casellesDespres) {
		String esquerra = casellesAbans[casellesAbans.length-2][0].getStyle();
		String baix = casellesAbans[casellesAbans.length-1][1].getStyle();
		String baixEsquerra = casellesAbans[casellesAbans.length-2][1].getStyle();
		
		String actual = casellesAbans[casellesAbans.length-1][0].getStyle();

		int veines = 0;
		if (baix.equals("-fx-background-color:white;") || baix.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (esquerra.equals("-fx-background-color:white;") || esquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (baixEsquerra.equals("-fx-background-color:white;") || baixEsquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		
		if(actual.equals("-fx-background-color:white;") || actual.equals("-fx-background-color:rgb(202, 202, 202);"))
		{
			if(veines < 2 || veines > 3)
			{
				casellesDespres[casellesDespres.length-1][0].setStyle("-fx-background-color:rgb(37, 37, 37);");
				totalMortes++;
			}
			else
			{
				casellesDespres[casellesDespres.length-1][0].setStyle("-fx-background-color:white;");
				celules++;
			}
		}
		else
		{
			if(veines == 3)
			{
				casellesDespres[casellesDespres.length-1][0].setStyle("-fx-background-color:rgb(202, 202, 202);");
				totalCreades++;
				celules++;
			}
			else
			{
				casellesDespres[casellesDespres.length-1][0].setStyle("-fx-background-color:black;");

			}
		}
		
		return casellesDespres;
	}
	private Label[][] so(Label[][] casellesAbans, Label[][] casellesDespres) {
		String dreta = casellesAbans[1][casellesAbans[1].length-1].getStyle();
		String dalt = casellesAbans[0][casellesAbans[0].length-2].getStyle();
		String daltDreta = casellesAbans[1][casellesAbans.length-2].getStyle();
		
		String actual = casellesAbans[0][casellesAbans[0].length-1].getStyle();
		
		int veines = 0;
		if (dalt.equals("-fx-background-color:white;") || dalt.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (dreta.equals("-fx-background-color:white;") || dreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (daltDreta.equals("-fx-background-color:white;") || daltDreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		
		if(actual.equals("-fx-background-color:white;") || actual.equals("-fx-background-color:rgb(202, 202, 202);"))
		{
			if(veines < 2 || veines > 3)
			{
				casellesDespres[0][casellesDespres[0].length-1].setStyle("-fx-background-color:rgb(37, 37, 37);");
				totalMortes++;
			}
			else
			{
				casellesDespres[0][casellesDespres[0].length-1].setStyle("-fx-background-color:white;");
				celules++;
			}
		}
		else
		{
			if(veines == 3)
			{
				casellesDespres[0][casellesDespres[0].length-1].setStyle("-fx-background-color:rgb(202, 202, 202);");
				totalCreades++;
				celules++;
			}
			else
			{
				casellesDespres[0][casellesDespres[0].length-1].setStyle("-fx-background-color:black;");
			}
		}
		
		return casellesDespres;
	}
	private Label[][] se(Label[][] casellesAbans, Label[][] casellesDespres) {
		String esquerra = casellesAbans[casellesAbans.length-2][casellesAbans[casellesAbans.length-2].length-1].getStyle();
		String dalt = casellesAbans[casellesAbans.length-1][casellesAbans[casellesAbans.length-1].length-2].getStyle();
		String daltEsquerra = casellesAbans[casellesAbans.length-2][casellesAbans[casellesAbans.length-2].length-2].getStyle();
		
		String actual = casellesAbans[casellesAbans.length-1][casellesAbans[casellesAbans.length-1].length-1].getStyle();

		int veines = 0;
		if (dalt.equals("-fx-background-color:white;") || dalt.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (esquerra.equals("-fx-background-color:white;") || esquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (daltEsquerra.equals("-fx-background-color:white;") || daltEsquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		
		if(actual.equals("-fx-background-color:white;") || actual.equals("-fx-background-color:rgb(202, 202, 202);"))
		{
			if(veines < 2 || veines > 3)
			{
				casellesDespres[casellesDespres.length-1][casellesDespres[casellesDespres.length-1].length-1].setStyle("-fx-background-color:rgb(37, 37, 37);");
				totalMortes++;
			}
			else
			{
				casellesDespres[casellesDespres.length-1][casellesDespres[casellesDespres.length-1].length-1].setStyle("-fx-background-color:white;");
				celules++;
			}
		}
		else
		{
			if(veines == 3)
			{
				casellesDespres[casellesDespres.length-1][casellesDespres[casellesDespres.length-1].length-1].setStyle("-fx-background-color:rgb(202, 202, 202);");
				totalCreades++;
				celules++;
			}
			else
			{
				casellesDespres[casellesDespres.length-1][casellesDespres[casellesDespres.length-1].length-1].setStyle("-fx-background-color:black;");
			}
		}

		return casellesDespres;
	}
	
	private Label[][] n(Label[][] casellesAbans, Label[][] casellesDespres, int x)
	{
		String esquerra = casellesAbans[x-1][0].getStyle();
		String baix = casellesAbans[x][1].getStyle();
		String dreta = casellesAbans[x+1][0].getStyle();
		String baixEsquerra = casellesAbans[x-1][1].getStyle();
		String baixDreta = casellesAbans[x+1][1].getStyle();
		
		String actual = casellesAbans[x][0].getStyle();

		int veines = 0;
		if (baix.equals("-fx-background-color:white;") || baix.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (esquerra.equals("-fx-background-color:white;") || esquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (dreta.equals("-fx-background-color:white;") || dreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (baixEsquerra.equals("-fx-background-color:white;") || baixEsquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (baixDreta.equals("-fx-background-color:white;") || baixDreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		
		if(actual.equals("-fx-background-color:white;") || actual.equals("-fx-background-color:rgb(202, 202, 202);"))
		{
			if(veines < 2 || veines > 3)
			{
				casellesDespres[x][0].setStyle("-fx-background-color:rgb(37, 37, 37);");
				totalMortes++;
			}
			else
			{
				casellesDespres[x][0].setStyle("-fx-background-color:white;");
				celules++;
			}
		}
		else
		{
			if(veines == 3)
			{
				casellesDespres[x][0].setStyle("-fx-background-color:rgb(202, 202, 202);");
				totalCreades++;
				celules++;
			}
			else
			{
				casellesDespres[x][0].setStyle("-fx-background-color:black;");
			}
		}

		return casellesDespres;
	}
	private Label[][] s(Label[][] casellesAbans, Label[][] casellesDespres, int x)
	{
		String esquerra = casellesAbans[x-1][casellesAbans[x].length-1].getStyle();
		String dalt = casellesAbans[x][casellesAbans[x].length-2].getStyle();
		String dreta = casellesAbans[x+1][casellesAbans[x].length-1].getStyle();
		String daltEsquerra = casellesAbans[x-1][casellesAbans[x].length-2].getStyle();
		String daltDreta = casellesAbans[x+1][casellesAbans[x].length-2].getStyle();
		
		String actual = casellesAbans[x][casellesAbans[x].length-1].getStyle();

		int veines = 0;
		if (dalt.equals("-fx-background-color:white;") || dalt.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (esquerra.equals("-fx-background-color:white;") || esquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (dreta.equals("-fx-background-color:white;") || dreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (daltEsquerra.equals("-fx-background-color:white;") || daltEsquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (daltDreta.equals("-fx-background-color:white;") || daltDreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		
		if(actual.equals("-fx-background-color:white;") || actual.equals("-fx-background-color:rgb(202, 202, 202);"))
		{
			if(veines < 2 || veines > 3)
			{
				casellesDespres[x][casellesDespres[x].length-1].setStyle("-fx-background-color:rgb(37, 37, 37);");
				totalMortes++;
			}
			else
			{
				casellesDespres[x][casellesDespres[x].length-1].setStyle("-fx-background-color:white;");
				celules++;
			}
		}
		else
		{
			if(veines == 3)
			{
				casellesDespres[x][casellesDespres[x].length-1].setStyle("-fx-background-color:rgb(202, 202, 202);");
				totalCreades++;
				celules++;
			}
			else
			{
				casellesDespres[x][casellesDespres[x].length-1].setStyle("-fx-background-color:black;");
			}
		}

		return casellesDespres;
	}
	private Label[][] e(Label[][] casellesAbans, Label[][] casellesDespres, int y)
	{
		String esquerra = casellesAbans[casellesAbans.length-2][y].getStyle();
		String baix = casellesAbans[casellesAbans.length-1][y+1].getStyle();
		String dalt = casellesAbans[casellesAbans.length-1][y-1].getStyle();
		String baixEsquerra = casellesAbans[casellesAbans.length-2][y+1].getStyle();
		String daltEsquerra = casellesAbans[casellesAbans.length-2][y-1].getStyle();
		
		String actual = casellesAbans[casellesAbans.length-1][y].getStyle();

		int veines = 0;
		if (dalt.equals("-fx-background-color:white;") || dalt.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (baix.equals("-fx-background-color:white;") || baix.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (esquerra.equals("-fx-background-color:white;") || esquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (daltEsquerra.equals("-fx-background-color:white;") || daltEsquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (baixEsquerra.equals("-fx-background-color:white;") || baixEsquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		
		if(actual.equals("-fx-background-color:white;") || actual.equals("-fx-background-color:rgb(202, 202, 202);"))
		{
			if(veines < 2 || veines > 3)
			{
				casellesDespres[casellesDespres.length-1][y].setStyle("-fx-background-color:rgb(37, 37, 37);");
				totalMortes++;
			}
			else
			{
				casellesDespres[casellesDespres.length-1][y].setStyle("-fx-background-color:white;");
				celules++;
			}
		}
		else
		{
			if(veines == 3)
			{
				casellesDespres[casellesDespres.length-1][y].setStyle("-fx-background-color:rgb(202, 202, 202);");
				totalCreades++;
				celules++;
			}
			else
			{
				casellesDespres[casellesDespres.length-1][y].setStyle("-fx-background-color:black;");
			}
		}

		return casellesDespres;
	}
	private Label[][] o(Label[][] casellesAbans, Label[][] casellesDespres, int y)
	{
		String dreta = casellesAbans[1][y].getStyle();
		String dalt = casellesAbans[0][y-1].getStyle();
		String baix = casellesAbans[0][y+1].getStyle();
		String daltDreta = casellesAbans[1][y-1].getStyle();
		String baixDreta = casellesAbans[1][y+1].getStyle();
		
		String actual = casellesAbans[0][y].getStyle();

		int veines = 0;
		if (dalt.equals("-fx-background-color:white;") || dreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (baix.equals("-fx-background-color:white;") || dreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (dreta.equals("-fx-background-color:white;") || dreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (daltDreta.equals("-fx-background-color:white;") || dreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (baixDreta.equals("-fx-background-color:white;") || dreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		
		if(actual.equals("-fx-background-color:white;") || dreta.equals("-fx-background-color:rgb(202, 202, 202);"))
		{
			if(veines < 2 || veines > 3)
			{
				casellesDespres[0][y].setStyle("-fx-background-color:rgb(37, 37, 37);");
				totalMortes++;
			}
			else
			{
				casellesDespres[0][y].setStyle("-fx-background-color:white;");
				celules++;
			}
		}
		else
		{
			if(veines == 3)
			{
				casellesDespres[0][y].setStyle("-fx-background-color:rgb(202, 202, 202);");
				totalCreades++;
				celules++;
			}
			else
			{
				casellesDespres[0][y].setStyle("-fx-background-color:black;");
			}
		}

		return casellesDespres;
	}
	
	private Label[][] c(Label[][] casellesAbans, Label[][] casellesDespres, int x, int y)
	{
		String dalt = casellesAbans[x][y-1].getStyle();
		String baix= casellesAbans[x][y+1].getStyle();
		String esquerra = casellesAbans[x-1][y].getStyle();
		String dreta = casellesAbans[x+1][y].getStyle();
		String daltEsquerra= casellesAbans[x-1][y-1].getStyle();
		String daltDreta = casellesAbans[x+1][y-1].getStyle();
		String baixEsquerra = casellesAbans[x-1][y+1].getStyle();
		String baixDreta = casellesAbans[x+1][y+1].getStyle();
		
		String actual = casellesAbans[x][y].getStyle();

		int veines = 0;
		if (dalt.equals("-fx-background-color:white;") || dalt.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (baix.equals("-fx-background-color:white;") || baix.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (esquerra.equals("-fx-background-color:white;") || esquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (dreta.equals("-fx-background-color:white;") || dreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (daltEsquerra.equals("-fx-background-color:white;") || daltEsquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (daltDreta.equals("-fx-background-color:white;") || daltDreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (baixEsquerra.equals("-fx-background-color:white;") || baixEsquerra.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		if (baixDreta.equals("-fx-background-color:white;") || baixDreta.equals("-fx-background-color:rgb(202, 202, 202);")) veines++;
		
		if(actual.equals("-fx-background-color:white;") || actual.equals("-fx-background-color:rgb(202, 202, 202);"))
		{
			if(veines < 2 || veines > 3)
			{
				casellesDespres[x][y].setStyle("-fx-background-color:rgb(37, 37, 37);");
				totalMortes++;
			}
			else
			{
				casellesDespres[x][y].setStyle("-fx-background-color:white;");
				celules++;
			}
		}
		else
		{
			if(veines == 3)
			{
				casellesDespres[x][y].setStyle("-fx-background-color:rgb(202, 202, 202);");
				totalCreades++;
				celules++;
			}
			else
			{
				casellesDespres[x][y].setStyle("-fx-background-color:black;");
			}
		}
		
		return casellesDespres;
	}
}
