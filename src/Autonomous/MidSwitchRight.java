package Autonomous;

import org.usfirst.frc.team6024.robot.commands.GoForwardCommand;
import org.usfirst.frc.team6024.robot.commands.GoToHeadingCommand;
import org.usfirst.frc.team6024.robot.commands.MoveArmCommand;
import org.usfirst.frc.team6024.robot.commands.MoveDriveCommand;
import org.usfirst.frc.team6024.robot.commands.MoveLiftCommand;
import org.usfirst.frc.team6024.robot.commands.MoveLiftTimeCommand;
import org.usfirst.frc.team6024.robot.commands.MoveWheelCommand;

import Autonomous.MidSwitchLeft.MoveAndLift;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MidSwitchRight extends CommandGroup{
	
	class MoveAndLift extends CommandGroup{
		public MoveAndLift() {
			addParallel(new MoveLiftCommand(50));
			addParallel(new Intake());
			addSequential(new GoToHeadingCommand(40)); // turn towards one of the switches
			addSequential(new GoForwardCommand(85, 0.2, 0.4)); // go forward towards the switch
			addSequential(new GoToHeadingCommand(0)); // align
			addSequential(new MoveDriveCommand(1000, 0.4));
		}
	}
	
	public MidSwitchRight() {
		addSequential(new InitialBlockDropCommand());
		addSequential(new GoForwardCommand(20, 0.3, 0.3));
		addSequential(new MoveArmCommand(1000, 0.6)); 
		addParallel(new MoveArmCommand(10000, 0.6));
		
		addSequential(new MoveAndLift());
		addSequential(new Shoot());
//		addParallel(new MoveLiftCommand(10));
//		addSequential(new GoToHeadingCommand(90)); // turn towards the right
//		addSequential(new GoForwardCommand(60, 0.6, 0.6)); // Go Forward
//		addSequential(new GoToHeadingCommand(0)); //
//		addSequential(new GoForwardCommand(50, 0.6, 0.6)); // Go Towards the middle	}
	}
}
