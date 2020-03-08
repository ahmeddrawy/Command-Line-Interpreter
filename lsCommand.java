package com.company;

//import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
/// todo handle the -1 option and no options print next to each other
public class lsCommand extends Command {

    lsCommand(@NotNull String []_args , String _path ){
        args = new String[_args.length];
        for (int i = 0 ; i <args.length ; ++i )
            args[i] = _args[i];
        mPath = _path;
    }
    protected  boolean check( ){    /// no arguments
        return args.length == 0;
    }
    protected  void run(String path){   /// todo
        if(check()) {
            File f = new File(path);
            ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
            for (String s : names) {
                System.out.println(s);
            }
        }
        else {
            System.out.println("Too much arguments");
        }
    }
    public void ShowArguments(){ ///todo
                                            /** we need to determine what arguments we need in each command*/
    }
}
