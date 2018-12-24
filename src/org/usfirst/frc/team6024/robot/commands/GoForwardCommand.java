package org.usfirst.frc.team6024.robot.commands;

import org.usfirst.frc.team6024.robot.Robot;
import org.usfirst.frc.team6024.robot.Sensors;

import edu.wpi.first.wpilibj.command.Command;

public class GoForwardCommand extends Command{
	double distance, maxSpeed, minSpeed;
	double initialLeftEncoder;
	double initialRightEncoder;
	long timeCount = 0;
	boolean diff = false;
	final static double InchesPerPulse = Math.PI/60;
	
	public GoForwardCommand(double distance, double minSpeed, double maxSpeed){
		this.distance = distance/InchesPerPulse;
		this.maxSpeed = maxSpeed;
		this.minSpeed = minSpeed;
		requires(Robot.driveSubsystem);
	}
	
	public GoForwardCommand(double distance, double minSpeed, double maxSpeed, double initialAngle){
		this.distance = distance/InchesPerPulse;
		this.maxSpeed = maxSpeed;
		this.minSpeed = minSpeed;
		requires(Robot.driveSubsystem);
		this.initialAngle = initialAngle;
		diff = true;
	}

	double initialAngle;
	protected void initialize() {
		Robot.driveSubsystem.move(0, 0);
		initialLeftEncoder = Sensors.leftE.get();
		initialRightEncoder = Sensors.rightE.get();
		if(diff == false) initialAngle = Sensors.navx.getFusedHeading();
	}
	
	private double speedAtDistance(double x) {
		return (Math.pow(((2*x)/distance)-1,4)*(minSpeed-maxSpeed))+maxSpeed;
	}
	
	private double speedAtDistance2(double x) {
		double perc = x/distance;
		if(perc < 0.33333) {
			return minSpeed*(1-perc*3) + maxSpeed*perc*3;
		}else if(perc < 0.66) {
			return maxSpeed;
		}else {
			return 0.1*(1-(1-perc)*3) + maxSpeed*(1 -perc)*3;
		}
	}
	double curError = 0;
	protected void execute() {
		double distanceTravelled;
		if(Math.abs(Sensors.rightE.get() - initialRightEncoder) > Math.abs(Sensors.leftE.get() - initialLeftEncoder)) { 
			distanceTravelled = Math.abs(Sensors.rightE.get() - initialRightEncoder); 
		}else distanceTravelled = Math.abs(Sensors.leftE.get() - initialLeftEncoder);
				
		double curSpeed = speedAtDistance2(distanceTravelled)*Math.signum(10*distance);
		curError = Robot.driveSubsystem.movePID(curSpeed,initialAngle, curError);
//		double leftSpeed = curSpeed;
//		double rightSpeed = curSpeed;
//		if(timeCount > 5) {
//			double ratio = ((double)Sensors.rightE.getRate())/Sensors.leftE.getRate(); 
//			leftSpeed *= ratio;
//			rightSpeed /= ratio;
//		}
//		Robot.driveSubsystem.move(leftSpeed, rightSpeed);
//		timeCount++;
		
	}

	protected boolean isFinished() {
		
		return (Math.abs((Sensors.rightE.get() - initialRightEncoder))) > Math.abs(distance);
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
