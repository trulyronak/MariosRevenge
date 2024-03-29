package Model;

import java.awt.Image;
import java.awt.image.BufferedImage;

import ImportManager.ImportManager;
import View.Level;

public class Player extends Entity {

	protected int playerHealth;

	private boolean movingRight, movingLeft, jumping, falling,
	isInJump, hasJumped, standing;//Booleans So GUI can see players current status
	private boolean attacking, hasAttacked;//for fireballs
	private boolean facingRight = true;
	private int moveImage;
	private int timesMoved;
	private Powerup ability = null;

	private final int jumpImage = 5;
	private final int fallingImage = 6;
	public Player(int x, int y, BufferedImage icon) {
		super(x, y, icon);
		this.hasHealth = true;
		playerHealth = 1;
		movingRight = false;
		movingLeft = false;
		jumping = false;
		falling = false;
		moveImage = 0;
		timesMoved = 0;
		isInJump = false;

	}

	public boolean isFacingRight(){
		return facingRight;
	}

	/**
	 * true if player is facing right, false if facing left
	 * @param b
	 */
	public void setDirection(boolean b){
		facingRight = b;
	}

	public boolean canAttack(){
		return (ability !=null);
	}

	public boolean hasAttacked(){
		return hasAttacked;
	}

	public void toggleHasAttacked(){
		hasAttacked = !hasAttacked;
	}

	public boolean isAttacking(){
		return attacking;
	}

	public void setAttacking(boolean b){
		attacking = b;
	}

	public int getTimesMoved(){
		return timesMoved;
	}
	//this is a change
	public void resetTime(){
		timesMoved = 0;
	}

	public BufferedImage getImg(){
		if(playerHealth <= 1){
			if(!facingRight){
				if(jumping)
					return ImportManager.leftMario[jumpImage-1];
				if(falling)
					return ImportManager.mario[fallingImage];//doesnt matter for falling

				return ImportManager.leftMario[moveImage];
			}
			else{// facingRight == true
				if(jumping)
					return ImportManager.mario[jumpImage-1];
				if(falling)
					return ImportManager.mario[fallingImage];//doesnt matter for falling
				return ImportManager.mario[moveImage];
			}
		}
		else{//if the player is fire mario
			if(!facingRight){
				if(jumping)
					return ImportManager.fireLeftMario[jumpImage-1];
				if(falling){
					BufferedImage wtf = ImportManager.fireMario[fallingImage-1];
					if(wtf == null){
						falling = true;
					}
					return wtf;//doesnt matter for falling
				}
				return ImportManager.fireLeftMario[moveImage];
			}
			else{// facingRight == true
				if(jumping)
					return ImportManager.fireMario[jumpImage-1];
				if(falling){
					BufferedImage wtf = ImportManager.fireMario[fallingImage-1];
					if(wtf == null){
						falling = true;
					}
					return wtf;//doesnt matter for falling
				}
				return ImportManager.fireMario[moveImage];
			}
		}
	}

	public void setHealth(int a)
	{
		playerHealth = a;
		if (playerHealth == 0){
			this.changeStatus();
		}
			
	}

	public int getHealth()
	{
		return playerHealth;
	}

	public boolean hasJumped()
	{
		if(hasJumped){
			return true;
		}
		return false;
	}

	public void toggleJumped()
	{
		hasJumped = !hasJumped;
	}



	@Override
	public void interact(Entity other) {
		if (other instanceof Powerup)
		{
			((Powerup)other).giveAbility(this);			
		}

		else if (other instanceof Destroyable)
		{

			if (other instanceof Enemy)
			{
				//if the player is jumping on the enemy
				if (getRow()+1 == other.getRow() && getY() < other.getY() && getVelY() <= 0)
				{
					((Enemy)other).changeStatus();
				}
				//if the player is next to the enemy
				else
				{
					setHealth(playerHealth - 1);
				}
			}

			else if (other instanceof Box )
			{
				if (getX() == other.getX() && getY() == other.getY() && getVelY() > 0)
				{
					if (other instanceof Powerbox)
					{
						//((Powerbox)other).getPowerup(this);			
					}

					((Box)other).changeStatus();
				}
			}

		}

		else if (other instanceof Standable)
		{
			if(movingRight || movingLeft){
				//do nothing
			}
			else if(getY() > other.getY()){
				falling = true;
				isInJump = true;
				standing = false;
				//hitPlatform = true;
				setVelY(0);
				setY(other.getY() - Level.height);

			}
			else if(getY() < other.getY()){

				falling = false;
				hasJumped = false;
				jumping = false;
				isInJump = false;
				setVelY(0);
				standing = true;
				setY(other.getY() + Level.height);
			}
			else{//aka the player is right next to the Standable Object

			}


		} 
		else if(other instanceof Exit){
			((Exit)other).toggleCompleted();
		}

	}

