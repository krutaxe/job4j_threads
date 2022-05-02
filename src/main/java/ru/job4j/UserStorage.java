package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private static final Map<Integer, User> STORES = new HashMap<>();
    public synchronized boolean add(User user) {
        return STORES.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return STORES.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return STORES.remove(user.getId()) != null;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User from = STORES.get(fromId);
        User to = STORES.get(toId);
        if (from == null || to == null) {
            throw new IllegalArgumentException("users do not exist");
        }
        if (from.getAmount() >= amount) {
            to.setAmount(to.getAmount() + amount);
            from.setAmount(from.getAmount() - amount);
        } else {
            throw new IllegalArgumentException("Not enough funds");
        }
    }

    public static void main(String[] args) {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 1000));
        storage.add(new User(2, 2000));
        storage.add(new User(3, 3000));
        storage.add(new User(4, 4000));
        STORES.forEach((k, v) -> System.out.println(k + " : " + v.getAmount()));
        storage.transfer(1, 2, 500);
        System.out.println("____________________");
        STORES.forEach((k, v) -> System.out.println(k + " : " + v.getAmount()));
        System.out.println(storage.update(new User(1, 999)));
        System.out.println("____________________");
        STORES.forEach((k, v) -> System.out.println(k + " : " + v.getAmount()));
        System.out.println(storage.delete(new User(3, 444)));
        System.out.println("_____________________");
        STORES.forEach((k, v) -> System.out.println(k + " : " + v.getAmount()));
    }
}
