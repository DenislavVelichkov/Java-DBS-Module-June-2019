package heroRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class HeroRepositoryTests {
    private HeroRepository heroRepository;

    @Before
    public void setUp() {
        this.heroRepository = new HeroRepository();
    }

    @Test(expected = NullPointerException.class)
    public void createInvalidHero() {
        this.heroRepository.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createHeroDuplicate() {
        this.heroRepository.create(new Hero("Peshko", 10));
        this.heroRepository.create(new Hero("Peshko", 5));
    }

    @Test(expected = NullPointerException.class)
    public void removeInvalidHero() {
        this.heroRepository.remove(null);
    }

    @Test
    public void removePlayerShouldReturnTrue() {
        this.heroRepository.create(new Hero("Peshko", 5));
        Assert.assertTrue(this.heroRepository.remove("Peshko"));
    }

    @Test
    public void returnHeroWithHighestLevel() {
        Hero hero = new Hero("Stapi", 10);
        Hero hero1 = new Hero("Peshko", 15);
        Hero hero2 = new Hero("Zafircho", 20);

        this.heroRepository.create(hero);
        this.heroRepository.create(hero1);
        this.heroRepository.create(hero2);

        Assert.assertEquals(hero2, this.heroRepository.getHeroWithHighestLevel());
    }

    @Test
    public void getHeroShouldWOrk() {
        Hero hero = new Hero("Zafircho", 20);
        this.heroRepository.create(hero);
        Assert.assertEquals(hero, this.heroRepository.getHero("Zafircho"));
    }


    @Test(expected = UnsupportedOperationException.class)
    public void tryToRemoveObjectFromUnmodifiableCOllection() {
        Hero hero = new Hero("Stapi", 10);
        this.heroRepository.create(hero);
        this.heroRepository.getHeroes().remove(hero);
    }
}
