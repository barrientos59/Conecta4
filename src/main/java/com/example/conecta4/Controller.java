    package com.example.conecta4;

    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.scene.Node;
    import javafx.scene.control.Alert;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.layout.GridPane;
    import javafx.scene.paint.Color;
    import javafx.scene.shape.Circle;

    public class Controller {

        @FXML
        private GridPane gridPane;

        private int currentPlayer = 1; // 1 para jugador 1, 2 para jugador 2

        private final int ROWS = 6;
        private final int COLUMNS = 7;
        private int[][] board = new int[ROWS][COLUMNS]; // 0 representa espacio vacío, 1 representa jugador 1, 2 representa jugador 2
        @FXML
        private Circle dropCircle;

        @FXML
        private Label turnoLabel;
        @FXML
        private void handleColumnButtonClick(ActionEvent event) {
            if (event.getSource() instanceof Button) {
                Button clickedButton = (Button) event.getSource();
                int columnIndex = GridPane.getColumnIndex(clickedButton);

                // Lógica para agregar una ficha en la columna correspondiente
                addFicha(columnIndex);
            }
        }

        private void addFicha(int columnIndex) {
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
                    if (isWinner(currentPlayer, row, columnIndex)) {
                        System.out.println("¡Jugador " + currentPlayer + " ha ganado!");
                        // Puedes reiniciar el juego aquí si lo deseas
                    }

                    // Cambia al siguiente jugador
                    currentPlayer = (currentPlayer == 1) ? 2 : 1;
                    // Actualiza el color del círculo en la columna 7
                    updateDropCircle();
                    break;
                }
            }
        }
        private void updateDropCircle() {
            // Obtén el color del jugador actual
            String color;
            if (currentPlayer == 1) {
                color = "RED";
            } else {
                color = "YELLOW";
            }

            // Actualiza el color del círculo en la columna 7
            dropCircle.setFill(javafx.scene.paint.Color.valueOf(color));
        }

        private boolean isWinner(int player, int row, int col) {
            // Verifica si hay un ganador después de cada movimiento
            if (checkColumn(player, col) || checkRow(player, row) || checkDiagonal(player, row, col)) {
                // Muestra una ventana emergente con el mensaje del jugador ganador
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("¡Fin del juego!");
                alert.setHeaderText(null);
                alert.setContentText("¡Jugador " + player + " ha ganado!");

                alert.showAndWait();

                // Puedes reiniciar el juego aquí si lo deseas

                return true;
            }

            return false;
        }

        private boolean checkColumn(int player, int col) {
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

        private boolean checkRow(int player, int row) {
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

        private boolean checkDiagonal(int player, int row, int col) {
            // Implementa la lógica para verificar la diagonal (izquierda arriba a derecha abajo)
            // y la diagonal (izquierda abajo a derecha arriba)
            // Adaptada según tus necesidades específicas
            return false;
        }


    }
