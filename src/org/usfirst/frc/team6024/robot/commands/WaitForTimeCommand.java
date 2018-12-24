package org.usfirst.frc.team6024.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForTimeCommand extends Command{

	private long initTime, time;
	
	public WaitForTimeCommand(long millis) {
		time = millis;
	}
	
	protected void initialize() {
		initTime = System.currentTimeMillis();
	}
	
	protected boolean isFinished() {
		return (System.currentTimeMillis() > initTime + time);
	}
	
}
