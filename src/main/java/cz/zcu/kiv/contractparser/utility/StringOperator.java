package cz.zcu.kiv.contractparser.utility;

public class StringOperator {

    public static String getPreviousChar(String string, int index){

        if(index > 0){
            return "" + string.charAt(index - 1);
        }
        else{
            return null;
        }
    }

    public static boolean verifyMethodClass(String statement, String searchedClass, int index){

        //System.out.println(statement);
        //System.out.println("INDEX: " + index);

        if(statement == null || searchedClass == null){
            return false;
        }

        if(index == 0){
            return true;
        }

        if(getPreviousChar(statement, index).equals(".")){

            int indexStart = index-1 - searchedClass.length();
            if(indexStart < 0){
                return false;
            }
            else{
                if(statement.substring(indexStart, index-1).equals(searchedClass)){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else{
            return false;
        }
    }
}
