package org.usfirst.frc.team6024.robot.commands;

import org.usfirst.frc.team6024.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WinchCommand extends Command{
	public WinchCommand() {
		requires(Robot.winchSubsystem);
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void execute() {
		Robot.winchSubsystem.move(0.4);
	}
	
	protected void end() {
		Robot.winchSubsystem.move(0);
	}
	
	protected void interrupted() {
		end();
	}
}
