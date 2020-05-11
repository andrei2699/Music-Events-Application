package controllers.scenes;

public interface ISceneResponseCall<T> {
    void onResponseCall(T callResult);
}
