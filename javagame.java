import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Question class to store question, options, and correct answer
class Question {
    private String questionText;
    private String[] options;
    private int correctAnswerIndex;

    public Question(String questionText, String[] options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

public class QuizGame extends Application {
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private Label questionLabel;
    private Button[] optionButtons;
    private Label feedbackLabel;

    @Override
    public void start(Stage primaryStage) {
        // Initialize questions
        initializeQuestions();

        // Set up the main layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // GUI components
        questionLabel = new Label();
        optionButtons = new Button[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new Button();
            int finalI = i;
            optionButtons[i].setOnAction(e -> checkAnswer(finalI));
        }
        feedbackLabel = new Label();
        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> showNextQuestion());

        // Add components to layout
        layout.getChildren().addAll(questionLabel, optionButtons[0], optionButtons[1],
                optionButtons[2], optionButtons[3], feedbackLabel, nextButton);

        // Show the first question
        showQuestion();

        // Set up the scene and stage
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setTitle("Quiz Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Initialize hardcoded questions (can be modified to read from a file)
    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?",
                new String[]{"Paris", "London", "Berlin", "Madrid"}, 0));
        questions.add(new Question("Which planet is known as the Red Planet?",
                new String[]{"Jupiter", "Mars", "Venus", "Mercury"}, 1));
        questions.add(new Question("What is 2 + 2?",
                new String[]{"3", "4", "5", "6"}, 1));
        questions.add(new Question("Which language is used for Android apps?",
                new String[]{"Python", "Java", "C++", "Ruby"}, 1));
        questions.add(new Question("What is the largest ocean?",
                new String[]{"Atlantic", "Indian", "Arctic", "Pacific"}, 3));

       
    }

    // Display the current question and options
    private void showQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.getQuestionText());
            String[] options = currentQuestion.getOptions();
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(options[i]);
                optionButtons[i].setDisable(false);
            }
            feedbackLabel.setText("");
        } else {
            // Show final score
            questionLabel.setText("Quiz Completed!");
            for (Button button : optionButtons) {
                button.setText("");
                button.setDisable(true);
            }
            feedbackLabel.setText("Your final score: " + score + "/" + questions.size());
        }
    }

    // Check the selected answer and update score
    private void checkAnswer(int selectedIndex) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (selectedIndex == currentQuestion.getCorrectAnswerIndex()) {
            score++;
            feedbackLabel.setText("Correct!");
        } else {
            feedbackLabel.setText("Incorrect. Correct answer: " +
                    currentQuestion.getOptions()[currentQuestion.getCorrectAnswerIndex()]);
        }
        // Disable buttons after selection
        for (Button button : optionButtons) {
            button.setDisable(true);
        }
    }

    // Move to the next question
    private void showNextQuestion() {
        currentQuestionIndex++;
        showQuestion();
    }

    public static void main(String[] args) {
        launch(args);
    }
}