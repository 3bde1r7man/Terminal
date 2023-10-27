package com.mycompany.terminal;
public class Parser {
    String commandName;
    String[] args;
    boolean outputToFile = false;
    boolean outputToAppend = false;
    String outputFile = null;

    public boolean parse(String input) {
        // Split the input into commandName and args
        String[] words = input.split("\\s+");
        
        // Initialize args as an empty array with the same length as words
        int length = words.length;
        for (int i = 0; i < words.length; i++) {
            if (i == words.length - 2) {
                if (words[i].equals(">")) {
                    outputToFile = true;
                    outputToAppend = false;
                    outputFile = words[i + 1];
                    length = i;
                    break;
                }
                else if (words[i].equals(">>")) {
                    outputToAppend = true;
                    outputToFile = false;
                    outputFile = words[i + 1];
                    length = i;
                    break;
                } else {
                    outputToAppend = false;
                    outputToFile = false;
                    outputFile = null;
                }
            }
        }
        
        args = new String[length - 1];
        // Set commandName to the first word
        commandName = words[0];

        for (int i = 1; i < words.length && words[i] != ""; i++) {
            if(words[i].equals(">") || words[i].equals(">>")) {
                break;
            }
            args[i - 1] = words[i];
        }


        return true;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }

    public boolean isOutputToFile() {
        return outputToFile;
    }

    public boolean isOutputToAppend() {
        return outputToAppend;
    }

    public String getOutputFile() {
        return outputFile;
    }
}