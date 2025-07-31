package user;

import exceptions.DataValidationException;
import exceptions.InvalidUserException;
import tradable.TradableDTO;

import java.util.TreeMap;

public final class UserManager {

    private static UserManager instance;
    private final static TreeMap<String, User> userMap = new TreeMap<>();

    public static UserManager getInstance(){
        if (instance == null)
            instance = new UserManager();
        return instance;
    }

    private UserManager(){

    }

    public void init(String[] usersIn) throws DataValidationException {

        for (String s : usersIn) {
            if (s == null) throw new DataValidationException("userId in array cannot be null");

            try {
                User u = new User(s);
                userMap.put(u.getId(), u);
            } catch (InvalidUserException e) {
                throw new DataValidationException("userId not valid: " + e.getMessage(), e);
            }
        }
    }

    public void updateTradable(String userId, TradableDTO o) throws DataValidationException{
        if (userId == null) throw new DataValidationException("userId can not be null");
        if (o == null) throw new DataValidationException("TradableDTO can not be null");
        if (!userMap.containsKey(userId)) throw new DataValidationException("User does not exist");
        userMap.get(userId).updateTradable(o);
    }



    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(User u: userMap.values()){
            sb.append(u.toString());
            //sb.append("\t\t...\n");
            sb.append("\n");
        }
        return sb.toString();
    }



}
