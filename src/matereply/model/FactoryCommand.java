package matereply.model;

import dbcp.Command;

public class FactoryCommand {
	private FactoryCommand() {}
	private static FactoryCommand instance = new FactoryCommand();
	public static FactoryCommand newInstance() {
		return instance;
	}
	
	public Command createInstance(String cmd){
		if(cmd.equals("MATE")){
			return new MateReplyCommand();
		}
		else if(cmd.equals("POST")){
			return new MateReplyPostCommand();
		}
		else if(cmd.equals("READ")){
			return new MateReplyReadCommand();
		}
		else if(cmd.equals("UPDATE")){
			return new MateReplyUpdateCommand();
		}
		else if(cmd.equals("DELETE")){
			return new MateReplyDeleteCommand();
		}
		else if(cmd.equals("COMPLETE")){
			return new MateReplyPostCompleteCommand();
		}
		return null;
	}
}
