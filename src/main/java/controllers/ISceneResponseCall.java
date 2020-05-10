package controllers;

public interface ISceneResponseCall<T> {
    void onResponseCall(T callResult);
}
