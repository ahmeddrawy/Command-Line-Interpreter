package com.company;

import javafx.util.Pair;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;

/** todo help function for every command - take care of commands with no class
 *  todo operator | < >
 *  todo args ,  rm ,more
 *
  */

/// todo : Pipe operator, > , >> , args, paths
public class Terminal {
    private String CurrentPath  = null;
    private String HomePath = "/home/tw3" ; /// todo current home on my machine , if you gonna try it with another machine you have to change it
    MyParser terminalParser ;
    String[]ListOfCommands = {"cd"};
    Terminal() throws IOException {
        terminalParser = new MyParser();
        CurrentPath = HomePath;
        while (true)
        PromptForCommands();

    }
    Terminal(String path) throws IOException {
        CurrentPath = path ;
        while(true)
            PromptForCommands();

    }
    Terminal(MyParser p) throws IOException {
        CurrentPath = HomePath ;
        terminalParser = p ;
        while(true)
            PromptForCommands();

    }
    private void PromptForCommands() throws IOException {
        if(CurrentPath == null || CurrentPath.compareTo(HomePath) ==0)
            System.out.print("Lamya'sPC:~$ ");
        else
            System.out.print("Lamya'sPC:~"+CurrentPath + " $ ");
//        runCommand(); /// todo remove

        Scanner s = new Scanner(System.in);
        String cmd =  s.nextLine();

        terminalParser.parse(cmd);
        while(terminalParser.QueueNotEmpty())
            runCommand(terminalParser.currentcmd() , terminalParser.currentArgs() );
    }
    protected boolean DirectoryExist(String path){  /// todo handle the short path
        File tmpFile = new File(path);
        return tmpFile.exists() && tmpFile.isDirectory();
    }
    protected boolean DirectoryExist(Path _path){  ///
        return _path.toFile().exists()&& _path.toFile().isDirectory();
    }
    protected boolean FileExist(String path){
        File tmpFile = new File(path);
        return tmpFile.exists() && tmpFile.isFile();
    }

