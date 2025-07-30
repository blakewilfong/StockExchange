package user;

import common.UserValidator;
import exceptions.InvalidInputException;
import exceptions.InvalidUserException;
import tradable.TradableDTO;

import java.util.HashMap;


public class User {

    private String userId;
    private final HashMap<String, TradableDTO> tradables = new HashMap<>();

    public User(String userIdIn) throws InvalidUserException {
        setUserId(userIdIn);
    }

    private void setUserId(String userIdIn) throws InvalidUserException {
        try {
            userId = UserValidator.validate(userIdIn);
        } catch (InvalidInputException e) {
            throw new InvalidUserException("Bad input: " + e.getMessage(), e);
        }
    }

    public void updateTradable(TradableDTO o) {
        if (o == null) return;
        tradables.put(o.tradableId(), o);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User Id: ").append(userId).append("\n");
        for (String s : tradables.keySet()) {
            TradableDTO t = tradables.get(s);
            sb.append("\tProduct: ").append(t.product());
            sb.append(", Price: ").append(t.price());
            sb.append(", OriginalVolume: ").append(t.originalVolume());
            sb.append(", RemainingVolume: ").append(t.remainingVolume());
            sb.append(", CancelledVolume: ").append(t.cancelledVolume());
            sb.append(", FilledVolume: ").append(t.filledVolume());
            sb.append(", User: ").append(t.user());
            sb.append(", Side: ").append(t.side());
            sb.append(", Id: ").append(t.tradableId());
            sb.append("\n");
        }
        return sb.toString();
    }

}
