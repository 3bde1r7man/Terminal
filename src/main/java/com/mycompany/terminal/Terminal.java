/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.terminal;

import java.util.Scanner;

/**
 *
 * @author Abelrhman Mostafa
 * @author Ahmed Hanfy
 */
public class Terminal {
    Parser parser;
    //Implement each command in a method, for example:
    public String pwd(){
        return "";
    }
    public void cd(String[] args){

    }
    // echo takes 1 arg and print it
    public String echo(String[] args){
        return "";
    }
    //This method will choose the suitable command method to be called
    public void chooseCommandAction(){
        if (parser.getCommandName().equals("echo")) {
            echo(parser.getArgs());
        } else if (parser.getCommandName().equals("pwd")) {
            pwd();
        } else if (parser.getCommandName().equals("cd")) {
            cd(parser.getArgs());
        } else {
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

