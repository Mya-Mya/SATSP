package view;

public interface SceneChanger {
    enum Scene{
        Tutorial,
        Playing
    }
    void changeScene(Scene newScene);
}
