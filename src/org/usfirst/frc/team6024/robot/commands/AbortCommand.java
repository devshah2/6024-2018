/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6024.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6024.robot.OI;
import org.usfirst.frc.team6024.robot.Robot;
import org.usfirst.frc.team6024.robot.subsystems.DriveSubsystem;

/**
 * An example command.  You can replace me with your own command.
 */
public class AbortCommand extends Command {
	public AbortCommand(){
		requires(Robot.driveSubsystem);
		requires(Robot.armSubsystem);
		requires(Robot.liftSubsystem);
		requires(Robot.winchSubsystem);
	}

	protected void initialize() {
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
