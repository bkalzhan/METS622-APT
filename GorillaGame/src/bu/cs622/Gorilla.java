package bu.cs622;

public class Gorilla extends Character {
 private int health;

 public Gorilla(String name, Location position, int health) {
     super(name, position);
     this.health = health;
 }

 // Getter and Setter for health
 public int getHealth() {
     return health;
 }

 public void setHealth(int health) {
     this.health = health;
 }

 // Overridden method to move Gorilla with special behavior
 @Override
 public void move(Location newPosition) {
     System.out.println(getName() + " is jumping to a new location.");
     super.move(newPosition);
 }
}

