package edu.neu.csye7374.pattern.command;

import edu.neu.csye7374.pattern.command.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {


    private List<Command> commandQueue = new ArrayList<Command>();

    public void takeOperation(Command command) {
        commandQueue.add(command);
    }

    public void executeOperation() {
        for (Command command : commandQueue) {
            command.execute();
        }

        commandQueue.clear();
    }

    public void triggerServerClient(String message) {
        ResponseServer server = new ResponseServer();
        RequestClient client = new RequestClient(message);

        CommandInvoker invoker = new CommandInvoker();
        invoker.takeOperation(server);
        invoker.takeOperation(client);

        invoker.executeOperation();


    }
} 