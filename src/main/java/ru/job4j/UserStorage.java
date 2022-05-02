package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    private static final Map<Integer, User> STORES = new HashMap<>();
    @GuardedBy("this")
    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (user != null) {
            STORES.putIfAbsent(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(ru.job4j.User user) {
        boolean rsl = false;
        if (user != null) {
            STORES.replace(user.getId(), user);
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (user != null) {
            STORES.remove(user.getId());
            rsl = true;
        }
        return rsl;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User from = STORES.get(fromId);
        User to = STORES.get(toId);
        if (from.getAmount() >= amount) {
            to.setAmount(to.getAmount() + amount);
            from.setAmount(from.getAmount() - amount);
        } else {
            throw new IllegalArgumentException("Not enough funds");
        }
    }

    public static void main(String[] args) {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 2000));
        storage.add(new User(2, 1000));
        STORES.forEach((k, v) -> System.out.println(k + " : " + v.getAmount()));
        storage.transfer(1, 2, 500);
        STORES.forEach((k, v) -> System.out.println(k + " : " + v.getAmount()));
    }
}
