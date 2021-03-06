package actors;

import java.util.List;

import mayflower.MayflowerImage;

public class MenuRobot extends Grunt
{
	private MenuRobot target;
	private boolean dead;
	
	public MenuRobot(MayflowerImage img)
	{
		super(100, 8, img);
		
		dead = false;
	}

	@Override
	public void act()
	{
		if ( health <= 0 )
			die();
		
		if ( world == null )
			world = getWorld();
		
		if ( target == null || target == this || target.isDead() )
		{
			List<MenuRobot> bots = world.getObjects( MenuRobot.class );
			if ( bots.size() > 0 )
				target = bots.get( (int) ( Math.random() * bots.size() ) );
			else
			{
				die();
				return;
			}
		}
		
		turnTowards( target );

		if ( !intersects( target ) )
		{
			move(speed);
			if ( weapon != null )
				weapon.fire();
		}
		else
			target.damage(5);
	}
	
	public boolean isDead()
	{ return dead; }
	
	@Override
	protected void die()
	{
		dead = true;
		world.removeObject(this);
	}
}
