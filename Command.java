package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Command {
    String []args ;
    String mPath;
    Path myPath ;
    Path TerminalPath ;


    protected abstract boolean check();
    protected abstract void run(String path) throws IOException;
    protected abstract void ShowArguments();
    protected boolean FileExist(String path){
        File tmpFile = new File(path);
        return tmpFile.exists() && tmpFile.isFile();
    }
    protected boolean DirectoryExist(String path){  /// todo handle the short path
        File tmpFile = new File(path);
        return tmpFile.exists() && tmpFile.isDirectory();
    }
    protected boolean DirectoryExist(Path _path){  ///
       return _path.toFile().exists()&& _path.toFile().isDirectory();
    }

    String toAbsolutePath(String _path){
        Path path = Paths.get(_path);
        if(!path.toFile().isAbsolute()) {
            path = Paths.get(TerminalPath.toString() + File.separatorChar + _path);
        }
        return path.normalize().toAbsolutePath().toString();
    }
}
