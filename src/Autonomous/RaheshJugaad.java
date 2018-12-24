package Autonomous;

import org.usfirst.frc.team6024.robot.commands.GoForwardCommand;
import org.usfirst.frc.team6024.robot.commands.GoToHeadingCommand;
import org.usfirst.frc.team6024.robot.commands.MoveArmCommand;
import org.usfirst.frc.team6024.robot.commands.MoveDriveCommand;
import org.usfirst.frc.team6024.robot.commands.MoveLiftCommand;
import org.usfirst.frc.team6024.robot.commands.MoveWheelCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class RaheshJugaad extends CommandGroup{
	
	public RaheshJugaad() {
		addSequential(new InitialBlockDropCommand());
		addSequential(new GoForwardCommand(10, 0.3, 0.3));
		addSequential(new MoveArmCommand(1000, 0.4)); 
		addParallel(new Intake());
		addParallel(new MoveArmCommand(10000, 0.4));
		addSequential(new GoForwardCommand(180, 0.3, 0.5));
	}
}
