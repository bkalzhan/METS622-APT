package bu.cs622;

public class Throw {
 private int angle;
 private int velocity;

 public Throw(int angle, int velocity) {
     this.angle = angle;
     this.velocity = velocity;
 }

 // Overloaded constructors to handle different throwing actions
 public Throw(int angle) {
     this.angle = angle;
     this.velocity = 10; // Default velocity
 }

 // Getter and Setter methods
 public int getAngle() {
     return angle;
 }

 public void setAngle(int angle) {
     this.angle = angle;
 }

 public int getVelocity() {
     return velocity;
 }

 public void setVelocity(int velocity) {
     this.velocity = velocity;
 }

 // Method to calculate the distance thrown
 public double calculateDistance() {
     // Simple formula for distance (this can be more complex)
     return (Math.pow(velocity, 2) * Math.sin(Math.toRadians(2 * angle))) / 9.8;
 }
}

