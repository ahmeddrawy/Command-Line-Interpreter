package com.company;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class moreCommand extends Command {
    moreCommand(@NotNull String [] _args , String _path){
        args = new String[_args.length];
        for (int i = 0 ; i <args.length ; ++i )
            args[i] = _args[i];
        TerminalPath  = Paths.get(_path);
        myPath  = Paths.get(_path);
    }
    protected boolean check() {
        return DirectoryExist(myPath) && args.length==1 &&FileExist(args[0]);
    }
    protected void run(String path) {
        args[0] = toAbsolutePath(args[0]);
        if(check()){
            File file = new File(args[0]);
            Scanner s = new Scanner(System.in);
            String moreInput;
            TerminalPath = Paths.get(path);
            int i=0;

                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String st;
                    while ((st = br.readLine() )!= null){
                        System.out.println("\t" + st);
                        if ((i+1)%7 == 0)
                        {
                            System.out.println("--more-- write more to continue, q to quit");
                            moreInput = s.nextLine();
                            if(moreInput.equals("q"))
                                break;
                        }
                        i++;
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

        }
        else
            System.out.println("too much arguments");
    }

    protected void ShowArguments() {
        ///todo
    }
}
