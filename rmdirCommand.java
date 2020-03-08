package com.company;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
public class rmdirCommand extends Command {
    rmdirCommand(@NotNull String [] _args , String _path) {
        args = new String[_args.length];
        for (int i = 0 ; i <args.length ; ++i )
            args[i] = _args[i];
        TerminalPath  = Paths.get(_path);
        myPath  = Paths.get(_path);
    }
    rmdirCommand(@NotNull String [] _args , Path _path){
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
        args[0] = toAbsolutePath(args[0]);
        File dir = new File(args[0]);
        TerminalPath = Paths.get(path);

        if (dir.isDirectory() == false) {
            System.out.println("Not a directory.");
            return;
        } else if (check() == false) {
            System.out.println("too much arguments");
            return;
        } else {
            dir.delete();
        }
        /*
        File dir = new File(args[0]);
        File[] files = dir.listFiles();
        for (File file : files) {
            file.delete();
        }*/ // deletes directories recursively (if a directory has another directories inside it)
    }

    protected void ShowArguments() {
        ///todo
    }
}
