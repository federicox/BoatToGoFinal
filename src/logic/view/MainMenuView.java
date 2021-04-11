package logic.view;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import logic.mydatecell.MyCallback;

public class MainMenuView extends MainView {
	private static final String ERR_MESSAGE = "You have to fill this field!";
	private Text txtErrCity = new Text(ERR_MESSAGE);
	private Text txtErrCheckIn = new Text(ERR_MESSAGE);
	private Text txtErrCheckOut = new Text(ERR_MESSAGE);
	private Text txtErrPersonCount = new Text("You have to select the number of people!");

	private TextField txtFieldCity = new TextField();

	private DatePicker dPickerCheckIn = new DatePicker();
	private DatePicker dPickerCheckOut = new DatePicker();

	private Label lblPersonCount = new Label("0");

	private Button btnSearch = new Button("Search");
	private Button btnPlus = new Button("+");
	private Button btnMinus = new Button("-");

	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) throws Exception {

		super.start(primaryStage);
		
		HBox hBoxTitle = new HBox();
		hBoxTitle.setAlignment(Pos.CENTER);
		hBoxTitle.setPadding(new Insets(20, 0, 20, 0));
		hBoxTitle.setBackground(new Background(new BackgroundFill(Color.LIGHTSEAGREEN, CornerRadii.EMPTY, null)));
		
		Text title = new Text("Welcome to Boat To Go!");
		title.setFont(Font.font("System", FontWeight.NORMAL, 48));
		hBoxTitle.getChildren().add(title);

		HBox hBoxSearch = new HBox(20);
		hBoxSearch.setPadding(new Insets(20, 20, 20, 20));
		hBoxSearch.setPrefWidth(Double.MAX_VALUE);

		Label lblcity = new Label("Where will you rent your boat?");
		Label lblCheckIn = new Label("Enter Check-In");
		Label lblCheckOut = new Label("Enter Check-Out");
		Label lblPerson = new Label("Select the number of people");

		txtFieldCity.setPromptText("e.g. Rome");
		dPickerCheckIn.setPromptText("Pick a date");
		dPickerCheckIn.setDayCellFactory(MyCallback.getDayCellFactory());
		dPickerCheckIn.setEditable(false);

		dPickerCheckOut.setPromptText("Pick a date");
		dPickerCheckOut.setDayCellFactory(MyCallback.getDayCellFactory());
		dPickerCheckOut.setEditable(false);

		this.txtErrCity.setFill(Color.RED);
		this.txtErrCity.setVisible(false);
		this.txtErrCheckIn.setFill(Color.RED);
		this.txtErrCheckIn.setVisible(false);
		this.txtErrCheckOut.setFill(Color.RED);
		this.txtErrCheckOut.setVisible(false);
		this.txtErrPersonCount.setFill(Color.RED);
		this.txtErrPersonCount.setVisible(false);

		GridPane gridPane = new GridPane();
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.add(lblcity, 0, 0);
		gridPane.add(txtFieldCity, 0, 1);
		gridPane.add(lblCheckIn, 1, 0);
		gridPane.add(dPickerCheckIn, 1, 1);
		gridPane.add(lblCheckOut, 2, 0);
		gridPane.add(dPickerCheckOut, 2, 1);
		gridPane.add(lblPerson, 3, 0);
		gridPane.add(this.txtErrCity, 0, 2);
		gridPane.add(this.txtErrCheckIn, 1, 2);
		gridPane.add(this.txtErrCheckOut, 2, 2);
		gridPane.add(this.txtErrPersonCount, 3, 2);

		HBox personCountHBox = new HBox(10);
		personCountHBox.setAlignment(Pos.CENTER);
		personCountHBox.getChildren().addAll(btnPlus, lblPersonCount, btnMinus);

		gridPane.add(personCountHBox, 3, 1);

		this.disableMinusButton();
		this.btnSearch.setFont(Font.font(18));
		hBoxSearch.setAlignment(Pos.CENTER);
		btnSearch.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		hBoxSearch.getChildren().addAll(gridPane, btnSearch);

		VBox vBoxSlogan = new VBox(10);
		vBoxSlogan.setPadding(new Insets(20, 20, 20, 20));
		vBoxSlogan.setAlignment(Pos.CENTER);

		Text txtSlogan = new Text(
				"Looking for a relaxing day rocked by the waves of the Italian seas?" + "\nLook no further!" + "\nFind your favourite boat and set sail!");
		txtSlogan.setFont(Font.font("System", FontWeight.NORMAL, 24));
		txtSlogan.setTextAlignment(TextAlignment.CENTER);

		vBoxSlogan.getChildren().addAll(txtSlogan);

		VBox vBoxMain = new VBox(50);
		vBoxMain.setPadding(new Insets(20, 20, 20, 20));
		vBoxMain.getChildren().addAll(hBoxTitle, vBoxSlogan, hBoxSearch);
		vBoxMain.setMargin(hBoxTitle, new Insets(0, -40, 0, -40));

		super.borderPane.setCenter(vBoxMain);

	}

	public void setVisibleErrCityField(boolean value) {
		this.txtErrCity.setVisible(value);
	}

	public void setVisibleErrCheckInField(boolean value) {
		this.txtErrCheckIn.setVisible(value);
	}

	public void setVisibleErrCheckOutField(boolean value) {
		this.txtErrCheckOut.setVisible(value);
	}

	public void setVisibleErrPersonCount(boolean value) {
		this.txtErrPersonCount.setVisible(value);
	}

	public void addSearchListener(EventHandler<ActionEvent> searchHandler) {
		this.btnSearch.setOnAction(searchHandler);
	}

	public void enableMinusButton() {
		this.btnMinus.setDisable(false);
	}

	public void disableMinusButton() {
		this.btnMinus.setDisable(true);
	}

	public void addMinusHanlder(EventHandler<ActionEvent> minusHandler) {
		this.btnMinus.setOnAction(minusHandler);
	}

	public void addPlusHanlder(EventHandler<ActionEvent> addHandler) {
		this.btnPlus.setOnAction(addHandler);
	}

	public String getPersonCount() {
		return this.lblPersonCount.getText();
	}

	public void setPersonCountText(String value) {
		this.lblPersonCount.setText(value);
	}

	public String getCityField() {
		return this.txtFieldCity.getText();
	}

	public void resetPersonCount() {
		this.lblPersonCount.setText("0");
	}

	public LocalDate getCheckOutDate() {
		return this.dPickerCheckOut.getValue();
	}

	public LocalDate getCheckInDate() {
		return this.dPickerCheckIn.getValue();
	}

}
