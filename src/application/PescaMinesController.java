package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class PescaMinesController {

    //Botons del menu principal per triar la dificultat
    @FXML private Button idfacil, idmitja, iddificil;

    
   //Genera un GridPane que es el quadrat que dependra la mida de la dificultat
    @FXML private GridPane quadratDfacil;
    @FXML private GridPane quadratDmitja;
    @FXML private GridPane quadratDdificil;

    
    //Temps del joc
    @FXML private Label ComptadorTempsJoc;
    
    private int tempstranscurrit = 0;     
    
    private Timeline temporitzador;        

    
    //Informació en  pantalla
    @FXML private Label casellesDescobertes;    
    @FXML private Label casellesperDescobrir;
    @FXML private Label MinesRestants;         
    @FXML private Label Banderespossades;      

    
    
    //Logica del joc
    private boolean[][] Mina;

    private boolean[][] casellaDescoberta;

    private boolean[][] Bandera;

    private int filesdelquadrat, columnesdelquadrat, totalMines;
    
    private Random aleatori = new Random();
    
    private Button[][] botoCaselles;

    private boolean primerClic = true;
    
    
    //Dades
    private String nivellElegit = "";
    
    
    
    private Usuari u;

	public void setUsuari(Usuari u) {
		this.u = u;
	}

	public Usuari getUsuari() {
		return this.u;
	}
    
    //Donar fi
	private boolean partidaAcabada = false;
	
	//posicions i temps dels tres en el ranking
    @FXML private Label primer;
    
    @FXML private Label tempsPrimer;
    @FXML private Label segon;
    
    @FXML private Label tempsSegon;
    @FXML private Label tercer;
    @FXML private Label tempsTercer;
  
    
    @FXML
    public void initialize() {
    	
        // Inicializació del usuari   	
        Platform.runLater(() -> {
        	
        	
            try {
                if (u == null) {
                	
                    Stage stage = (Stage) ComptadorTempsJoc.getScene().getWindow();
                    Usuari usuariRecuperat = (Usuari) stage.getUserData();
                    if (usuariRecuperat != null) this.u = usuariRecuperat;
                }
                
            } catch (Exception e) {

            }
        });
          
        // Comprobar si cada element existeix o no per a que funcione i entrem al quadrat corresponent
        if (idfacil != null && idmitja != null && iddificil != null) {
            nivellElegit = "facil";
            idfacil.setOnAction(e -> accedirQuadratJoc("facil.fxml", "Pescamines dificultat Fàcil", e));
            
            nivellElegit = "mitja";
            idmitja.setOnAction(e -> accedirQuadratJoc("mitja.fxml", "Pescamines dificultat Mitjà", e));
            
            nivellElegit = "dificil";
            iddificil.setOnAction(e -> accedirQuadratJoc("dificil.fxml", "Pescamines dificultat Difícil", e));
        }        
        
        
        
        if (quadratDfacil != null) {
            crearQuadratJoc(quadratDfacil, 9, 9, 10); 
        }
        if (quadratDmitja != null) {
            crearQuadratJoc(quadratDmitja, 16, 16, 40); 
        }
        if (quadratDdificil != null) {
            crearQuadratJoc(quadratDdificil, 16, 30, 99);
        }    
    }

    

   //Accedir a la nova finestra amb el quadrat de la dificultat elegida carregant i mostrant finestra
    private void accedirQuadratJoc(String fxml, String titol, ActionEvent e) {
    	
        try {
        	
        	Parent root = FXMLLoader.load(getClass().getResource(fxml));

            Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

            Scene escena = new Scene(root);

            window.setScene(escena);
            window.setTitle(titol);
           
            
            window.show();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


   //Creacio dels botons que cada una representa casella del joc
    private void crearQuadratJoc(GridPane taula, int files, int columnes, int numerodeMines) {
        
    	//reiniciar estat de la partida
    	 partidaAcabada = false;
    	
    	//Dimensions del quadrat/tablero i les mines definides més a dalt en els if
    	filesdelquadrat = files;
    	
    	columnesdelquadrat = columnes;
    	
    	totalMines = numerodeMines;
    	
    	
    	//Matrius
    	Mina = new boolean[files][columnes];
    	casellaDescoberta = new boolean[files][columnes];
    	Bandera = new boolean [files][columnes];
    	
        botoCaselles = new Button[files][columnes]; 
    	
        
        primerClic = true;
    	

    	    //Eliminar tot el que hi haja de antes
    	  taula.getChildren().clear();
    	  
    	  
          //espai en horizontal entre columnes
          taula.setHgap(1);
          
          //espai en vertical entre files
          taula.setVgap(1);
          
          
          taula.setStyle("-fx-background-color: #222;");

          
          //Per cada posició del quadrat crea un boto i se afegeix
          for (int fila = 0; fila < files; fila++) {
        	  
              for (int columna = 0; columna < columnes; columna++) {
            	  
                  Button boto = new Button();
                  boto.setPrefSize(35, 35);
                  
                  boto.setStyle("-fx-background-color: #d3d3d3; -fx-border-color: #888; -fx-border-width: 1;");
                  
                  botoCaselles[fila][columna] = boto;
                  
                  final int filaCasella = fila;
                  final int columnaCasella = columna;
        
                  
                  //Clic esquerre per descobrir casella

                  boto.setOnMouseClicked(e -> {
                      if (e.getButton() == MouseButton.PRIMARY) {
                    	  
                          if (!Bandera[filaCasella][columnaCasella] && !casellaDescoberta[filaCasella][columnaCasella]) {
                        	  
                              descobrirCasella(taula, filaCasella, columnaCasella);
                          }
                      }
                      
                  //Clic dret per possar o llevar la bandera
                      else if (e.getButton() == MouseButton.SECONDARY) {
                    	  
                          if (!casellaDescoberta[filaCasella][columnaCasella]) {
                        	  
                              Bandera[filaCasella][columnaCasella] = !Bandera[filaCasella][columnaCasella];
                              actualitzartaula(taula);
                              actualitzarInfoPM();

                          }
                      }
                  });

                  taula.add(boto, columna, fila);
        
              	}
          }
          //Inicia el temps, es actualitza la taula/quadrat, i la informació
          actualitzartaula(taula);
          actualitzarInfoPM();
          iniciarTemporitzador();
          
    }
    
    
    	private void descobrirCasella(GridPane taula, int fila, int columna) {
    		
    		if(primerClic) {
    			
    			possarMines(fila,columna);
    			primerClic = false;
    			
    		}
    		
    		casellaDescoberta[fila][columna]=true;
    		
    		Button boto = botoCaselles[fila][columna];    		
    	
        	//Si hi ha mina mostra la bomba i acaba la partida
            if (Mina[fila][columna]) {
            	
                boto.setStyle("-fx-background-color: red;");
                
                Image imatgeBomba = new Image(getClass().getResource("/img/bomba.png").toExternalForm());
                
                ImageView imatgeBombavista= new ImageView(imatgeBomba);
                
                imatgeBombavista.setFitWidth(15);
                imatgeBombavista.setFitHeight(15);
                boto.setGraphic(imatgeBombavista);
                
                
                boto.setDisable(false);
                
                mostrartotesLesMines(taula);
                bloqueigIfinal();
                pararTemporitzador();
                actualitzarInfoPM();
                

                //Tindre la finestra principal 
                Stage stage = (Stage) botoCaselles[0][0].getScene().getWindow();
                
                boolean maxim = stage.isMaximized();

                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
                    "Has perdut la partida, vols tornar a jugar? Et portarem al menú per si vols seleccionar altra dificultat", 
                    ButtonType.YES, ButtonType.NO);

                alert.initOwner(stage);
                
                //Modal/Prioritat respecte la finestra principal
                alert.initModality(Modality.WINDOW_MODAL);

                alert.showAndWait().ifPresent(response -> {
                	
                    if (response == ButtonType.YES) {

                    	stage.close();

                        //Obrir el menu de dificultats altra volta
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("PescaMines.fxml"));
                            Parent root = loader.load();
                            Stage menuStage = new Stage();
                            menuStage.setScene(new Scene(root));
                            
                            menuStage.setTitle("Menu dificultat Pescamines");
                            
                            menuStage.setMaximized(maxim); 
                            menuStage.show();
                            
                        } catch (IOException e) {
                        	
                            e.printStackTrace();
                        }
                        
                        //Elegis no
                    } else {

                    	stage.close();
                    }
                });
           
                return;
      
            }
            

            int mines = contarMines(fila, columna);     
            
            //Si hi ha mines mostra el numero
            if (mines > 0) {
            	
                boto.setText(String.valueOf(mines));
                
                
            } else {
            	
                boto.setText("");
                //EXPANDIR
                
                
                //Direccions ortogonals per comprobar cada casella

                //Dalt
                if (casellaExpandible(fila-1, columna)) descobrirCasella(taula, fila-1, columna);
                
                //Baix
                if (casellaExpandible(fila+1, columna)) descobrirCasella(taula, fila+1, columna);
                
                //esquerra
                if (casellaExpandible(fila, columna-1)) descobrirCasella(taula, fila, columna-1);
                
                //detra
                if (casellaExpandible(fila, columna+1)) descobrirCasella(taula, fila, columna+1);
               
                
                //Diagonal dalt esquerra
                if (casellaExpandible(fila-1, columna-1)) descobrirCasella(taula, fila-1, columna-1);

                //diagonal dalt detra
                if (casellaExpandible(fila-1, columna+1)) descobrirCasella(taula, fila-1, columna+1);

                //diagonal baix esquerra
                if (casellaExpandible(fila + 1, columna -1)) descobrirCasella(taula, fila+1, columna-1);

                //diagonal baix detra
                if (casellaExpandible(fila +1, columna + 1)) descobrirCasella(taula, fila+1, columna +1);

            }
            
            actualitzartaula(taula);
            actualitzarInfoPM();
            casellesDescobertesSenseMines();
    	}
    	
    	
    	private void possarMines(int filaInicial, int columnaInicial) {
    		
    		int minesPosades = 0;
    		
    		
    		while(minesPosades < totalMines) {
    			
    			
    			int filaAleatoria = aleatori.nextInt(filesdelquadrat);
    			int columnaAleatoria = aleatori.nextInt(columnesdelquadrat);
    			
    			
    	        //Evitar possar mina a la casella inicial/el primer clic i evitar repetir mina
    			if ((filaAleatoria != filaInicial || columnaAleatoria != columnaInicial) && !Mina[filaAleatoria][columnaAleatoria]) {
    	            Mina[filaAleatoria][columnaAleatoria] = true;
    	            
    	            minesPosades++;
    			}
    		}
    	}
    	
    	// Comproba si la casella esta dins dels límits del quadrat
    	//no està descoberta i no té mina
    	private boolean casellaExpandible(int fila, int columna) {
    		
    		
        	//Retorna true si la casella es pot expandir
    	    return fila >= 0 && fila < filesdelquadrat && columna >= 0 && columna < columnesdelquadrat &&
    	           !casellaDescoberta[fila][columna] &&
    	           
    	           !Mina[fila][columna];    
    	}
    	
    	
        //Contar quantes mines hi ha alrededor de la casella 
        private int contarMines(int fila, int columna) {
            int totaldeMines = 0;

         //Dalt
            if (fila > 0 && Mina[fila-1][columna]) totaldeMines++;

            //Baix
            if (fila < filesdelquadrat-1 && Mina[fila+1][columna]) totaldeMines++;

            //Esquerra
            if (columna > 0 && Mina[fila][columna-1]) totaldeMines++;

            //Dreta
            if (columna < columnesdelquadrat-1 && Mina[fila][columna+1]) totaldeMines++;

            //Diagonal dalt esquerra
            if (fila > 0 && columna > 0 && Mina[fila-1][columna-1]) totaldeMines++;

            //Diagonal dalt dreta
            if (fila > 0 && columna < columnesdelquadrat-1 && Mina[fila-1][columna+1]) totaldeMines++;

            //Diagonal baix esquerra
            if (fila < filesdelquadrat-1 && columna > 0 && Mina[fila+1][columna-1]) totaldeMines++;

            //Diagonal baix dreta
            if (fila < filesdelquadrat-1 && columna < columnesdelquadrat-1 && Mina[fila+1][columna+1]) totaldeMines++;

            return totaldeMines;
        }
        
        //Mostrar mines quant es perd la partida
        private void mostrartotesLesMines(GridPane taula) {
        	
    	    //Recorrer totes les files i columnes del quadrat per trobar i mostrar les mines
            for (int fila = 0; fila < filesdelquadrat; fila++) { 
            	
                for (int columna = 0; columna < columnesdelquadrat; columna++) {
                	
                    if (Mina[fila][columna]) {
                    	
                    	
                        Button boto = botoCaselles[fila][columna];
                        Image imatgeBomba = new Image(getClass().getResource("/img/bomba.png").toExternalForm());
                        ImageView imatgeBombaView = new ImageView(imatgeBomba);
                        
                        imatgeBombaView.setFitWidth(15);
                        
                        imatgeBombaView.setFitHeight(15);
                        
                        boto.setGraphic(imatgeBombaView);
                        boto.setDisable(false);
                        boto.setStyle("-fx-background-color: red;");
                    }
                }
            }
        }
        
        //Actualitza les caselles depenent de com estiga el quadrat/taula
        private void actualitzartaula(GridPane taula) {
        	
        	
            for (int fila = 0; fila < filesdelquadrat; fila++) {
            	
            	
                for (int columna = 0; columna < columnesdelquadrat; columna++) {
                	
                    Button boto = botoCaselles[fila][columna];
                    boto.setGraphic(null);
                    if (casellaDescoberta[fila][columna]) {
                    	
                        // Si la casella esta descoberta mostra el número o res
                        boto.setDisable(true);
                        
                        int mines = contarMines(fila, columna);
                        boto.setText(mines > 0 ? String.valueOf(mines) : "");
                        boto.setStyle("-fx-background-color: white; -fx-border-color: #888; -fx-border-width: 1;");
                    } else if (Bandera[fila][columna]) {
                    	
                    	
                        Image imatge = new Image(getClass().getResource("/img/bandera.png").toExternalForm());
                        ImageView imatgeBandera = new ImageView(imatge);
                        imatgeBandera.setFitWidth(15);   
                        imatgeBandera.setFitHeight(15);
                        boto.setGraphic(imatgeBandera);
                        boto.setDisable(false);
                        boto.setStyle("-fx-background-color: #d3d3d3; -fx-border-color: #888; -fx-border-width: 1;");
                        
                        
                    } else {
                    	
                        //Casella normal que no es descoberta ni bandera
                        boto.setText("");
                        boto.setDisable(false);
                        boto.setStyle("-fx-background-color: #d3d3d3; -fx-border-color: #888; -fx-border-width: 1;");
                    }
                }
            }
        }

        private void bloqueigIfinal() {
        	
        	
            for (int fila = 0; fila < filesdelquadrat; fila++) {
            	
            	
                for (int columna = 0; columna < columnesdelquadrat; columna++) {
                	
                	
                    botoCaselles[fila][columna].setDisable(true);
                }
            }
        }

        private void iniciarTemporitzador() {
        	
        	
            tempstranscurrit = 0; 
            
            if (temporitzador != null) temporitzador.stop(); 
            
            temporitzador = new Timeline(
            	    //Creacio de KeyFrame que se executa de cada segon
                new KeyFrame(Duration.seconds(1), event -> {
                    tempstranscurrit++; 
                    
                    //Calcul de hores dividint segons totals entre 3600
                    int hores = tempstranscurrit / 3600;
                    
                    // Calcul de minuts despres de llevar les hores
                    int minuts = (tempstranscurrit % 3600)/60;
                    
                    //Calcul dels segons despres de llevar hores y minutss
                    int segons = tempstranscurrit %60;
                    
                    
                    //Creacio cadena de text amb el temps format  
                    String tempsgrafic = "Temps: " +hores+ ":" +minuts+ ":" +segons;
                    
                    
                    ComptadorTempsJoc.setText(tempsgrafic);
                })
            );
            
            temporitzador.setCycleCount(Timeline.INDEFINITE);
            temporitzador.play(); 
        }

        
        private void pararTemporitzador() {
            if (temporitzador != null) {
                temporitzador.stop();
            }
        }
        
        
        private void actualitzarInfoPM() {
        	
        	
            int descobertes = 0;
            int banderes = 0;
            int restants = filesdelquadrat*columnesdelquadrat-totalMines-contadorCasellesDescobertesSenseMines();
            
            for (int fila = 0; fila < filesdelquadrat; fila++) {
            	
                for (int columna = 0; columna < columnesdelquadrat; columna++) {
                	
                	
                    if (casellaDescoberta[fila][columna]) descobertes++;
                    if (Bandera[fila][columna]) banderes++;
                }
            }

            
            if (casellesDescobertes != null) 
            	
                casellesDescobertes.setText("Descobertes: " +descobertes);
            
            
            
            if (casellesperDescobrir != null)
            	
                casellesperDescobrir.setText("Restants: " +restants);
            
            
            if (MinesRestants != null)
            	
                MinesRestants.setText("Mines: " +totalMines);
            
            if (Banderespossades != null)
            	
                Banderespossades.setText("Banderes: " +banderes);
            
        }

        
        private int contadorCasellesDescobertesSenseMines() {
        	
            int contador = 0;
            for (int fila = 0; fila < filesdelquadrat; fila++) {
            	
                //Comprobar si la casella ha sigut descoberta i no té mina
                for (int columna = 0; columna < columnesdelquadrat; columna++) {
                	
                    if (casellaDescoberta[fila][columna] && !Mina[fila][columna]) {
                    	
                //Incrementar el contador de caselles descobertes sense mina
                        contador++;
                    }
                }
            }
            return contador;
        }

        private void casellesDescobertesSenseMines() {
        	
            int total = filesdelquadrat * columnesdelquadrat - totalMines;
            if (contadorCasellesDescobertesSenseMines() == total) {
            	
                victoria();
            }
        }

        

