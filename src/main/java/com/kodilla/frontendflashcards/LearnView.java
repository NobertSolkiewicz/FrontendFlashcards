package com.kodilla.frontendflashcards;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Route("learn")
@Component
public class LearnView extends VerticalLayout {

    private Div flashcardContainer;
    private Div flashcardText;
    private Div frontSide;
    private Div backSide;
    private Div continueFrame;
    private Button continueButton;
    private Button correctButton;
    private Button wrongButton;
    private List<String> flashcardContents;
    private int currentFlashcardIndex;
    private boolean isAnswerVisible = false;

    public LearnView() {
        H1 pageTitle = new H1("Flashcard");
        pageTitle.getStyle()
                .set("background-color", "#E2DDCA")
                .set("height", "100px")
                .set("width", "100%")
                .set("margin-top", "-16px")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center");

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        frontSide = createFlashcardSide("What is the capital of France?");
        backSide = createFlashcardSide("The capital of France is Paris.");

        flashcardText = frontSide;
        flashcardContainer = new Div(flashcardText);

        continueFrame = new Div();
        continueFrame.getStyle()
                .set("position", "absolute")
                .set("bottom", "20px")
                .set("display", "flex")
                .set("justify-content", "center")
                .set("width", "100%");


        continueButton = new Button("Continue");
        continueButton.addClickListener(event -> toggleAnswer());
        continueButton.getStyle()
                .set("background-color", "#4CAF50")
                .set("color", "white")
                .set("border", "none")
                .set("padding", "10px 20px")
                .set("text-align", "center")
                .set("text-decoration", "none")
                .set("display", "inline-block")
                .set("font-size", "16px")
                .set("margin", "4px 2px")
                .set("cursor", "pointer")
                .set("border-radius", "8px");

        correctButton = createSquareButton("Good", "green");
        correctButton.setVisible(isAnswerVisible);

        flashcardContents = Arrays.asList(
                "What is the capital of France?",
                "The capital of France is Paris.",
                "Kolena fiszka",
                "klasa flashcard tu musi byc"
        );
        currentFlashcardIndex = 0;

        correctButton.addClickListener(event -> {
            showNextFlashcard();
        });

        wrongButton = createSquareButton("Bad", "red");
        wrongButton.setVisible(isAnswerVisible);

        wrongButton.addClickListener(event -> {
            showNextFlashcard();

        });

        Div buttonsContainer = new Div(correctButton, wrongButton);
        buttonsContainer.getStyle()
                .set("display", "flex")
                .set("justify-content", "center");

        continueFrame.add(continueButton);

        add(pageTitle, flashcardContainer, continueFrame, buttonsContainer);
        setFlexGrow(1, flashcardContainer);
    }

    private void toggleAnswer() {
        isAnswerVisible = !isAnswerVisible;
        flashcardText = isAnswerVisible ? backSide : frontSide;
        flashcardContainer.removeAll();
        flashcardContainer.add(frontSide, backSide);
        continueButton.setVisible(false);
        correctButton.setVisible(true);
        wrongButton.setVisible(true);
    }

    private Button createSquareButton(String text, String color) {
        Button button = new Button(text);
        button.getStyle()
                .set("background-color", color)
                .set("color", "white")
                .set("border", "none")
                .set("padding", "10px 20px")
                .set("text-align", "center")
                .set("text-decoration", "none")
                .set("display", "inline-block")
                .set("font-size", "16px")
                .set("margin", "4px 2px")
                .set("cursor", "pointer")
                .set("border-radius", "8px");
        button.setVisible(false);
        return button;
    }

    private Div createFlashcardSide(String content) {
        Div flashcardSide = new Div();
        flashcardSide.getStyle()
                .set("font-size", "18px")
                .set("padding", "10px");

        Text text = new Text(content);
        flashcardSide.add(text);

        return flashcardSide;
    }

    private void showNextFlashcard() {
        currentFlashcardIndex = (currentFlashcardIndex + 1) % flashcardContents.size();

        String nextFlashcardContent = flashcardContents.get(currentFlashcardIndex);

        flashcardText.setText(nextFlashcardContent);

        correctButton.setVisible(false);
        wrongButton.setVisible(false);
        continueButton.setVisible(true);
    }
}
