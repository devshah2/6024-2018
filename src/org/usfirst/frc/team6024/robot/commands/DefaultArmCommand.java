package org.usfirst.frc.team6024.robot.commands;

import org.usfirst.frc.team6024.robot.OI;
import org.usfirst.frc.team6024.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DefaultArmCommand extends Command{

	public DefaultArmCommand() { 
		requires(Robot.armSubsystem);
	}
	
	protected void execute() {
		Robot.armSubsystem.teleop();
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		Robot.armSubsystem.setWheelSpeed(0);
		Robot.armSubsystem.setArmSpeed(0);
	}
	
	protected void interuppted() {
		end();
	}
}
