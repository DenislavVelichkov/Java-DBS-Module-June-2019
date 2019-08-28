package heroRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HeroRepositoryTest {
    private Item item;
    private Hero hero;
    private HeroRepository heroRepository;
    
    @Before
    public void setUpItem() {
        this.item = new Item(10, 10, 10);
    }
    
    @Before
    public void setUpHeroClass() {
        this.hero = new Hero("Zafircho", 1, item);
    }
    
    @Before
    public void setUpHeroRepo() {
        this.heroRepository = new HeroRepository();
    }
    
    
    @Test
    public void addHeroShouldAddHero() {
        this.heroRepository.add(hero);
        Assert.assertEquals(1, this.heroRepository.getCount());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addHeroShouldThrowException() {
        this.heroRepository.add(hero);
        this.heroRepository.add(hero);
    }
    
    @Test(expected = NullPointerException.class)
    public void removeHeroNullShouldThrowException() {
        this.heroRepository.remove(null);
    }
    
    @Test
    public void removeHeroShouldWorkCorrectly() {
        this.heroRepository.add(hero);
        this.heroRepository.remove(hero.getName());
        Assert.assertEquals(0, this.heroRepository.getCount());
    }
    
    @Test
    public void getHeroWithHighestStrength() {
        Item newItem = new Item(20, 10, 10);
        Hero additionalHero = new Hero("Pesho", 3, newItem);
        Item newItem2 = new Item(10, 10, 10);
        Hero additionalHero2 = new Hero("Zafir", 3, newItem2);
        Item newItem3 = new Item(5, 10, 10);
        Hero additionalHero3 = new Hero("ZafiroV", 3, newItem3);
        
        this.heroRepository.add(additionalHero);
        this.heroRepository.add(additionalHero2);
        this.heroRepository.add(additionalHero3);
        
        Assert.assertEquals(additionalHero, this.heroRepository.getHeroWithHighestStrength());
    }
    
    @Test(expected = NullPointerException.class)
    public void getHeroWithHighestStrengthShouldThrowException() {
        Item newItem = new Item(0, 5, 5);
        Hero additionalHero = new Hero("Pesho", 3, newItem);
        Hero additionalHero2 = new Hero("Zafir", 3, newItem);
        Hero additionalHero3 = new Hero("Zmeicho", 3, newItem);

        this.heroRepository.add(additionalHero);
        this.heroRepository.add(additionalHero2);
        this.heroRepository.add(additionalHero3);
        
        this.heroRepository.getHeroWithHighestStrength();
    }
    
    @Test
    public void getHeroWithHighestAgility() {
        Item newItem = new Item(10, 20, 10);
        Hero additionalHero = new Hero("Pesho", 3, newItem);
        Item newItem2 = new Item(10, 10, 10);
        Hero additionalHero2 = new Hero("Zafir", 3, newItem2);
        Item newItem3 = new Item(10, 5, 10);
        Hero additionalHero3 = new Hero("Zafiris", 3, newItem3);
        
        this.heroRepository.add(additionalHero2);
        this.heroRepository.add(additionalHero);
        this.heroRepository.add(additionalHero3);
        
        Assert.assertEquals(additionalHero, this.heroRepository.getHeroWithHighestAgility());
    }
    
    @Test(expected = NullPointerException.class)
    public void getHeroWithHighestAgilityShouldThrowException() {
        Item newItem = new Item(10, 0, 5);
        Hero additionalHero = new Hero("Pesho", 3, newItem);
        Hero additionalHero2 = new Hero("Zafir", 3, newItem);
        Hero additionalHero3 = new Hero("Aston", 3, newItem);

        this.heroRepository.add(additionalHero);
        this.heroRepository.add(additionalHero2);
        this.heroRepository.add(additionalHero3);
        
        this.heroRepository.getHeroWithHighestAgility();
    }
    
    @Test
    public void getHeroWithHighestIntelligence() {
        Item newItem = new Item(10, 10, 20);
        Hero additionalHero = new Hero("Pesho", 3, newItem);
        Item newItem2 = new Item(10, 10, 10);
        Hero additionalHero2 = new Hero("Zafir", 3, newItem2);
        Item newItem3 = new Item(10, 10, 5);
        Hero additionalHero3 = new Hero("Zafiris", 3, newItem3);
        
        this.heroRepository.add(additionalHero);
        this.heroRepository.add(additionalHero2);
        this.heroRepository.add(additionalHero3);
        
        Assert.assertEquals(additionalHero, this.heroRepository.getHeroWithHighestIntelligence());
    }
    
    @Test(expected = NullPointerException.class)
    public void getHeroWithHighestIntelligenceShouldThrowException() {
        Item newItem = new Item(10, 5, 0);
        Hero additionalHero = new Hero("Pesho", 3, newItem);
        Hero additionalHero2 = new Hero("Zafir", 3, newItem);
        Hero additionalHero3 = new Hero("Aston", 3, newItem);

        this.heroRepository.add(additionalHero);
        this.heroRepository.add(additionalHero2);
        this.heroRepository.add(additionalHero3);

        this.heroRepository.getHeroWithHighestIntelligence();
    }
    
    @Test
    public void getCount() {
        this.heroRepository.add(hero);
        Assert.assertEquals(1, this.heroRepository.getCount());
    }
    
    @Test
    public void testString() {
        String expectedOutput =
            String.format("Hero: Zafircho – 1" + System.lineSeparator() +
                              "  *  Strength: 10" + System.lineSeparator() +
                              "  *  Agility: 10" + System.lineSeparator() +
                              "  *  Intelligence: 10" + System.lineSeparator());
        
        String output =
            String.format("Hero: %s – %d%n" +
                              "  *  Strength: %d%n" +
                              "  *  Agility: %d%n" +
                              "  *  Intelligence: %d%n", this.hero.getName(), this.hero.getLevel(),
                this.item.getStrength(), this.item.getAgility(), this.item.getIntelligence());
        Assert.assertEquals(expectedOutput, output);
    }
}