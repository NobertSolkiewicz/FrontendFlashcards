package com.kodilla.frontendflashcards;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route("createFlashcard")
@Component
public class CreateFlashcards extends VerticalLayout {
    private Div sideMenu;
    private Div mainLayout;
    private Button createButton;
    private Button myDecksButton;
    private Button myAccountButton;
    private Button logoutButton;
    private List<Div> squareContainers = new ArrayList<>();
    private boolean isMenuOpen;

    public CreateFlashcards() {

        H1 pageTitle = new H1("Flashcard");
        pageTitle.getStyle()
                .set("background-color", "#E2DDCA")
                .set("height", "100px")
                .set("width", "100%")
                .set("margin-top", "-16px")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center");

        Button menuButton = new Button("Menu");
        menuButton.addClickListener(event -> toggleMenu());
        menuButton.getStyle().set("color", "black");

        sideMenu = new Div();
        sideMenu.addClassName("side-menu");
        sideMenu.getStyle()
                .set("width", "250px")
                .set("height", "100vh")
                .set("background-color", "#E2DDCA")
                .set("border", "1px solid #000");

        createButton = new Button("Create Deck");
        myDecksButton = new Button("My Decks");
        myAccountButton = new Button("My Account");
        logoutButton = new Button("Log Out");
        logoutButton.getStyle().set("margin-top", "700px");
        createButton.getStyle().set("color", "black");
        myDecksButton.getStyle().set("color", "black");
        myAccountButton.getStyle().set("color", "black");
        logoutButton.getStyle().set("color", "black");

        createButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(CreateDecks.class));
        });

        myDecksButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(CreateDecks.class));
        });

        myAccountButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(MainMenuView.class));
        });

        hideButtons();

        VerticalLayout buttonsLayout = new VerticalLayout(createButton, myDecksButton, myAccountButton, logoutButton);
        buttonsLayout.getStyle().set("padding", "16px");

        sideMenu.add(buttonsLayout);

        Button addSquareButton = createAddButton();
        TextField frontNameTextField = new TextField();
        TextField backNameTextField = new TextField();
        frontNameTextField.setVisible(false);
        frontNameTextField.setPlaceholder("Front name");
        backNameTextField.setVisible(false);
        backNameTextField.setPlaceholder("Back name");

        Div createSquareContainer = createSquareContainer("Create Flashcard", "Add");
        createSquareContainer.getStyle().set("margin-left", "50px");
        Div addSquareButtonContainer = new Div();
        addSquareButtonContainer.add(addSquareButton);

        mainLayout = new Div(sideMenu, createSquareContainer, addSquareButtonContainer);
        mainLayout.getStyle()
                .set("display", "flex")
                .set("flex-wrap", "wrap");
        mainLayout.setSizeFull();

        add(pageTitle, menuButton, mainLayout);
    }

    private void toggleMenu() {
        isMenuOpen = !isMenuOpen;

        if (isMenuOpen) {
            sideMenu.getStyle().set("width", "250px");
            showButton();

        } else {
            sideMenu.getStyle().set("width", "0");
            hideButtons();
        }
    }

    private void showButton() {
        sideMenu.setVisible(true);
        createButton.setVisible(true);
        myDecksButton.setVisible(true);
        myAccountButton.setVisible(true);
        logoutButton.setVisible(true);
    }

    private void hideButtons() {
        sideMenu.setVisible(false);
        createButton.setVisible(false);
        myDecksButton.setVisible(false);
        myAccountButton.setVisible(false);
        logoutButton.setVisible(false);
    }

    private Div createSquareContainer(String title, String buttonText) {
        Div squareContainer = new Div();
        squareContainer.getStyle()
                .set("width", "300px")
                .set("height", "260px")
                .set("margin-right", "50px")
                .set("margin-bottom", "50px")
                .set("border", "1px solid black")
                .set("background-color", "#E2DDCA");

        Div nameSquare = new Div();
        nameSquare.getStyle()
                .set("width", "300px")
                .set("height", "80px")
                .set("background-color", "#E2DDCA")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center");

        nameSquare.add(title);

        Div startButtonSquare = new Div();
        startButtonSquare.getStyle()
                .set("width", "300px")
                .set("height", "160px")
                .set("background-color", "#E2DDCA")
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center")
                .set("justify-content", "center");

        Button addButton = new Button(buttonText);
        TextField frontNameTextField = new TextField();
        TextField backNameTextField = new TextField();

        addButton.addClickListener(event -> {
            String deckName = frontNameTextField.getValue();
            String deckTag = backNameTextField.getValue();

            if (!deckName.isEmpty() && ! deckTag.isEmpty()) {
                createNewDeck(deckName, deckTag);
                frontNameTextField.setValue("");
                backNameTextField.setValue("");
            } else {
                Notification.show("Please enter front name and back name");
            }
        });

        frontNameTextField.setVisible(false);
        frontNameTextField.setPlaceholder("Front name");
        backNameTextField.setVisible(false);
        backNameTextField.setPlaceholder("Back name");

        addButton.addClickListener(event -> {
            frontNameTextField.setVisible(true);
            backNameTextField.setVisible(true);
        });

        startButtonSquare.add(addButton, frontNameTextField, backNameTextField);

        squareContainer.add(nameSquare, startButtonSquare);

        return squareContainer;
    }

    private Button createAddButton() {
        Button addSquareButton = new Button(new Icon(VaadinIcon.PLUS));
        addSquareButton.getStyle()
                .set("width", "40px")
                .set("height", "40px")
                .set("margin-top", "115px")
                .set("background-color", "#E2DDCA")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("cursor", "pointer");

        addSquareButton.addClickListener(event -> {
            Div newSquareContainer = createSquareContainer("Create Flashcard", "Add");
            /*newSquareContainer.getStyle()
                    .set("margin-right", "100px");*/

            mainLayout.getElement().insertChild(mainLayout.getComponentCount() - 1, newSquareContainer.getElement());
            /*mainLayout.add(newSquareContainer);
            mainLayout.add(addSquareButton);*/
        });

        return addSquareButton;
    }

    private void createNewDeck(String frontName, String backName) {
//        Flashcard newFlashcard = new Flashcard(deckName, deckTag);

        Notification.show("Question: " + frontName + "\n Answer: " + backName);
    }

}