private void victoria() {
	
    if (partidaAcabada) return; 
    
    partidaAcabada = true;
	
        pararTemporitzador();
        bloqueigIfinal();
        
        for (int fila = 0; fila < filesdelquadrat; fila++)
        	
            for (int columna = 0; columna < columnesdelquadrat; columna++)
            	
                if (!Mina[fila][columna]) {
                	
                    botoCaselles[fila][columna].setStyle("-fx-background-color: lightgreen;");
                    botoCaselles[fila][columna].setDisable(true);
                    
                }
        //inicialitzat el usuari
        if (u == null) {
        	
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Usuari no inicialitzat");
            alert.setContentText("El usuari actual no està inicialitzat, inicia sessio perfavor");
            
            alert.showAndWait();
            return;
        }
        
        mostrarRanking(u.getId(), tempstranscurrit, nivellElegit);
    }



public void mostrarRanking(int idJugador, int temps, String dificultat) {
	
        try (Connection c = DriverManager.getConnection(
                "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04", "root", "root")) {

            String sql = "INSERT INTO Pescamines (id, temps, nivell) VALUES (?, ?, ?)";
            
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, idJugador);
            ps.setInt(2, temps);
            ps.setString(3, dificultat);
            
            ps.executeUpdate();

            String sqlTop = "SELECT id, temps FROM Pescamines WHERE nivell = ? ORDER BY temps ASC LIMIT 3";
            PreparedStatement psTop = c.prepareStatement(sqlTop);
            
            
            psTop.setString(1, dificultat);
            ResultSet rs = psTop.executeQuery();

            //Per saber si està en el top
            boolean esTop = false;
            
            //Contador de posicio
            int posicio = 1;
            
            //Array que almacena els tres id's primers
            int[] ids = new int[3];
            
            //Array que almacena els temps dels primers
            int[] tempsResultat = new int[3];

            //Iterar i guardar dels tres millors i guardar id's i guardar temps
            while (rs.next() && posicio <= 3) {
            	
                ids[posicio-1] = rs.getInt("id");
                tempsResultat[posicio -1] = rs.getInt("temps");
                
                if (ids[posicio -1] == idJugador && tempsResultat[posicio-1] == temps) esTop = true;
                
                posicio++;
            }

            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/RANKING.fxml"));
            Parent root = loader.load();
            
            Scene escena = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(escena);
            
            stage.setTitle("Ranking de PescaMines");
            stage.setWidth(1200);
            stage.setHeight(800);
            stage.show();
            RankingController rankingController = loader.getController();
            
            rankingController.top3(
            		
            	    nomPerId(ids[0]), tempsResultat[0],
            	    nomPerId(ids[1]), tempsResultat[1],
            	    nomPerId(ids[2]), tempsResultat[2]
            	);


            if (esTop) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
                alert.setTitle("Enhorabona");
                alert.setHeaderText("Estàs en el top dels tres millors");
                alert.setContentText("El teu temps està entre els millors");
                alert.showAndWait();
                
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
                alert.setTitle("Ranking");
                alert.setHeaderText("NO estàs en el top dels tres millors");
                alert.setContentText("El teu temps no es millor que els altres tres");
                alert.showAndWait();
            }

        } catch (Exception e) {
        	
            e.printStackTrace();
            
            Platform.runLater(() -> {
            	
                Alert alert = new Alert(Alert.AlertType.ERROR);
                
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error al mostrar el ranking: " + e.getMessage());
                alert.showAndWait();
            });
        }
    }

private String nomPerId(int id) {
	
    if (id == 0) return "-";
    
    try (Connection c = DriverManager.getConnection(
            "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04", "root", "root")) {
        String sql = "SELECT Nom FROM Usuaris WHERE id = ?";
        
        
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        
        ResultSet rs = ps.executeQuery();
        
        //Si hi ha un resultat torna el nom del jugador
        
        if (rs.next()) {
        	
            return rs.getString("Nom");
            
         //sino el torna amb el seu id   
        } else {
        	
            return "Jugador " + id;
        }
        
        //Per si hi ha problemes amb la connexió
    } catch (Exception e) {
    	
        return "Jugador " + id;
    }
}


}