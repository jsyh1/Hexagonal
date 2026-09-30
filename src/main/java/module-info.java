module co.edu.poli {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires io.github.cdimascio.dotenv.java;
  
    opens co.edu.poli.vista to javafx.fxml;

	//Nuevas
	opens co.edu.poli.infrastructure.ui to javafx.fxml;
	opens co.edu.poli.aplication.domain.model to javafx.base;
	
    exports co.edu.poli.vista;
}
