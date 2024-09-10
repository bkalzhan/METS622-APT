package bu.cs622;

public class Character {
 private String name;
 private Location position;

 public Character(String name, Location position) {
     this.name = name;
     this.position = position;
 }

 // Getter and Setter methods
 public String getName() {
     return name;
 }

 public void setName(String name) {
     this.name = name;
 }

 public Location getPosition() {
     return position;
 }

 public void setPosition(Location position) {
     this.position = position;
 }

 // Method to move character
 public void move(Location newPosition) {
     this.position = newPosition;
 }
}

