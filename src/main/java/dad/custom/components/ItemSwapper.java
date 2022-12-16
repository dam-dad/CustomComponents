package dad.custom.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class ItemSwapper<T> extends GridPane implements Initializable {

	// model

	private ListProperty<T> leftItems = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<T> rightItems = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<T> leftSelected = new SimpleObjectProperty<>();
	private ObjectProperty<T> rightSelected = new SimpleObjectProperty<>();
	private StringProperty leftTitle = new SimpleStringProperty();
	private StringProperty rightTitle = new SimpleStringProperty();

	// view

	@FXML
	private Label leftLabel;

	@FXML
	private ListView<T> leftList;

	@FXML
	private Button moveAllToLeftButton;

	@FXML
	private Button moveAllToRightButton;

	@FXML
	private Button moveToLeftButton;

	@FXML
	private Button moveToRightButton;

	@FXML
	private Label rightLabel;

	@FXML
	private ListView<T> rightList;

	@FXML
	private GridPane view;

	public ItemSwapper() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ItemSwapper.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		leftList.itemsProperty().bind(leftItems);
		rightList.itemsProperty().bind(rightItems);
		leftSelected.bind(leftList.getSelectionModel().selectedItemProperty());
		rightSelected.bind(rightList.getSelectionModel().selectedItemProperty());
		leftLabel.textProperty().bind(leftTitle);
		rightLabel.textProperty().bind(rightTitle);

		// enable/disable buttons

		moveToLeftButton.disableProperty().bind(rightSelected.isNull());
		moveToRightButton.disableProperty().bind(leftSelected.isNull());
		moveAllToLeftButton.disableProperty().bind(rightItems.emptyProperty());
		moveAllToRightButton.disableProperty().bind(leftItems.emptyProperty());

	}

	@FXML
	void onMoveAllToLeftAction(ActionEvent event) {
		leftItems.addAll(rightItems);
		rightItems.clear();
	}

	@FXML
	void onMoveAllToRightAction(ActionEvent event) {
		rightItems.addAll(leftItems);
		leftItems.clear();
	}

	@FXML
	void onMoveToLeftAction(ActionEvent event) {
		leftItems.add(rightSelected.get());
		rightItems.remove(rightSelected.get());
	}

	@FXML
	void onMoveToRightAction(ActionEvent event) {
		rightItems.add(leftSelected.get());
		leftItems.remove(leftSelected.get());
	}

	public final ListProperty<T> leftItemsProperty() {
		return this.leftItems;
	}

	public final ObservableList<T> getLeftItems() {
		return this.leftItemsProperty().get();
	}

	public final void setLeftItems(final ObservableList<T> leftItems) {
		this.leftItemsProperty().set(leftItems);
	}

	public final ListProperty<T> rightItemsProperty() {
		return this.rightItems;
	}

	public final ObservableList<T> getRightItems() {
		return this.rightItemsProperty().get();
	}

	public final void setRightItems(final ObservableList<T> rightItems) {
		this.rightItemsProperty().set(rightItems);
	}

	public final StringProperty leftTitleProperty() {
		return this.leftTitle;
	}

	public final String getLeftTitle() {
		return this.leftTitleProperty().get();
	}

	public final void setLeftTitle(final String leftTitle) {
		this.leftTitleProperty().set(leftTitle);
	}

	public final StringProperty rightTitleProperty() {
		return this.rightTitle;
	}

	public final String getRightTitle() {
		return this.rightTitleProperty().get();
	}

	public final void setRightTitle(final String rightTitle) {
		this.rightTitleProperty().set(rightTitle);
	}

}
