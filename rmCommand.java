package com.company;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class rmCommand extends Command {
    rmCommand(@NotNull String [] _args , String _path) {
        args = new String[_args.length];
        for (int i = 0 ; i <args.length ; ++i )
            args[i] = _args[i];
        TerminalPath  = Paths.get(_path);
        myPath  = Paths.get(_path);
    }
    rmCommand(@NotNull String [] _args , Path _path){
        args = new String[_args.length];
        for (int i = 0 ; i <args.length ; ++i )
            args[i] = _args[i];
        TerminalPath = _path;
        myPath = _path;
    }
    protected boolean check() {
        return DirectoryExist(myPath) && args.length==1;
    }

    protected void run(String path) {
        args[0]= toAbsolutePath(args[0]);
        File file = new File(args[0]);
        TerminalPath = Paths.get(path);
        if(check() && file.isFile()){
            file.delete();
        }
        else if (file.isDirectory()){
            System.out.println("Error : is a Directory");
        }
        else{
            System.out.println("Error too much arguments");
        }
    }

    protected void ShowArguments() {
        ///todo
    }
}
