package actors;

import worlds.EndWorld;
import engine.Settings;
import items.AutoLaser;
import items.SemiLaser;

import mayflower.Keyboard;
import mayflower.Mayflower;

public class Player extends ShudyActor
{	
	public Player()
	{
		setImage("assets/img/actors/player.gif");
		speed = 10;
		health = 100;
		weapon = new SemiLaser( this );
	}
	
	@Override
	public void act()
	{
		super.act();
		
		// have the robot look towards the mouse
		rotate();
		
		// process movement (WASD)
		move();
		
		// speed control (ARROW_UP/ARROW_DOWN)
		speed();
		
		if ( weapon instanceof AutoLaser && Mayflower.getMouseInfo().getButton() == 0 )
			weapon.fire();
		else if ( Mayflower.mousePressed( null ) )
			weapon.fire();
		
		if ( getX() > Settings.WIDTH )
			setLocation( 0, getY() );
		else if ( getX() < 0 )
			setLocation( Settings.WIDTH, getY() );
		
		if ( getY() > Settings.HEIGHT )
			setLocation( getX(), 0 );
		else if ( getY() < 0 )
			setLocation( getX(), Settings.HEIGHT );
		
	}
	
	protected void move()
	{
		if ( Mayflower.isKeyDown(Keyboard.KEY_W) )
			setLocation(getX(), getY() - speed);
		
		if ( Mayflower.isKeyDown(Keyboard.KEY_A) )
			setLocation(getX() - speed, getY());
		
		if ( Mayflower.isKeyDown(Keyboard.KEY_S) )
			setLocation(getX(), getY() + speed);
		
		if ( Mayflower.isKeyDown(Keyboard.KEY_D) )
			setLocation(getX() + speed, getY());
	}
	
	protected void rotate()
	{
		// thanks to Vishnu for helping me figure this out!
		setRotation( (int) ( Math.toDegrees( Math.atan2(
				Mayflower.getMouseInfo().getY() - getCenterY(),
				Mayflower.getMouseInfo().getX() - getCenterX() ) ) ) );
	}
	
	protected void speed()
	{
		if ( Mayflower.isKeyPressed(Keyboard.KEY_UP) && speed < 20 )
			speed++;
		
		if ( Mayflower.isKeyPressed(Keyboard.KEY_DOWN) && speed > 0)
			speed--;
	}

	public int getHealth()
	{ return health; }

	public void setHealth(int health)
	{ this.health = health; }

	public void heal(int h)
	{ health += h; }

	public int getAmmo()
	{ return weapon.getAmmo(); }

	public void setAmmo(int a)
	{ weapon.setAmmo(a); }
	
	@Override
	public void die()
	{
		super.die();
		Mayflower.setWorld( new EndWorld() );
	}
}
