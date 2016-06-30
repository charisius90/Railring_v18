package reviewreply.model;

import dbcp.Command;

public class FactoryCommand {
	private FactoryCommand() {}
	private static FactoryCommand instance = new FactoryCommand();
	public static FactoryCommand newInstance() {
		return instance;
	}
	
	public Command createInstance(String cmd){
		if(cmd.equals("REVIEW")){
			return new ReviewReplyCommand();
		}
		else if(cmd.equals("POST")){
			return new ReviewReplyPostCommand();
		}
		else if(cmd.equals("READ")){
			return new ReviewReplyReadCommand();
		}
		else if(cmd.equals("UPDATE")){
			return new ReviewReplyUpdateCommand();
		}
		else if(cmd.equals("DELETE")){
			return new ReviewReplyDeleteCommand();
		}
		else if(cmd.equals("COMPLETE")){
			return new ReviewReplyPostCompleteCommand();
		}
		return null;
	}
}
