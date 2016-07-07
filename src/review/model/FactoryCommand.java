package review.model;

import dbcp.Command;

//by 손승한, 강병현
public class FactoryCommand {
	private FactoryCommand() {}
	private static FactoryCommand instance = new FactoryCommand();
	public static FactoryCommand newInstance() {
		return instance;
	}
	
	public Command createInstance(String cmd){
		if(cmd.equals("REVIEW")){
			return new ReviewCommand();
		}
		else if(cmd.equals("POST")){
			return new ReviewPostCommand();
		}
		else if(cmd.equals("READ")){
			return new ReviewReadCommand();
		}
		else if(cmd.equals("UPDATE")){
			return new ReviewUpdateCommand();
		}
		else if(cmd.equals("DELETE")){
			return new ReviewDeleteCommand();
		}
		else if(cmd.equals("COMPLETE")){
			return new ReviewPostCompleteCommand();
		}
		else if(cmd.equals("UPCOMPLETE")){
			return new ReviewUpdateCompleteCommand();
		}
		return null;
	}
}
