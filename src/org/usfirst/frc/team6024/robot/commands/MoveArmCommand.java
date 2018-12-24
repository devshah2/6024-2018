package org.usfirst.frc.team6024.robot.commands;

import org.usfirst.frc.team6024.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArmCommand extends Command {
	private long finalTime, millis;
	private double speed;
	public MoveArmCommand(long millis, double speed) {
		this.millis = millis;
		this.speed = speed;
	}
	
	protected void initialize() {
		finalTime = millis + System.currentTimeMillis();
	}
	
	public void execute() {
		Robot.armSubsystem.setArmSpeed(speed);
	}
	
	protected boolean isFinished() {
		return System.currentTimeMillis() > finalTime;
	}
	
	protected void end() {
		Robot.armSubsystem.setArmSpeed(0);
	}
	
	protected void interrupted() {
		end();
	}
	
}
