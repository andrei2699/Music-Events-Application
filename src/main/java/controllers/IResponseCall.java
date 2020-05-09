package controllers;

public interface IResponseCall<T> {
    void onResponseCall(T callPackage);
}
