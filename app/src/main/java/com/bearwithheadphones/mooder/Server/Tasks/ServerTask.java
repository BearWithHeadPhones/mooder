package com.bearwithheadphones.mooder.Server.Tasks;

/**
 * Created by bartoszcwynar on 10.05.2016.
 */
public interface ServerTask {

    public String execute();

    public void postExecute(String result);
}
