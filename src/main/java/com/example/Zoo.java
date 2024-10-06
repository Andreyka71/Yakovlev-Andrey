package com.example;

// basic interfaces
interface Herbivores {
    public String food = "траву";
    void eat();
}
interface Predators {
    public String food = "любое мясо";
    void eat();
}
interface Land {
    public String movement = "ходит";
    void movement();
}
interface Waterfowl {
    public String movement = "плавает";
    void movement();
}
interface Flying {
    public String movement = "летает";
    void movement();
}
// animals
class Horse implements Herbivores, Land {
    public void eat() {
        System.out.println("Лошадь ест " + food);
    }
    public void movement() {
        System.out.println("Лошадь " + movement);
    }
}
class Tiger implements Land, Predators {
    public String food = "говядину";
    public void eat() {
        System.out.println("Тигр ест " + food);
    }
    public void movement() {
        System.out.println("Тигр " + movement);
    }
}
class Dolphin implements Waterfowl, Predators {
    public String food = "рыбу";
    public void eat() {
        System.out.println("Дельфин ест " + food);
    }
    public void movement() {
        System.out.println("Дельфин " + movement);
    }
}
class Eagles implements Flying, Predators {
    public void eat() {
        System.out.println("Орёл ест " + food);
    }
    public void movement() {
        System.out.println("Орёл " + movement);
    }
}
class Camel implements Herbivores, Land {
    public void eat() {
        System.out.println("Верблюд ест " + food);
    }
    public void movement() {
        System.out.println("Верблюд " + movement);
    }
}