package com.company;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class MyParser {
    private String[] args; // Will be filled by arguments extracted by parse method
    private     String cmd; //
    private      Queue<Vector<String>> cmds;
    private     Vector<String> curr ;
    public MyParser(){
        cmd = null ;
    }
    public boolean parse (String input){/// todo check if command exist
        if(input == null)
            return false ; /// todo check when we return false from parsing
        mysplit(input);
        return true ;///todo remove later

    }
//    public String getCmd(){
//        return cmd ;
//    }
    public void mysplit(String s ){

        Queue<Vector<String>> vec= new LinkedList<Vector<String>>();
        Vector<String > tempvec= new Vector<String>();
        String temp = "";
        for(int i = 0 ; i < s.length(); ++i){
             temp = "";

            boolean quotes =false ;
            boolean endOfCommand = false ;
            int j=  0 ;
            for ( j=  i ; j< s.length(); ++j){
                if(s.charAt(j) == '|' || s.charAt(j) == '>' || s.charAt(j) == '<'){
                    endOfCommand= true;
                    break;
                }
                else if(s.charAt(j) == ' '&&!quotes )
                    break;
                else if(s.charAt(j) == '"'&& !quotes)
                    quotes =true;
                else if(s.charAt(j) == '"' &&quotes)
                    break;
                else
                    temp+=s.charAt(j);
            }
            i = j ; /// to move cursor
            if(temp.length() > 0)
            tempvec.add(temp);
            if(endOfCommand){
                Vector<String> t = new Vector<String>(tempvec);
                vec.add(t);
                tempvec.clear();
                if(s.charAt(j) == '>' || s.charAt(j) == '<'){
                    String opTopush  = "";
                    opTopush+=s.charAt(j);
                    if(s.charAt(j) =='>' && s.charAt(j+1)=='>') {
                        opTopush += s.charAt(j);
                        ++j;
                        i=j;
                    }
                    tempvec.add
                            (opTopush);

                }
            }
        }
        if(tempvec.size()>0){
            Vector<String> t = new Vector<String>(tempvec);
            vec.add(t);
        }
        cmds = vec;
    }
    public void PrintArgs(){
        for(int i = 0 ; i < args.length ;++i){
            System.out.println(args[i]);
        }
    }
    private void processNext(){

        curr = cmds.element();
        cmds.remove();
    }
    boolean QueueNotEmpty(){
        return cmds.size() > 0;
    }
    String currentcmd(){
        processNext();
        return curr.get(0);
    }
    boolean DirectCommand(){
        if(!QueueNotEmpty())
            return  false ;
        Vector<String > nxt= cmds.peek();
        if(nxt.get(0).compareTo(">") ==0 || nxt.get(0).compareTo("<") ==0  || nxt.get(0).compareTo(">>") ==0 )
            return true ;
        return  false ;
    }

    String [] currentArgs(){
        String [] _args = new String[curr.size() -1];
        for (int  i = 1; i < curr.size() ; ++i)
            _args[i-1] = curr.get(i);
        return _args;
    }


}
