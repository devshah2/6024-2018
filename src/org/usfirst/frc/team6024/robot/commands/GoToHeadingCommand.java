package org.usfirst.frc.team6024.robot.commands;

import org.usfirst.frc.team6024.robot.Robot;
import org.usfirst.frc.team6024.robot.Sensors;

import edu.wpi.first.wpilibj.command.Command;

public class GoToHeadingCommand extends Command{
	
	double heading;
	public GoToHeadingCommand(double heading){
		this.heading = heading;
		requires(Robot.driveSubsystem);
	}

	protected void initialize() {
		Robot.driveSubsystem.move(0, 0);
	}
	
	private double findHeading(double curAngle) {
		double ans = (curAngle - heading + 360) % 360;
		return ans > 180 ? ans - 360 : ans;
	}

	protected void execute() {
		double curAngle = findHeading(Sensors.navx.getFusedHeading());
		double myHeading = Math.pow(Math.abs(curAngle/250), 1.5)*Math.signum(curAngle);
		myHeading += Math.signum(myHeading)*0.2;
		//if(myHeading > 0)myHeading += Math.signum(myHeading)*0.23;
		//else myHeading -= 0.13;
		//double rate = (Math.abs(Sensors.rightE.getRate())/myHeading) + 1;
		double mult = 1;
		if(Math.abs(Sensors.rightE.getRate()) < 20 && Math.abs(curAngle) > 60) mult = 1.6;
		Robot.driveSubsystem.move(myHeading*mult, -myHeading*mult);
	}

	protected boolean isFinished() {
		double curAngle = Sensors.navx.getFusedHeading();
		double myHeading = findHeading(curAngle);
		if(Math.abs(myHeading) < 4)
			return true;
		else
			return false;
	}

	protected void end() {
		Robot.driveSubsystem.move(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}

}
