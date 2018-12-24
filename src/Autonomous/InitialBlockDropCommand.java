package Autonomous;

import org.usfirst.frc.team6024.robot.commands.MoveArmCommand;
import org.usfirst.frc.team6024.robot.commands.WaitForTimeCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class InitialBlockDropCommand extends CommandGroup {
	public InitialBlockDropCommand(){
		addSequential(new MoveArmCommand(500, -0.4));
		//addSequential(new WaitForTimeCommand(300));
	}
}
