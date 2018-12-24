package org.usfirst.frc.team6024.robot.commands;

import org.usfirst.frc.team6024.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveWheelCommand extends Command {
	private long finalTime, millis;
	private double speed;
	public MoveWheelCommand(long millis, double speed) {
		this.millis = millis;
		this.speed = speed;
	}
	
	protected void initialize() {
		finalTime = System.currentTimeMillis() + millis;
	}
	
	public void execute() {
		Robot.armSubsystem.setWheelSpeed(speed);
	}
	
	protected boolean isFinished() {
		return System.currentTimeMillis() > finalTime;
	}
	
	protected void end() {
		Robot.armSubsystem.setWheelSpeed(0);
	}
	
	protected void interrupted() {
		end();
	}
	
}
