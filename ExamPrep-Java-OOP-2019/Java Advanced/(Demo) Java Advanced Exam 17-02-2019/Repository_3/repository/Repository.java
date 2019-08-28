package JavaAdvanced.DemoExam_17_02_19.Repository_3.repository;

import java.util.LinkedHashMap;
import java.util.Map;

public class Repository {
    private static final int ID = 0;
    private Map<Integer, Person> repo;
    private int index;

    public Repository() {
        this.repo = new LinkedHashMap<>();
        this.index = ID;
    }

    public void add(Person person) {
        this.repo.put(index, person);
        this.index++;
    }

    public Person get(int key) {
        return this.repo.get(key);
    }

    public boolean update(int key, Person newPerson) {
        boolean status = isPresent(key);
        if (status) {
            this.repo.put(key, newPerson);
        }

        return status;
    }

    public boolean delete(int key) {
        boolean status = isPresent(key);

        if (status) {
            this.repo.remove(key);
        }

        return status;
    }

    public int getCount() {
        return this.repo.entrySet().size();
    }

    private boolean isPresent(int key) {
        return this.repo.containsKey(key);
    }

}
