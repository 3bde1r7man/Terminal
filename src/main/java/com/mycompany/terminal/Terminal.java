/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List; 

/**
 *
 * @author Abelrhman Mostafa
 * @author Ahmed Hanfy
 */

public class Terminal {
    Parser parser;
    ArrayList<String> history = new ArrayList<String>();
    ArrayList<String> output = new ArrayList<String>();
    ArrayList<String> error = new ArrayList<String>();

    // echo takes 1 arg and print it
    public void echo(String[] args){
        if (args.length >= 1) {
            String message = String.join(" ", args);
            output.add(message);
        } else {
            error.add("No message to echo.");
        }
    }

    // print the current working directory
    public void pwd(){
        output.add(System.getProperty("user.dir"));
    }

    // Change the current working directory to a given path 
    public void cd(String[] args){
        // If no passed arguments
        if(args.length == 0){
            output.add("You must specify a directory.");
        }else if(args.length == 1 && "..".equals(args[0])){
            File currentDirectory = new File(System.getProperty("user.dir"));
            // Navigate up one directory level
            File parentDirectory = currentDirectory.getParentFile();
            System.setProperty("user.dir", parentDirectory.getAbsolutePath());
            output.add("Changed working directory to: " + System.getProperty("user.dir"));
        }else if (args.length == 1) {
            File directory = new File(args[0]);
            if (directory.isAbsolute()) {
                // Absolute path provided
                if (directory.exists() && directory.isDirectory()) {
                    System.setProperty("user.dir", directory.getAbsolutePath());
                    output.add("Changed working directory to: " + System.getProperty("user.dir"));
                } else {
                    error.add("Invalid path or The directory does not exist: " + directory.getAbsolutePath());
                }
            } else {
                // Relative path provided
                File currentDirectory = new File(System.getProperty("user.dir"));
                File newDirectory = new File(currentDirectory, args[0]);

                if (newDirectory.exists() && newDirectory.isDirectory()) {
                    System.setProperty("user.dir", newDirectory.getAbsolutePath());
                    output.add("Changed working directory to: " + System.getProperty("user.dir"));
                } else {
                    error.add("Invalid path or The directory does not exist: " + newDirectory.getAbsolutePath());
                }
            }
        }else if (args.length > 1) { 
            String path = String.join(" ", args);
            File directory = new File(path);
            if (directory.isAbsolute()){
                // Absolute path provided
                if (directory.exists() && directory.isDirectory()) {
                    System.setProperty("user.dir", directory.getAbsolutePath());
                    output.add("Changed working directory to: " + System.getProperty("user.dir"));
                } else {
                    error.add("Invalid path or The directory does not exist: " + directory.getAbsolutePath());
                }
            } else {
                // Relative path provided
                File currentDirectory = new File(System.getProperty("user.dir"));
                File newDirectory = new File(currentDirectory, path);

                if (newDirectory.exists() && newDirectory.isDirectory()) {
                    System.setProperty("user.dir", newDirectory.getAbsolutePath());
                    output.add("Changed working directory to: " + System.getProperty("user.dir"));
                } else {
                    error.add("Invalid path or The directory does not exist: " + newDirectory.getAbsolutePath());
                }
            
            }
        }
    }
    
