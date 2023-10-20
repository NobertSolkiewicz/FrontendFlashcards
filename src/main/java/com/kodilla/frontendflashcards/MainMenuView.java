package com.kodilla.frontendflashcards;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

@Route("main")
@Component
public class MainMenuView extends VerticalLayout {

    private Div sideMenu;
    private boolean isMenuOpen = false;
    private Button createButton;
    private Button myDecksButton;
    private Button myAccountButton;
    private Button logoutButton;

    public MainMenuView() {
        FlexLayout flexLayout = new FlexLayout();
        flexLayout.setSizeFull();
        flexLayout.setAlignItems(Alignment.CENTER);
        flexLayout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);

        H1 pageTitle = new H1("Flashcard");
        pageTitle.getStyle().set("background-color", "#E2DDCA");
        pageTitle.getStyle().set("height", "100px");
        pageTitle.getStyle().set("width", "100%");
        pageTitle.getStyle().set("margin-top", "-16px");
        pageTitle.getStyle().set("display", "flex");
        pageTitle.getStyle().set("align-items", "center");
        pageTitle.getStyle().set("justify-content", "center");

        Button menuButton = new Button("Menu");
        menuButton.addClickListener(event -> toggleMenu());
        menuButton.getStyle().set("color", "black");

        sideMenu = new Div();
        sideMenu.addClassName("side-menu");
        sideMenu.getStyle().set("width", "250px");
        sideMenu.getStyle().set("height", "100vh");

        sideMenu.getStyle().set("background-color", "#E2DDCA");
        sideMenu.getStyle().set("border", "1px solid #000");

        createButton = new Button("Create Flashcard");
        myDecksButton = new Button("My Decks");
        myAccountButton = new Button("My Account");
        logoutButton = new Button("Log Out");
        logoutButton.getStyle().set("margin-top", "700px");

        createButton.getStyle().set("color", "black");
        myDecksButton.getStyle().set("color", "black");
        myAccountButton.getStyle().set("color", "black");
        logoutButton.getStyle().set("color", "black");

        createButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(CreateFlashcards.class));
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

        Div squareContainer = new Div();
        squareContainer.getStyle().set("position", "absolute");
        squareContainer.getStyle().set("top", "178px");
        squareContainer.getStyle().set("left", "350px");
        squareContainer.getStyle().set("width", "300px");
        squareContainer.getStyle().set("height", "160px");
        squareContainer.getStyle().set("margin-top", "15px");

        squareContainer.getStyle().set("border", "1px solid black");
        squareContainer.getStyle().set("background-color", "#E2DDCA");

        Div nameSquare = new Div();
        nameSquare.getStyle().set("background-color", "#E2DDCA");
        nameSquare.getStyle().set("width", "300px");
        nameSquare.getStyle().set("height", "80px");
        nameSquare.getStyle().set("display", "flex");
        nameSquare.getStyle().set("align-items", "center");
        nameSquare.getStyle().set("justify-content", "center");

        nameSquare.add(new H1("Nazwa"));

        Div startButtonSquare = new Div();
        startButtonSquare.getStyle().set("background-color", "#E2DDCA");
        startButtonSquare.getStyle().set("width", "300px");
        startButtonSquare.getStyle().set("height", "80px");
        startButtonSquare.getStyle().set("display", "flex");
        startButtonSquare.getStyle().set("align-items", "center");
        startButtonSquare.getStyle().set("justify-content", "center");
        startButtonSquare.add(new Button("Start"));

        squareContainer.add(nameSquare, startButtonSquare);

        flexLayout = new FlexLayout(sideMenu, squareContainer);
        flexLayout.setSizeFull();
        flexLayout.setAlignItems(Alignment.CENTER);

        add(pageTitle, menuButton, flexLayout);
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
}


