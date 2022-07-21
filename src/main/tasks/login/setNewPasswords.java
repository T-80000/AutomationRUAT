package main.tasks.login;

public class setNewPasswords {
    public static String with(String pwd){
        String pwd2;
        if(pwd.substring(8,9).equals("8")){
            pwd2 = pwd.substring(0,7)+"7$";
        }else
        {
            pwd2 = pwd.substring(0,8)+"8$";
        }
        return pwd2;
    }
}
