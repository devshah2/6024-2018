package org.usfirst.frc.team6024.robot.commands;

import org.usfirst.frc.team6024.robot.Robot;
import org.usfirst.frc.team6024.robot.Sensors;
import org.usfirst.frc.team6024.robot.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class MoveLiftCommand extends Command {

	public long pulsesUpCount;
	public MoveLiftCommand(long inchesUp) {
		pulsesUpCount = (long) (inchesUp * LiftSubsystem.pulsesPerInchSlow);
	}
	
	public void execute() {
		//long dist = (Sensors.leftLE.get() + Sensors.rightLE.get())/2 - pulsesUpCount;
//		long dist = 0;
//		if(!Sensors.leftLE.getStopped() && !Sensors.rightLE.getStopped())
//			dist = (Sensors.leftLE.get() + Sensors.rightLE.get())/2 - pulsesUpCount;
//		else if(!Sensors.leftLE.getStopped())
//			dist = Sensors.leftLE.get();
//		else if(!Sensors.rightLE.getStopped())
//			dist = Sensors.rightLE.get();
//		else dist = 2; // not cool man
		long dist = Sensors.rightLE.get() - pulsesUpCount;
		double speed;
		if(dist > 0)
			speed = Math.max(0.3, Math.min(1, Math.pow(Math.abs(dist/200.00), 3)))*Math.signum(dist);
		else
			speed = -1;//Math.max(0.9, Math.min(1, Math.abs(dist/200.00)))*Math.signum(dist);
		Robot.liftSubsystem.move(speed);
		Robot.liftSubsystem.leftCount = Sensors.leftLE.get();
		Robot.liftSubsystem.rightCount = Sensors.rightLE.get();
	}
	
	protected void end() {
		Robot.liftSubsystem.leftCount = Sensors.leftLE.get();
		Robot.liftSubsystem.rightCount = Sensors.rightLE.get();
		Robot.liftSubsystem.brake();
	}
	
	protected void interrupted() {
		end();
	}
	
	protected boolean isFinished() {
		long dist = Sensors.rightLE.get();
		return Math.abs(dist - pulsesUpCount) < 60;
	}
}
