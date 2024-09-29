package com.example;

public class Main {
    public static void main(String[] args) {
      Tiger d = new Tiger();
      Horse c = new Horse();
      Camel e = new Camel();
      Dolphin t = new Dolphin();
      d.eat();
      c.eat();
      c.movement();
      e.eat();
      e.movement();
      t.eat();
      t.movement();
    }
}