    String toAbsolutePath(String _path){
        Path path = Paths.get(_path);
        if(!path.toFile().isAbsolute()) {
            path = Paths.get(CurrentPath + File.separatorChar + _path);
        }
        return path.normalize().toAbsolutePath().toString();
    }
    Pair<Boolean,  Object> DirectCommmand(String operator,String[] directionFile ) throws IOException {


            if(directionFile.length > 1){
                System.out.println("too much argument");
                return new Pair<Boolean , Object>(false , null);
            }
            directionFile[0] = toAbsolutePath(directionFile[0]);
            new File(directionFile[0]).createNewFile();
            if(FileExist(directionFile[0])) {
                PrintStream tp = null;
                InputStream ip = null;
                if(operator.compareTo(">>") == 0)
                    tp = new PrintStream(new FileOutputStream(directionFile[0] , true));
                else if(operator.compareTo(">") == 0 )
                    tp = new PrintStream(new FileOutputStream(directionFile[0]));
                else {
                    ip = new FileInputStream(directionFile[0]);
                    return new Pair <Boolean, Object>(true , ip);
                }
//               p = tp;
                return new Pair<Boolean , Object>(true , tp);
            }
            else{
                System.out.println("Not a file");
                return new Pair<Boolean , Object>(false , null);
            }


    }
    public void runCommand(String cmd , String args[]) throws IOException { /// we have < operator but we can't take input because we already parsed the command and it's arguments
//        System.out.println(cmd + " " + args[0]);
        PrintStream consoleout = System.out;
        InputStream consolein = System.in;
        InputStream in = null ;
        PrintStream p = null ;
        if(terminalParser.DirectCommand()) {
            String operator = terminalParser.currentcmd();/// redundant to move the queue
            String[] directionFile = terminalParser.currentArgs();
            Pair <Boolean , Object> pair = DirectCommmand(operator , directionFile);
            if(!pair.getKey())
                return  ;
            else if(operator.compareTo("<") == 0){
                in  = (InputStream)pair.getValue();
            }
            else
                p = (PrintStream) pair.getValue();
        }
        if(p != null){

            System.setOut(p);
        }
        if( cmd.compareTo("cd") == 0){
                cdCommand c = new cdCommand(args, CurrentPath);
                CurrentPath = c.changeDirectory();
        }
        else if( cmd.compareTo("pwd") == 0)
           pwd(args);
        else if(cmd.compareTo("ls") == 0){
           lsCommand l = new lsCommand(args , CurrentPath);
           l.run(CurrentPath);
        }
        else if(cmd.compareTo("cat") == 0){
           CatCommand c = new CatCommand(args , CurrentPath);
           c.run(CurrentPath);
        }
        else if(cmd.compareTo("cp") == 0){
            cpCommand c =new cpCommand(args , CurrentPath);
            c.run(CurrentPath);
        }
        else if(cmd.compareTo("date") == 0){
            Date d = new Date();
            System.out.println  (d);
        }
        else if(cmd.compareTo("mkdir") ==0 ){
            mkdirCommand c  = new mkdirCommand(Paths.get(CurrentPath) ,args );
            c.run(CurrentPath);
        }
        else if(cmd.compareTo("clear") == 0){
            clear(args);
        }
        else if(cmd.compareTo("help") == 0){
            help(args);
        }
        else if(cmd.compareTo("mv") == 0){
            mvCommand c = new mvCommand(args, CurrentPath);
            c.run(CurrentPath);
        }
        else if(cmd.compareTo("rm") == 0) {
            rmCommand c = new rmCommand(args, CurrentPath);
            c.run(CurrentPath);
        }
        else if(cmd.compareTo("rmdir") == 0) {
            rmdirCommand c = new rmdirCommand(args, CurrentPath);
            c.run(CurrentPath);
        }
        else if(cmd.compareTo("more") == 0){
            moreCommand c = new moreCommand(args, CurrentPath);
            c.run(CurrentPath);
        }
        else if(cmd.compareTo("exit") == 0){
            System.exit(1);
        }
        else if(cmd.compareTo("args")==0){
            if(args.length > 1)
                System.out.println("too much arguments");
            else if(args.length == 0 )
                System.out.println("need arguments");
            else {
                /// todo

            }

        }
        else {
            System.out.println(cmd +" command not found ");

        }
        System.setOut(consoleout);
        System.setIn(consolein);
    }


    void pwd(String args[]){ /// todo check the parameters size
        if(args.length >0){
            System.out.println("Too much arguments" );
        }
        else
            System.out.println(CurrentPath);
    }
    void help(String args[]){
        if(args.length >0){
            System.out.println("Too much arguments" );
        }
        else {
            System.out.println("\t--cd:     \tchange current directory");
            System.out.println("\t--ls:     \tlist files and directories names");
            System.out.println("\t--cp:     \tcopy files and directories");
            System.out.println("\t--args: \tlist all command arguments");
            System.out.println("\t--mv:     \tmoves files or directories from one place to another");
            System.out.println("\t--rm:     \tremove objects such as files and empty directories");
            System.out.println("\t--mkdir: \tcreate new directory");
            System.out.println("\t--rmdir: \tremove empty directories");
            System.out.println("\t--cat:     \tcreate file and view its content");
            System.out.println("\t--more: \tview output in scrollable manner");
            System.out.println("\t--pwd:     \tget full system path");
            System.out.println("\t--date: \tcurrent system date and time");
            System.out.println("\t--help: \tlist all possible commands");
            System.out.println("\t-->:     \tredirect output to be overwritten in a file");
            System.out.println("\t-->>:     \tredirect output to be appended to a file");
            System.out.println("\t--|:     \tredirect output of previous command as an input to another command");
            System.out.println("\t--clear: \tclear terminal screen");
            System.out.println("\t--exit: \texit (close) terminal");
        }
    }
    void clear(String args []){
        if(args.length >0){
            System.out.println("Too much arguments" );
        }
        else
        {
            for(int i=0; i<20; i++)
                System.out.println("\n");
        }
    }
}
