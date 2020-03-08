package com.company;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class mkdirCommand extends Command{
    mkdirCommand(Path _terminal , String[]_args){
        TerminalPath = _terminal;
        args = _args;
    }
    protected  boolean check(){
        return args.length ==1 ; ///todo
    }
    protected  void run(String _path){
//        args[0] = toAbsolutePath(args[0]);
        myPath  = Paths.get(toAbsolutePath(args[0]));
        TerminalPath = Paths.get(_path);
        if(check()){
//             new File(TerminalPath.toString() +"/" + args[0]).mkdir();
            new File(myPath.toString()).mkdir();
        }
        else {
            System.out.println("error too much arguments");
        }
    }
    protected  void ShowArguments(){ ///todo

    }
}
