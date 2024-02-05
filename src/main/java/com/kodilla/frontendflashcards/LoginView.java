package com.kodilla.frontendflashcards;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Route("login")
@Component
public class LoginView extends VerticalLayout {

    public LoginView() {
        Div flashcardTitle = new Div();
        flashcardTitle.setText("FLASHCARDS");
        flashcardTitle.getStyle()
                .set("font-size", "43px")
                .set("text-align", "center")
                .set("margin-left", "auto")
                .set("margin-right", "auto")
                .set("margin-top", "150px");

        Div formContainer = new Div();
        formContainer.getStyle()
                .set("border", "1px solid #ccc")
                .set("padding", "20px")
                .set("border-radius", "5px")
                .set("width", "15%")
                .set("height", "30%")
                .set("position", "absolute")
                .set("top", "50%")
                .set("left", "50%")
                .set("transform", "translate(-50%, -50%)")
                .set("box-shadow", "5px 5px 10px 0px rgba(0,0,0,0.75)")
                .set("background-color", "#E2DDCA");

        TextField usernameField = new TextField("username");
        usernameField.setWidthFull();

        PasswordField passwordField = new PasswordField("password");
        passwordField.setWidthFull();

        Button loginButton = new Button("Log in");
        loginButton.addClassName("primary");

        Span youDontHaveAccountText = new Span("You don't have an account yet?");
        youDontHaveAccountText.getElement().getStyle()
                .set("font-size", "12px")
                .set("margin-left", "auto")
                .set("margin-right", "auto")
                .set("margin-top", "10px")
                .set("margin-bottom", "-10px");

        Anchor registerLink = new Anchor("/register", "Register");
        registerLink.getElement().getStyle()
                .set("font-size", "12px")
                .set("margin-left", "auto")
                .set("margin-right", "auto")
                .set("margin-top", "-10px");

        VerticalLayout buttonLayout = new VerticalLayout(loginButton, youDontHaveAccountText, registerLink);
        buttonLayout.getStyle().set("align-items", "center");

        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        formContainer.add(usernameField, passwordField, buttonLayout);

        loginButton.addClickListener(event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();

            try {
                if (isValidUser(username, password)) {
                    loginUser(username);
                    saveUserData(username, password);
                    Notification.show("Log in successfully", 3000, Notification.Position.MIDDLE);
                } else {
                    Notification.show("Invalid credentials", 3000, Notification.Position.MIDDLE);
                }
            } catch (Exception e) {
                Notification.show("An error occurred", 3000, Notification.Position.MIDDLE);
            }
        });


        add(flashcardTitle, formContainer);
    }


    /*private boolean isValidUser(String username, String password) {
        JSONObject userData = new JSONObject();
        userData.put("username", username);
        userData.put("password", password);

        try {
            HttpResponse<String> response = Unirest.post("http://localhost:8080/auth/login")
                    .header("Content-Type", "application/json")
                    .body(userData.toString())
                    .asString();

            return response.getStatus() == 200;
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
    }
*/
    private boolean isValidUser(String username, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(getUserJson(username, password)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String token = response.body();
                addCookie("JSESSIONID", token);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addCookie(String name, String value) {
        String expires = "expires=" + LocalDateTime.now().plusDays(30);
        String cookieValue = name + "=" + value + ";" + expires + ";path=/";
        UI.getCurrent().getPage().executeJs("document.cookie='" + cookieValue + "'");
    }

    private void loginUser(String username) {
        VaadinSession.getCurrent().setAttribute("user", username);
        Notification.show("Logged in as: " + username, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate(MainMenuView.class));
    }

    private void saveUserData(String username, String password) {
        String userKey = "user_" + username;
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        vaadinSession.setAttribute(userKey, username);
        Notification.show("Data saved in session for user: " + username);
    }

    private String getUserJson(String username, String password) {
        return "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
    }
}
