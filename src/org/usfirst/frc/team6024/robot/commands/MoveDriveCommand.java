package org.usfirst.frc.team6024.robot.commands;

import org.usfirst.frc.team6024.robot.Robot;
import org.usfirst.frc.team6024.robot.Sensors;

import edu.wpi.first.wpilibj.command.Command;

public class MoveDriveCommand extends Command {
	private long finalTime, millis;
	private double speed, initialAngle;
	public MoveDriveCommand(long millis, double speed) {
		this.millis = millis;
		this.speed = speed;
	}
	
	protected void initialize() {
		finalTime = millis + System.currentTimeMillis();
		initialAngle = Sensors.navx.getFusedHeading();
	}
	private double curError = 0;
	public void execute() {
		Robot.armSubsystem.setArmSpeed(speed);
		//curError = Robot.driveSubsystem.movePID(speed,initialAngle, curError);
		Robot.driveSubsystem.move(-speed, -speed);
	}
	
	protected boolean isFinished() {
		return System.currentTimeMillis() > finalTime;
	}																						
	
	protected void end() {
		Robot.driveSubsystem.move(0, 0);
	}
	
	protected void interrupted() {
		end();
	}	
}
