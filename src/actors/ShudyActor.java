package actors;

import mayflower.Actor;

public abstract class ShudyActor extends Actor
{
	protected int health;
	protected int speed;
	
	@Override
	public void act()
	{
		if ( health <= 0)
			die();
	}
	
	private void die()
	{ getWorld().removeObject(this); }
	
	protected void damage(int d)
	{ health -= d; }
}
