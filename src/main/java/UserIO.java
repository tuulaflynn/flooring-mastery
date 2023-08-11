import view.View;

public class UserIO {
    public static void main(String[] args) {
        // Initialising the view object calls the View constructor which intern calls the reading and storing of all data.
        View view = new View();
        view.homeScreen();

    }
}
