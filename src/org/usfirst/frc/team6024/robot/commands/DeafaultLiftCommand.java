package org.usfirst.frc.team6024.robot.commands;

import org.usfirst.frc.team6024.robot.OI;
import org.usfirst.frc.team6024.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DeafaultLiftCommand extends Command{


	public DeafaultLiftCommand() { 
		requires(Robot.liftSubsystem);
	}
	
	protected void execute() {
		Robot.liftSubsystem.teleopMove();
	}
	
	protected boolean isFinished() {
		return false;
	}
	
	protected void end() {
		Robot.liftSubsystem.move(0);
	}
	
	protected void interuppted() {
		end();
	}
}
