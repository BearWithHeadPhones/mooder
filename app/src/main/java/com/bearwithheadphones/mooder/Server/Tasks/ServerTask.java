package com.bearwithheadphones.mooder.Server.Tasks;

/**
 * Created by bartoszcwynar on 10.05.2016.
 */
public abstract class ServerTask {

    public enum Result{
        NoInternetConnection, Success, Failure
    }

    public abstract String execute();

    public abstract void postExecute(String result);

    public void handleResult(Result result){};
}
