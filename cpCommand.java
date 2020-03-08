package com.company;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class cpCommand extends Command {
    protected String secondDirectoryPath;
    protected Path _secondDirectoryPath;
    cpCommand(@NotNull String [] _args , String _path){
        args = new String[_args.length];
        for (int i = 0 ; i <args.length ; ++i )
            args[i] = _args[i];
        TerminalPath  = Paths.get(_path);
        myPath  = Paths.get(_path);
    }
    cpCommand(@NotNull String [] _args , Path _path){
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
    protected void run(String path) throws IOException {
        args[0] = toAbsolutePath(args[0]);
        args[1] = toAbsolutePath(args[1]);
        String source = args[0];
        String destination = args[1];
        if(!DirectoryExist(destination)){
            System.out.println("No such destination ");
            return ;
        }

        String fileName = "" ;
        for(int i = args[0].length() -1 ; i >=0 ; --i){
            if(args[0].charAt(i) == '/')
                break;
            else
                fileName=args[0].charAt(i) + fileName; /// reversed;
        }
        File destinationFile = null;
        if(FileExist(source)) {
             destinationFile = new File(destination + '/' + fileName);
            if (!destinationFile.createNewFile()) {
                System.out.println("file already exists");
                return;
            }
            destinationFile.createNewFile();
        }
        else if(DirectoryExist(source)){
            if(DirectoryExist(destination + '/' + fileName)) {
                System.out.println("Directory already exist");
                return ;
            }
             new File(destination + '/' + fileName).mkdir();
//             destinationFile = new File(destination + '/' + fileName);
            destinationFile = new File(destination);
        }
        else {
            System.out.println("not directory or file with this name");
            return ;
        }
        File src = new File (source);
        try {

            if (FileExist(source))
                FileUtils.copyFile(src, destinationFile);

            else if(DirectoryExist(source))
                FileUtils.copyDirectory(src, destinationFile);
        }catch(IOException e) {
            e.printStackTrace();}
    }
    protected void ShowArguments() {
///todo
    }
}
