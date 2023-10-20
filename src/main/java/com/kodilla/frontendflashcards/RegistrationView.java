package com.kodilla.frontendflashcards;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Route;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Route("registration")
@Component
public class RegistrationView extends VerticalLayout {

    public RegistrationView() {
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
                .set("height", "40%")
                .set("position", "absolute")
                .set("top", "50%")
                .set("left", "50%")
                .set("transform", "translate(-50%, -50%)")
                .set("box-shadow", "5px 5px 10px 0px rgba(0,0,0,0.75)")
                .set("background-color", "#E2DDCA");

        FormLayout formLayout = new FormLayout();



        TextField usernameField = new TextField("username");
        usernameField.setWidthFull();

        TextField emailField = new TextField("email");
        emailField.setWidthFull();

        PasswordField passwordField = new PasswordField("password");
        passwordField.setWidthFull();

        PasswordField confirmPasswordField = new PasswordField("confirm password");
        confirmPasswordField.setWidthFull();

        Text message = new Text("");


        Button registerButton = new Button("Register", event -> {
            String username = usernameField.getValue();
            String email = emailField.getValue();
            String password = passwordField.getValue();
            String confirmPassword = confirmPasswordField.getValue();

            if (!password.equals(confirmPassword)) {
                Notification.show("Passwords do not match");
                return;
            }

            boolean registrationSuccess = registerUser(username, email, password);

            if (registrationSuccess) {
                Notification.show("Registration successful!");
                getUI().ifPresent(ui -> ui.navigate("Login"));
                message.setText("Registration successful!");
                message.getStyle().set("color", "green");
            } else {
                Notification.show("Registration failed. Please check your inputs.");
                message.setText("Registration failed. Please check your inputs.");
                message.getStyle().set("color", "red");
            }
        });
        registerButton.addClassName("primary");

        VerticalLayout buttonLayout = new VerticalLayout(registerButton);
        buttonLayout.getStyle().set("align-items", "center");

        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        formLayout.add(usernameField, emailField, passwordField, confirmPasswordField , buttonLayout);

        formContainer.add(formLayout);

        add(flashcardTitle, formContainer);
    }

    private boolean registerUser(String username, String email, String password) {

        JSONObject userData = new JSONObject();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("password", password);

        try {
            HttpResponse<String> response = Unirest.post("https://example.com/api/register")
                    .header("Content-Type", "application/json")
                    .body(userData.toString())
                    .asString();

            if (response.getStatus() == 200) {
                getUI().ifPresent(ui -> ui.navigate(LoginView.class));
                return true;
            } else {
                return false;
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return true;
    }

}
