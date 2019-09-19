package ui;

public interface SceneChanger {
    enum Scene{
        Tutorial,
        Playing
    }
    void changeScene(Scene newScene);
}
