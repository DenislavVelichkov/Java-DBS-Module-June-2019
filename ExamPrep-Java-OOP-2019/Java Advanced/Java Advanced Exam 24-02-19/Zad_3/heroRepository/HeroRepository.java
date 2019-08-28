package JavaAdvanced.Exam24_02_19.Zad_3.heroRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class HeroRepository {
    private ArrayList<Hero> data;

    public HeroRepository() {
        this.data = new ArrayList<>();
    }

    public void add(Hero hero) {
        this.data.add(hero);
    }

    public void remove(String name) {
        Optional<Hero> tempHero = this.data.stream().filter(hero -> hero.getName().equals(name)).findAny();
        this.data.remove(tempHero);
    }

    public Hero getHeroWithHighestStrength() {
        Hero tempHero = this.data.stream().max(Comparator.comparingInt(o -> o.getItem().getStrength())).get();
        return tempHero;
    }

    public Hero getHeroWithHighestAgility() {
        Hero tempHero = this.data.stream().max(Comparator.comparingInt(o -> o.getItem().getAgility())).get();
        return tempHero;
    }

    public Hero getHeroWithHighestIntelligence() {
        Hero tempHero = this.data.stream().max(Comparator.comparingInt(o -> o.getItem().getIntelligence())).get();
        return tempHero;
    }

    public int getCount() {
        return this.data.size();
    }

   @Override
    public String toString() {
       StringBuilder str = new StringBuilder();
       this.data.stream().forEach(hero -> str.append(hero.toString()));
        return str.toString();
    }
}
