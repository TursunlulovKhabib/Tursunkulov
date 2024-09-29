package org.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    Zoo zoo = new Zoo();
    zoo.check();
    Camel camel = new Camel();
    camel.walk();
    Dolphin dolphin = new Dolphin();
    dolphin.swim();
    Tiger tiger = new Tiger();
    tiger.walk();
    Horse horse = new Horse();
    horse.walk();
    Eagle eagle = new Eagle();
    eagle.fly();
  }
}

class Zoo {
  ArrayList<Animal> animals;

  public Zoo() {
    this.animals = new ArrayList<Animal>();
    Animal[] animals = {
      new Tiger(), new Dolphin(), new Camel(), new Eagle(), new Horse(),
    };
    this.animals.addAll(Arrays.asList(animals));
  }

  public void check() {
    for (Animal animal : this.animals) {
      animal.hello();
      animal.eat();
      System.out.println();
    }
  }
}

abstract class Animal {
  public static void animal() {}

  public void eat() {}
  ;

  public void hello() {}
  ;
}

class Camel extends Herbivores implements Land {

  public Camel() {}

  @Override
  public void walk() {
    System.out.println("Верблюд ходит");
  }

  @Override
  public void hello() {
    System.out.println("Вы наблюдаете верблюда ");
  }
}

class Dolphin extends Predator implements WaterFlow {

  public Dolphin() {
    super("Рыба");
  }

  @Override
  public void swim() {
    System.out.println("Дельфин плавает");
  }

  @Override
  public void hello() {
    System.out.println("Вы наблюдаете дельфина ");
  }
}

class Eagle extends Predator implements Flying {
  public Eagle() {
    super("Мясом");
  }

  @Override
  public void fly() {
    System.out.println("Орёл летает ");
  }

  @Override
  public void hello() {
    System.out.println("Вы наблюдаете орла ");
  }
}

class Herbivores extends Animal {
  public Herbivores() {}

  private String food = "Травой";

  @Override
  public void eat() {
    System.out.println("Этот зверёк питается " + food);
  }
}

class Horse extends Herbivores implements Land {

  public Horse() {}

  @Override
  public void walk() {
    System.out.println("Лошадь ходит");
  }

  @Override
  public void hello() {
    System.out.println("Вы наблюдаете лошадь ");
  }
}

class Predator extends Animal {
  private String food = null;

  public Predator(String food) {
    this.food = food;
  }

  @Override
  public void eat() {
    System.out.println("Этот зверёк питается " + food);
  }
}

class Tiger extends Predator implements Land {

  public Tiger() {
    super("Мясом");
  }

  @Override
  public void walk() {
    System.out.println("Тигр ходит");
  }

  @Override
  public void hello() {
    System.out.println("Вы наблюдаете Тигра ");
  }
}

interface WaterFlow {
  public void swim();
}

interface Land {
  public void walk();
}

interface Flying {
  public void fly();
}
