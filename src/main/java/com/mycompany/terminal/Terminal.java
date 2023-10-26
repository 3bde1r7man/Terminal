/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.terminal;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Abelrhman Mostafa
 * @author Ahmed Hanfy
 */

public class Terminal {
    Parser parser;

    // echo takes 1 arg and print it
    public void echo(String[] args){
        if (args.length >= 1) {
            String message = String.join(" ", args);
            System.out.println(message);
        } else {
            System.out.println("No message to echo.");
        }
    }

    // print the current working directory
    public void pwd(){
        System.out.println(System.getProperty("user.dir"));
    }

    // Change the current working directory to a given path 
    public void cd(String[] args){
        // If no passed arguments
        if(args.length == 0){
            System.out.println("You must specify a directory.");
        }else if(args.length == 1 && "..".equals(args[0])){
            File currentDirectory = new File(System.getProperty("user.dir"));
            // Navigate up one directory level
            File parentDirectory = currentDirectory.getParentFile();
            System.setProperty("user.dir", parentDirectory.getAbsolutePath());
            System.out.println("Changed working directory to: " + System.getProperty("user.dir"));
        }else if (args.length == 1) {
            File directory = new File(args[0]);
            if (directory.exists() && directory.isDirectory()) {
                // The path exists and is a directory
                System.out.println("The directory exists: " + directory.getAbsolutePath());

                // Change the current working directory
                if (directory.isDirectory()) {
                    System.setProperty("user.dir", directory.getAbsolutePath());
                    System.out.println("Changed working directory to: " + System.getProperty("user.dir"));
                }
            } else {
                // The path does not exist or is not a directory
                System.out.println("Invalid path or The directory does not exist: " + directory.getAbsolutePath());
            }
        }else { // If more than one argument
            System.out.println("There is no specified path.");
        }
    }
    
    // lists the contents of the current directory sorted alphabetically or descending order
    public void ls(String[] args) {
        File currentDirectory = new File(System.getProperty("user.dir"));

        // List the files in the current directory
        File[] files = currentDirectory.listFiles();
        
        if (files != null) {
            System.out.println("Files in the current directory:");
            if(args.length == 1 && "-r".equals(args[0])) {
                for (int i = files.length - 1; i >= 0; i--) {
                    System.out.println(files[i].getName());
                }
            } else if(args.length == 0){
                for (File file : files) {
                    System.out.println(file.getName());
                }
            }
            else {
                System.out.println("Unknown argument.");
            }
        }
    }
    // Takes 1 argument which is either the full path or the relative (short) path that ends with a file name and creates this file.
    public void touch(String[] args){

    }
    // 
    public void rm(String[] args){
        if(args.length == 0){
            System.out.println("You must specify a file name.");
        }else if(args.length == 1){
            File file = new File(args[0]);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File " + args[0] + " has been removed.");
                } else {
                    System.out.println("Unable to remove the file " + args[0]);
                }
            } else {
                System.out.println("File " + args[0] + " does not exist in the current directory.");
            }
        }
        
    }
    //This method will choose the suitable command method to be called
    public void chooseCommandAction(){
        if (parser.getCommandName().equals("echo")) {
            echo(parser.getArgs());
        } else if (parser.getCommandName().equals("pwd")) {
            pwd();
        } else if (parser.getCommandName().equals("cd")) {
            cd(parser.getArgs());
        }else if (parser.getCommandName().equals("ls")) {
            ls(parser.getArgs());
        }else if (parser.getCommandName().equals("rm")) {
            rm(parser.getArgs());
        } else if (parser.getCommandName().equals("exit")) {
            System.exit(0);
        }else {
            System.out.println("Unknown command: " + parser.getCommandName());
        }
    }
    public static void main(String[] args){
        System.out.print("Enter a command: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        
        Terminal terminal = new Terminal();
        terminal.parser = new Parser();

        if (terminal.parser.parse(input)) {
            terminal.chooseCommandAction();
        } else {
            System.out.println("Failed to parse the command.");
        }
        scanner.close();
        
    }

}

