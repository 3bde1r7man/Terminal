package com.mycompany.terminal;

public class Parser {
    String commandName;
    String[] args;
    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user
    public boolean parse(String input){
        
        // split the input into commandName and args
        String[] words = input.split("\\s+");
        commandName = words[0].toLowerCase();
        args = new String[words.length - 1];
        for (int i = 1; i < words.length; i++) {
            args[i-1] = words[i];
        }
        return true;
    }
    public String getCommandName(){
        return commandName;
    }
    public String[] getArgs(){
        return args;
    }
}