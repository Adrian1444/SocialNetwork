package retea.reteadesocializare;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import retea.reteadesocializare.domain.Friendship;
import retea.reteadesocializare.domain.Tuple;
import retea.reteadesocializare.domain.User;
import retea.reteadesocializare.domain.validators.FriendshipValidator;
import retea.reteadesocializare.domain.validators.MessageValidator;
import retea.reteadesocializare.domain.validators.UserValidator;
import retea.reteadesocializare.repository.Repository;
import retea.reteadesocializare.repository.db.FriendshipDbRepository;
import retea.reteadesocializare.repository.db.MessageDbRepository;
import retea.reteadesocializare.repository.db.UserDbRepository;
import retea.reteadesocializare.service.Service;
import retea.reteadesocializare.service.ServiceException;

import java.io.IOException;

public class HelloController {
    Service service;

    public HelloController() {
        Repository<Long, User> userDbRepository = new UserDbRepository("jdbc:postgresql://localhost:5432/ReteaDeSocializare", "postgres", "142001", new UserValidator());
        Repository<Tuple<Long, Long>, Friendship> friendshipDbRepository = new FriendshipDbRepository("jdbc:postgresql://localhost:5432/ReteaDeSocializare", "postgres", "142001", new FriendshipValidator());
        MessageDbRepository messageDbRepository=new MessageDbRepository("jdbc:postgresql://localhost:5432/ReteaDeSocializare", "postgres", "142001", new MessageValidator(),userDbRepository);
        Service service1 = new Service(userDbRepository, friendshipDbRepository,messageDbRepository);
        this.service = service1;
        //initFriendsList();
    }

    /*
    public void initFriendsList(){
        FriendsList=new ListView<String>();
        items = FXCollections.observableArrayList (
                "RUBY", "APPLE", "VISTA", "TWITTER");
        FriendsList.setItems(items);
    }
    */
    //ObservableList<String> items;

    @FXML
    private Label ErrorMessageLoginIn;

    @FXML
    private Label welcomeText;

    @FXML
    private Button LogInButton;

    @FXML
    private TextField LoginTextField;

    @FXML
    private TextField EditProfileTextField;

    @FXML
    private Button FriendRequestsButton;

    @FXML
    private Button FriendsListButton;

    @FXML
    private Button LogOutButton;

    @FXML
    private TextField SearchBar;

    @FXML
    private GridPane GridPaneListFriends;

    @FXML
    private ListView<String> FriendsList;

    @FXML
    void LogInButtonClicked(MouseEvent event) throws IOException {
        /*
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 200, 200);
        stage.setScene(scene);
        ((Node)(event.getSource())).getScene().getWindow().hide();
        stage.show();
        */
        String id = null;
        id= LoginTextField.getText();
        try {
            Long ID = Long.parseLong(id);
            Iterable<User> l= service.getAllUsers();
            service.checkUserExistence(ID);

            Stage stage = new Stage();
            stage.setTitle("CyberBear");
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenu-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setScene(scene);
            ((Node)(event.getSource())).getScene().getWindow().hide();
            stage.show();

        } catch (NumberFormatException ex) {
            ErrorMessageLoginIn.setText("ID should be a number");
        }catch(ServiceException ex){
            ErrorMessageLoginIn.setText(ex.getMessage());
        }

    }

    @FXML
    void FriendRequestsButtonClicked(MouseEvent event) throws IOException {

    }

    @FXML
    void FriendsListButtonClicked(MouseEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("friendsList-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("CyberBear");
        stage.setScene(scene);

        stage.show();

    }

    @FXML
    void LogOutButtonClicked(MouseEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

    public void setService(Service service) {
        this.service = service;
    }
}