	public void changeAnimation(){
		if(moveImage == 4){
			moveImage = 0;
		}
		int m = moveImage;
		moveImage++;
	}

	public void changeImage(){
		moveImage = 0; //stub code
	}

	/**
	 * Resets The Players Picture to its default
	 */
	public void resetAnimation(){
		moveImage = 0;
	}
	public boolean isMovingRight(){

		return movingRight;
	}

	public boolean isMovingLeft(){
		return movingLeft;
	}

	public boolean isFalling(){
		return falling;
	}

	/**
	 * Returns whether the player is jumping
	 * @return
	 */
	public boolean isJumping(){
		return jumping;
	}

	/**
	 * Toggles the moving status of dir
	 * 
	 * @param dir
	 */
	public void changeMovingStatus(String dir){
		if(dir.equals("right")){
			movingRight = !movingRight;
		}

		else if(dir.equals("left")){
			movingLeft = !movingLeft;
		}

		else if(dir.equals("up")){

			jumping = true;
			isInJump = true;
			//toggleJumped();

		} 

		else if(dir.equals("falling")){
			falling = true;
		}


	}

	/**
	 * Returns the Y pixels the player has traveled
	 * @return
	 */
	/*public int getYTraveled()
	{
		return yTraveled;
	} */


	/**
	 * Visually moves the player right and updates timesMoved
	 */
	public void moveRight() {
		xLoc = xLoc += Level.movePixels;
		timesMoved++;
		facingRight = true;
	}


	/**
	 * Visually moves the player left and updates timesMoved
	 */

	public void moveLeft() {
		xLoc = xLoc -= Level.movePixels;
		timesMoved --;
		facingRight = false;
	}

	public void moveY(int velY)
	{
		if (isInJump || !isStanding()) //isInJump is boolean that is reset by landing on a platform
		{
			setVelY(velY); //sets the player velocity to the argument

			if (getVelY() >= 0) //velocity is greater than zero --> you are moving down
			{
				falling = true;
				jumping = false;
			}

			else if (getVelY() < 0){ //velocity is less than zero --> you are moving up
				jumping = true;
				falling = false;
			}

			yLoc += getVelY() + Level.gravity; //increments the player position


			if (jumping){

				if (getVelY() < 0) 
				{

					if (0 - getVelY() >= Level.gravity) // if the difference between player vel and zero is at least gravity
					{
						//velocity is decreased by gravity
						setVelY(getVelY() + Level.gravity);
					}

					//other wise if the difference is less than gravity just set to zero
					else{
						setVelY(0);
					}
				}
			}

			else if (falling)
			{
				//if the difference between the terminal vel and current vel is at least gravity
				if (Level.MAX_FALL_SPEED - getVelY() >= 0 && Level.MAX_FALL_SPEED - getVelY() >= Level.gravity ){
					//vel accelerates downwards by gravity amount
					setVelY(getVelY() + Level.gravity);
				}

				else if (getVelY() < Level.MAX_FALL_SPEED){
					//otherwise if the difference is less than gravity
					//player just accelerates to terminal v
					setVelY(Level.MAX_FALL_SPEED);
				}
			}
		}
	}


	public void givePowerup(Powerup pwr) {
		ability = pwr;

	}



	/**
	 * Sets TimesMoved to 0
	 */
	public void resetTimesMoved() {
		timesMoved = 0;
	}

	@Override
	/**
	 * Returns whether the player is dead or not. So if the player's health is below
	 * 0, the method will return false
	 */
	public boolean getStatus(){
		if(playerHealth <= 0){
			return false;
		}
		return true;
	}


	public void setStanding(boolean bool){
		standing = bool;
	}

	public boolean isStanding() {
		// TODO Auto-generated method stub
		return standing;
	}


	public void setFalling(boolean b) {
		falling  = b;

	}


	public boolean getDirection() {
		if(movingLeft){
			return false;
		}
		else{
			return true;
		}
	}


	public void setHasAttacked(boolean b) {
		hasAttacked = b;

	}
}
