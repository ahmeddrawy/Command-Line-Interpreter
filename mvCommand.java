package com.company;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class mvCommand extends Command {
    mvCommand(@NotNull String [] _args , String _path) {
        args = new String[_args.length];
        for (int i = 0 ; i <args.length ; ++i )
            args[i] = _args[i];
        TerminalPath  = Paths.get(_path);
        myPath  = Paths.get(_path);
    }
    mvCommand(@NotNull String [] _args , Path _path){
        args = new String[_args.length];
        for (int i = 0 ; i <args.length ; ++i )
            args[i] = _args[i];
//        mPath = _path;
        TerminalPath = _path;
        myPath = _path;
    }
    protected boolean check() {
        return DirectoryExist(myPath) &&  args.length==2;
    }

    protected void run(String path) {
        if(check()) {
            args[0] = toAbsolutePath(args[0]);
            args[1] = toAbsolutePath(args[1]);
            //File source = new File(args[0]);
            String destination = args[1];
            String source= args[0];
            try {
                File afile = new File(source);
                if (afile.renameTo(new File(destination + '/' +afile.getName())) ){
                    System.out.println("File is moved successful!");
                } else {
                    System.out.println("File is failed to move!");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("no such arguments");
        }
    }

    protected void ShowArguments() {
///todo
    }
}
