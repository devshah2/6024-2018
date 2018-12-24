package org.usfirst.frc.team6024.robot.commands;

import org.usfirst.frc.team6024.robot.OI;
import org.usfirst.frc.team6024.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DefaultWinchCommand extends Command {

	public DefaultWinchCommand() { 
		requires(Robot.winchSubsystem);
	}

	protected void execute() {
		Robot.winchSubsystem.teleop();
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.winchSubsystem.move(0);
	}

	protected void interuppted() {
		end();
	}
}
