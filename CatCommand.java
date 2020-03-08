package com.company;

import java.io.*;
import java.nio.file.Paths;

public class CatCommand extends Command {
    CatCommand(String []_args , String _path){
        args = _args;
        mPath = _path;
        TerminalPath = Paths.get(_path);
    }
    protected boolean check(){
        boolean yes = true ;
        for(int i = 0 ; i < args.length ; ++i){
            yes = yes  && FileExist(toAbsolutePath(args[i]));
        }
        return yes;
    }
    protected void run(String path) {
        mPath = toAbsolutePath(path);
        if (check()){
            for(int i = 0 ; i < args.length; ++i) {
                try (BufferedReader br = new BufferedReader(new FileReader(toAbsolutePath(args[i])))) {
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        else  {
            System.out.println("Too much arguments");

        }

    }
    String fileToProcess(){ /// we checked the file name before , so we have 2 cases short or complete path

        if(FileExist(args[0]))
            return args[0];
        return mPath +'/' + args[0];
    }
    public void ShowArguments(){/// todo

    }
}