    // lists the contents of the current directory sorted alphabetically or descending order
    public void ls(String[] args) {
        File currentDirectory = new File(System.getProperty("user.dir"));

        // List the files in the current directory
        File[] files = currentDirectory.listFiles();
        
        if (files != null) {
            output.add("Files in the current directory:");
            if(args.length == 1 && "-r".equals(args[0])) {
                for (int i = files.length - 1; i >= 0; i--) {
                    output.add(files[i].getName());
                }
            } else if(args.length == 0){
                for (File file : files) {
                    output.add(file.getName());
                }
            }
            else {
                error.add("Unknown argument.");
            }
        }
    }
    // Takes 1 argument which is either the full path or the short path that ends with a directory name and creates this directory.
    public void mkdir(String[] args){
        if(args.length == 0){
            error.add("No arguments specified.");
        }
        String path = args[0];
        if(args.length > 1){
            path = String.join(" ", args);
        }
        File currentDirectory = new File(System.getProperty("user.dir"));
        File directory = new File(currentDirectory, path);
        if (directory.exists()) {
            error.add("Directory '" + path + "' already exists.");
        } else {
            if (directory.mkdirs()) {
                output.add("Directory '" + path + "' created successfully.");
            } else {
                error.add("Failed to create directory '" + path + "'.");
            }
        }
    }
    // Takes 1 argument which is either the full path or the short path that ends with a file name and creates this file.
    public void touch(String[] args){
        try {
            String path = args[0];
            if(args.length > 1){
                path = String.join(" ", args);
            }
            File currentDirectory = new File(System.getProperty("user.dir"));
            File file = new File(currentDirectory, path);
            if(file.createNewFile()){
                output.add("File " + file.getName() + " created successfully.");
            }else{
                error.add("File " + file.getName() + " already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // rmoves file from the current directory
    public void rm(String[] args){
        if(args.length == 0){
            error.add("You must specify a file name.");
        }else if(args.length == 1){
            File currentDirectory = new File(System.getProperty("user.dir"));
            File file = new File(currentDirectory, args[0]);
            if (file.exists()) {
                if (file.delete()) {
                    output.add("File " + args[0] + " has been removed.");
                } else {
                    output.add("Unable to remove the file " + args[0]);
                }
            } else {
                error.add("File " + args[0] + " does not exist in the current directory.");
            }
        }
        
    }

    public void cat(String[] args) {
        if(args.length == 1){
            printFileContent(args[0]);
        }else if (args.length == 2){
            concatenateAndPrintFiles(args[0], args[1]);
        }else{
            error.add("no arguments specified or more arguments specified.");
        }
    }
    public void printFileContent(String fileName) {
        try {
            File currentDirectory = new File(System.getProperty("user.dir"));
            fileName = currentDirectory.getAbsolutePath() + "\\" + fileName;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
            reader.close();
        } catch (IOException e) {
            error.add("Error reading the file: " + e.getMessage());
        }
    }
    
    public void concatenateAndPrintFiles(String fileName1, String fileName2) {
        try {
            File currentDirectory = new File(System.getProperty("user.dir"));
            fileName1 = currentDirectory.getAbsolutePath() + "\\" + fileName1;
            fileName2 = currentDirectory.getAbsolutePath() + "\\" + fileName2;
            BufferedReader reader1 = new BufferedReader(new FileReader(fileName1));
            BufferedReader reader2 = new BufferedReader(new FileReader(fileName2));

            String line;
            while ((line = reader1.readLine()) != null) {
                output.add(line);
            }
            reader1.close();

            while ((line = reader2.readLine()) != null) {
                output.add(line);
            }
            reader2.close();
        } catch (IOException e) {
            System.err.println("Error reading the file(s): " + e.getMessage());
        }
    }
    // count the number of line &  words and characters in the file
    public void wc(String[] args) {
        String fileName = args[0];
        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;
        try {
            File currentDirectory = new File(System.getProperty("user.dir"));
            fileName = currentDirectory.getAbsolutePath() + "\\" + fileName;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                String[] words = line.split("\\s+"); // Split line into words
                wordCount += words.length;
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        output.add("Lines: " + lineCount);
        output.add("Words: " + wordCount);
        output.add("Characters: " + charCount);
        output.add("File Name: " + fileName);
    }

    // history command
    public void history(String[] args){
        if(args.length == 0){
            for(int i = 0; i < history.size(); i++){
                output.add(history.get(i));
            }
        }else{
            error.add("Unknown argument.");
        }
    }
    
    // cp Takes 2 arguments, both are files and copies the first onto the second.
    public void cp(String[] args){
        if(args.length == 2){
            try {
                File currentDirectory = new File(System.getProperty("user.dir"));
                args[0] = currentDirectory.getAbsolutePath() + "\\" + args[0];
                args[1] = currentDirectory.getAbsolutePath() + "\\" + args[1];
                BufferedReader reader = new BufferedReader(new FileReader(args[0]));
                FileWriter writer = new FileWriter(args[1]);
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line + "\n");
                }
                reader.close();
                writer.close();
            } catch (IOException e) {
                error.add("Error reading the file: " + e.getMessage());
            }
        }else{
            error.add("no arguments specified or more arguments specified.");
        }
    }
    // rmdir command delete directory only if it is empty
    public void rmdir(String[] args){
        if(args.length == 0){
            error.add("You must specify a directory name.");
        }else if(args.length == 1){
            if(args[0].equals("*")){
                File currentDirectory = new File(System.getProperty("user.dir"));
                File[] files = currentDirectory.listFiles();
                for (File file : files) {
                    if(file.isDirectory()){
                        File[] subDirs = file.listFiles();
                        if(subDirs.length == 0){
                            file.delete();
                        }
                    }
                }
            }else{
                File currentDirectory = new File(System.getProperty("user.dir"));
                args[0] = currentDirectory.getAbsolutePath() + "\\" + args[0];
                File file = new File(args[0]);
                if (file.exists()) {
                    File[] subDirs = file.listFiles();
                    if(subDirs.length == 0){
                        if (file.delete()) {
                            output.add("Directory " + file.getName() + " has been removed.");
                        } else {
                            error.add("Unable to remove the directory " + file.getName());
                        }
                    }else{
                        error.add("Directory " + file.getName()  + " is not empty.");
                    }
                } else {
                    error.add("Directory " + file.getName()  + " does not exist in the current directory.");
                }
            }
            
        }
    }

    //This method will choose the suitable command method to be called
    public void executeTerminal() {
        chooseCommandAction();
        Scanner scanner = new Scanner(System.in);
        while (!parser.getCommandName().equals("exit")) {
            if (parser.isOutputToFile()) {
                writeOutputToFileInternal(false);
            }
            if (parser.isOutputToAppend()) {
                writeOutputToFileInternal(true);
            } else {
                printOutput();
            }
            System.out.print("Enter a command: ");
            parser.parse(scanner.nextLine());
            chooseCommandAction();

        }
        scanner.close();
    }
    
    public void printOutput() {
        if (!error.isEmpty()) {
            for (String message : error) {
                System.err.println(message);
            }
            error.clear();
        } else {
            for (String message : output) {
                System.out.println(message);
            }
            output.clear();
        }
    }

    private void writeOutputToFileInternal(boolean append) {
        try {
            FileWriter fileWriter = new FileWriter(parser.getOutputFile(), append);

            List<String> messages = error.isEmpty() ? output : error;
            for (String message : messages) {
                fileWriter.write(message + "\n");
            }
            messages.clear();

            fileWriter.close();
        } catch (IOException e) {
            error.add("An error occurred.");
            e.printStackTrace();
        }
    }

    public void chooseCommandAction() {
        history.add(parser.getCommandName());
        if (parser.getCommandName().equals("echo")) {
            echo(parser.getArgs());
        } else if (parser.getCommandName().equals("pwd")) {
            pwd();
        } else if (parser.getCommandName().equals("cd")) {
            cd(parser.getArgs());
        } else if (parser.getCommandName().equals("ls")) {
            ls(parser.getArgs());
        } else if(parser.getCommandName().equals("mkdir")) {
            mkdir(parser.getArgs());
        } else if(parser.getCommandName().equals("touch")) {
            touch(parser.getArgs());
        } else if (parser.getCommandName().equals("rm")) {
            rm(parser.getArgs());
        } else if (parser.getCommandName().equals("cat")){
            cat(parser.getArgs());
        } else if(parser.getCommandName().equals("wc")) {
            wc(parser.getArgs());
        } else if (parser.getCommandName().equals("history")) {
            history(parser.getArgs());
        } else if (parser.getCommandName().equals("cp")) {
            cp(parser.getArgs());
        } else if (parser.getCommandName().equals("rmdir")) {
            rmdir(parser.getArgs());
        } else if (parser.getCommandName().equals("exit")) {
            System.exit(0);
        } else {
            error.add("Unknown command: " + parser.getCommandName());
        }
    }

    public static void main(String[] args){
        System.out.print("Enter a command: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        
        Terminal terminal = new Terminal();
        terminal.parser = new Parser();

        if (terminal.parser.parse(input)) {
            terminal.executeTerminal();
        } else {
            System.out.println("Failed to parse the command.");
        }
        scanner.close();

    }

}

