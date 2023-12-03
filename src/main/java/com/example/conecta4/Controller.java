    package com.example.conecta4;

    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.scene.layout.GridPane;
    import javafx.scene.paint.Color;
    import javafx.scene.shape.Circle;


    public class Controller {
        @FXML
        private GridPane gridPane;
        private int currentPlayer = 1;
        private final int ROWS = 6;
        private final int COLUMNS = 7;
        private int[][] board = new int[ROWS][COLUMNS];
        @FXML
        private Circle dropCircle;
        @FXML
        private ToggleGroup modoJuego;
        @FXML
        private TextField nombreJugador1, nombreJugador2;
        @FXML
        private Button columnButton0,columnButton1,columnButton2,columnButton3,columnButton4,columnButton5,columnButton6;

        @FXML
        private void handleIniciarPartidaButtonClick(ActionEvent event) {
            columnButton0.setDisable(false);
            columnButton1.setDisable(false);
            columnButton2.setDisable(false);
            columnButton3.setDisable(false);
            columnButton4.setDisable(false);
            columnButton5.setDisable(false);
            columnButton6.setDisable(false);
        }
        @FXML
        private void initialize() {
            // Desactiva todos los botones de columna al inicio
            columnButton0.setDisable(true);
            columnButton1.setDisable(true);
            columnButton2.setDisable(true);
            columnButton3.setDisable(true);
            columnButton4.setDisable(true);
            columnButton5.setDisable(true);
            columnButton6.setDisable(true);
            currentPlayer=1;

        }
        @FXML
        private void handleColumnButtonClick(ActionEvent event) {
            if (event.getSource() instanceof Button) {
                Button clickedButton = (Button) event.getSource();
                int columnIndex = GridPane.getColumnIndex(clickedButton);


                // Lógica para agregar una ficha en la columna correspondiente
                meterFicha(columnIndex);

                // Si quieres acceder a los nombres de los jugadores, puedes hacerlo así:
                String nombreJugadorUno = nombreJugador1.getText();
                String nombreJugadorDos = nombreJugador2.getText();

            }
        }
        private void meterFicha(int columnIndex) {
            for (int row = 5; row >= 0; row--) {
                if (board[row][columnIndex] == 0) {
                    // Agrega una ficha en la posición válida
                    board[row][columnIndex] = currentPlayer;

                    // Actualiza la GUI
                    Circle circle = new Circle(50.0);
                    circle.setStroke(Color.BLACK);
                    circle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
                    if (currentPlayer == 1) {
                        circle.setFill(Color.RED);
                    } else {
                        circle.setFill(Color.YELLOW);
                    }

                    gridPane.add(circle, columnIndex, row);

                    // Verifica si hay un ganador
                    if (Ganador(currentPlayer, row, columnIndex)) {
                        System.out.println("¡Jugador " + currentPlayer + " ha ganado!");
                        // Puedes reiniciar el juego aquí si lo deseas
                    }

                    // Cambia al siguiente jugador
                    currentPlayer = (currentPlayer == 1) ? 2 : 1;
                    // Actualiza el color del círculo en la columna 7
                    turnoActual();

                    if ("JugadorVsIA".equals(modoJuego.getSelectedToggle().getUserData()) && currentPlayer == 2) {
                        realizarMovimientoIA();
                    }

                    break;
                }
            }
        }
        private void turnoActual() {
            // Obtén el color del jugador actual
            String color;
            if (currentPlayer == 1) {
                color = "RED";
            } else if (currentPlayer == 2) {
                color = "YELLOW";
            }else{
                color = "WHITE";
            }

            // Actualiza el color del círculo en la columna 7
            dropCircle.setFill(Color.valueOf(color));


        }
        private boolean Ganador(int player, int row, int col) {
            // Verifica si hay un ganador después de cada movimiento
            if (columna(player, col) || fila(player, row) || diagonales(player, row, col)) {
                // Muestra una ventana emergente con el mensaje del jugador ganador
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("¡Fin del juego!");
                alert.setHeaderText(null);
                alert.setContentText("¡Jugador " + player + " ha ganado!");

                Toggle selectedToggle = modoJuego.getSelectedToggle();

                if (selectedToggle instanceof RadioButton) {
                    RadioButton selectedRadioButton = (RadioButton) selectedToggle;
                    String mode = selectedRadioButton.getText();
                    String mensaje;
                    if (mode.equals("Jugador Vs Jugador")) {
                        if (player==1){
                            mensaje= "¡Jugador " + nombreJugador1.getText() + " ha ganado!";
                            alert.setContentText(mensaje);
                        }else{
                            mensaje= "¡Jugador " + nombreJugador2.getText() + " ha ganado!";
                            alert.setContentText(mensaje);
                        }

                    } else if (mode.equals("Jugador Vs IA")) {
                        if (player==1){
                        alert.setContentText("¡Jugador " + nombreJugador1.getText() + " ha ganado!");
                    }else {
                            alert.setContentText("¡La IA ha ganado!");
                        }
                    }
                }

                // Configura los botones de la alerta
                ButtonType salirButton = new ButtonType("Salir");
                ButtonType reiniciarButton = new ButtonType("Reiniciar");

                alert.getButtonTypes().setAll(salirButton, reiniciarButton);

                // Muestra la alerta y espera a que se seleccione un botón
                alert.showAndWait().ifPresent(response -> {
                    if (response == salirButton) {

                        System.exit(0);
                    } else if (response == reiniciarButton) {
                        reiniciarJuego();
                    }
                });

                return true;
            }

            return false;
        }
        private void reiniciarJuego() {
            // Implementa la lógica para reiniciar el juego según tus necesidades
            // Puede incluir la limpieza del tablero, restablecer variables, etc.
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    board[i][j] = 0;
                        // Actualiza la GUI
                        Circle circle = new Circle(50.0);
                        circle.setStroke(Color.BLACK);
                        circle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
                        circle.setFill(Color.WHITE);

                        gridPane.add(circle, j, i);
                        turnoActual();
                }
            }
            currentPlayer=1;
            turnoActual();

        }
        private boolean columna(int player, int col) {
            int count = 0;
            for (int i = 0; i < ROWS; i++) {
                if (board[i][col] == player) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
            return false;
        }
        private boolean fila(int player, int row) {
            int count = 0;
            for (int j = 0; j < COLUMNS; j++) {
                if (board[row][j] == player) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
            return false;
        }
        private boolean diagonales(int player, int row, int col) {
            // Verificar la diagonal (izquierda arriba a derecha abajo)
            int count = 0;
            for (int i = -3; i <= 3; i++) {
                int currentRow = row + i;
                int currentCol = col + i;

                if (posicionValida(currentRow, currentCol) && board[currentRow][currentCol] == player) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }

            // Verificar la diagonal (izquierda abajo a derecha arriba)
            count = 0;
            for (int i = -3; i <= 3; i++) {
                int currentRow = row - i;
                int currentCol = col + i;

                if (posicionValida(currentRow, currentCol) && board[currentRow][currentCol] == player) {
                    count++;
                    if (count == 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }

            return false;
        }
        private boolean posicionValida(int row, int col) {
            return row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
        }
        @FXML
        private void modoDeJuego(ActionEvent event) {
            Toggle selectedToggle = modoJuego.getSelectedToggle();

            if (selectedToggle instanceof RadioButton) {
                RadioButton botonSelecionado = (RadioButton) selectedToggle;
                String mode = botonSelecionado.getText();
                // Oculta ambos textfields por defecto
                nombreJugador1.setVisible(false);
                nombreJugador2.setVisible(false);

                if (mode.equals("Jugador Vs Jugador")) {
                    // Habilita la interacción del jugador
                    currentPlayer = 1;
                    reiniciarJuego(); // Reinicia si cambia de modo de juego

                    // Muestra los textfields solo en el modo Jugador Vs Jugador
                    nombreJugador1.setVisible(true);
                    nombreJugador2.setVisible(true);
                } else if (mode.equals("Jugador Vs IA")) {
                    // Deshabilita la interacción del jugador dos (IA)
                    nombreJugador1.setVisible(true);
                    currentPlayer = 1;
                    reiniciarJuego();
                    realizarMovimientoIA();
                }
            }
        }
        private void realizarMovimientoIA() {
            int columnaAleatoria = (int) (Math.random() * COLUMNS);

            // Busca la fila disponible en la columna aleatoria
            for (int row = 5; row >= 0; row--) {
                if (board[row][columnaAleatoria] == 0) {
                    // Agrega una ficha en la posición válida
                    board[row][columnaAleatoria] = 2;
                    // Actualiza la GUI
                    Circle circle = new Circle(50.0);
                    circle.setStroke(Color.BLACK);
                    circle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
                    circle.setFill(Color.YELLOW);

                    gridPane.add(circle, columnaAleatoria, row);
                    // Verifica si hay un ganador después del movimiento de la IA
                    if (Ganador(2, row, columnaAleatoria)) {
                        System.out.println("¡La IA ha ganado!");
                    }
                    // Cambia al siguiente jugador
                    currentPlayer = 1;
                    // Actualiza el color del círculo en la columna 7
                    turnoActual();
                    break;
                }
            }

        }
        @FXML
        private void handleSalir(ActionEvent event) {
            System.exit(0);
        }

        @FXML
        private void handleReiniciar(ActionEvent event) {
            reiniciarJuego();
        }


    }
