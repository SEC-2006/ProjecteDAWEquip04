package application;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
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
    private String usuari = "";
    private String nivellElegit = "";
    
    
    @FXML
    public void initialize() {
      
    	
      //Comprobar si cada element existeix o no per a que funcione i entrem al quadrat corresponent
        if (idfacil != null && idmitja != null && iddificil != null) {
        	
        	
            nivellElegit = "facil";
            idfacil.setOnAction(e -> accedirQuadratJoc("facil.fxml", "Pescamines dificultat Fàcil"));
            
            nivellElegit = "mitja";
            idmitja.setOnAction(e -> accedirQuadratJoc("mitja.fxml", "Pescamines dificultat Mitjà"));
            
            nivellElegit = "dificil";
            iddificil.setOnAction(e -> accedirQuadratJoc("dificil.fxml", "Pescamines dificultat Difícil"));
       
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
    private void accedirQuadratJoc(String fxml, String titol) {
        try {
        	
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage escena = new Stage();
            
            escena.setTitle(titol);
            escena.setScene(new Scene(root));
            escena.show();
            
        } catch (Exception e) {
        	
            e.printStackTrace();
        }
        
    }
    

   //Creacio dels botons que cada una representa casella del joc
    private void crearQuadratJoc(GridPane taula, int files, int columnes, int numerodeMines) {
        
    	
    	//Dimensions del quadrat/tablero i les mines
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
                //boto.setText("💣"); 
                Image imatgeBomba = new Image(getClass().getResource("/img/bombaBOOM.png").toExternalForm());
                ImageView imatgeBombaView = new ImageView(imatgeBomba);
                
                imatgeBombaView.setFitWidth(15);
                imatgeBombaView.setFitHeight(15);
                

                boto.setGraphic(imatgeBombaView);
                boto.setDisable(false);

                
                
                mostrartotesLesMines(taula);
                bloqueigIfinal();
                pararTemporitzador();
                actualitzarInfoPM();

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


             // Direcciones diagonals/esquines de alrededor

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
    	    return fila >= 0 && fila < filesdelquadrat &&columna >= 0 && columna < columnesdelquadrat &&
    	           !casellaDescoberta[fila][columna] &&
    	           !Mina[fila][columna];
    	}



        //Contar quantes mines hi ha alrededor de la casella 
        private int contarMines(int fila, int columna) {
            int totaldeMines = 0;

            // Dalt
            if (fila > 0 && Mina[fila-1][columna]) totaldeMines++;

            // Baix
            if (fila < filesdelquadrat-1 && Mina[fila+1][columna]) totaldeMines++;

            // Esquerra
            if (columna > 0 && Mina[fila][columna-1]) totaldeMines++;

            // Dreta
            if (columna < columnesdelquadrat-1 && Mina[fila][columna+1]) totaldeMines++;

            // Diagonal dalt-esquerra
            if (fila > 0 && columna > 0 && Mina[fila-1][columna-1]) totaldeMines++;

            // Diagonal dalt-dreta
            if (fila > 0 && columna < columnesdelquadrat-1 && Mina[fila-1][columna+1]) totaldeMines++;

            // Diagonal baix-esquerra
            if (fila < filesdelquadrat-1 && columna > 0 && Mina[fila+1][columna-1]) totaldeMines++;

            // Diagonal baix-dreta
            if (fila < filesdelquadrat-1 && columna < columnesdelquadrat-1 && Mina[fila+1][columna+1]) totaldeMines++;

            
            return totaldeMines;
        }

        
        //Mostrar mines quant es perd la partida
        private void mostrartotesLesMines(GridPane taula) {
        	
        	    // Recorrer totes les files i columnes del quadrat per trobar i mostrar les mines
        	    for (int fila = 0; fila < filesdelquadrat; fila++) { 
        	    	
        	        for (int columna = 0; columna < columnesdelquadrat; columna++) {
        	        	
        	            if (Mina[fila][columna]) {
        	            	
        	            	
        	            	 Button boto = botoCaselles[fila][columna];
        	                 //boto.setText("💣"); 
        	            	 Image imatgeBomba = new Image(getClass().getResource("/img/bombaBOOM.png").toExternalForm());
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
                	 
                    //Lleva qualsevol imatge de antes possa
                    boto.setGraphic(null);

                    if (casellaDescoberta[fila][columna]) {
                    	
                        // Si la casella esta descoberta mostra el número o res
                        boto.setDisable(true);
                        
                        int mines = contarMines(fila, columna);
                        
                        boto.setText(mines > 0 ? String.valueOf(mines) : "");
                        
                        boto.setStyle("-fx-background-color: white; -fx-border-color: #888; -fx-border-width: 1;");
                        
                    } else if (Bandera[fila][columna]) {
                      
                        //boto.setText("🚩");
                    	
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
            		
                new KeyFrame(Duration.seconds(1), event -> {
                	
                    tempstranscurrit++; 

                    int hores = tempstranscurrit / 3600;
                    
                    int minuts = (tempstranscurrit % 3600)/60;
                    int segons = tempstranscurrit %60;


                    String tempsgrafic = "Temps: " +hores+ ":" +minuts+ ":" +segons;


                    ComptadorTempsJoc.setText(tempsgrafic);

                })
            );
            
            temporitzador.setCycleCount(Timeline.INDEFINITE);
            
            temporitzador.play(); 
        }

        private void pararTemporitzador() {
        	
            if (temporitzador != null) temporitzador.stop();
            
        }
        
        private void actualitzarInfoPM() {
        	
            int descobertes = 0;
            int banderes = 0;
            int totalCaselles = filesdelquadrat * columnesdelquadrat;
            
            for (int fila = 0; fila < filesdelquadrat; fila++) {
            	
                for (int columna = 0; columna < columnesdelquadrat; columna++) {
                	
                    if (casellaDescoberta[fila][columna]) descobertes++;
                    
                    if (Bandera[fila][columna]) banderes++;
                }
            }
            
            int restants=totalCaselles-descobertes;
            
            if (casellesDescobertes != null) 
            	casellesDescobertes.setText("Descobertes: " +descobertes);
            
            if (casellesperDescobrir != null)
            	
            	casellesperDescobrir.setText("Restants: " +restants);
            
            if (MinesRestants != null)
            	
            	MinesRestants.setText("Mines: " +totalMines);
            
            
            if (Banderespossades != null)
            	
            	Banderespossades.setText("Banderes: " +banderes);
            
    
        }
        
        
        
        private void victoria() {
        	
            pararTemporitzador();
            bloqueigIfinal();
            
            for (int fila = 0; fila < filesdelquadrat; fila++) {
            	
                for (int columna = 0; columna < columnesdelquadrat; columna++) {
                	
                    if (!Mina[fila][columna]) {
                        botoCaselles[fila][columna].setStyle("-fx-background-color: lightgreen;");
                    }
                }
            }

            System.out.println("Felicitats " +usuari+ "! Has conseguit guanyar sense explotar");
            
            System.out.println("Temps transcurrit: " + tempstranscurrit);
            System.out.println("Dificultat elegida: " + nivellElegit);
            System.out.println("Nom del usuari: " +usuari);
        }
    


        private void casellesDescobertesSenseMines() {
        	
            int total = filesdelquadrat * columnesdelquadrat - totalMines;
            int contadordescobertes = 0;

            for (int fila = 0; fila < filesdelquadrat; fila++) {
            	
                for (int columna = 0; columna < columnesdelquadrat; columna++) {
                	
                    //Comprobar si la casella ha sigut descoberta i no té mina
                    if (casellaDescoberta[fila][columna] && !Mina[fila][columna]) {
                    	
                    //Incrementar el contador de caselles descobertes sense mina
                        contadordescobertes++;
                    }
                }
            }

            if (contadordescobertes == total) {
                victoria();
            }
        }

}

        
    


	