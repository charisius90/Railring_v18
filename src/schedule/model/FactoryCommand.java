package schedule.model;

import dbcp.Command;
import detail.model.DetailCommand;
import detail.model.DetailLoadCommand;
import detail.model.DetailPostCommand;
import matereply.model.MateReplyPostCommand;
import timeTable.model.TimeTableCommand;

//by °­º´Çö
public class FactoryCommand {
	private FactoryCommand() {}
	private static FactoryCommand instance = new FactoryCommand();
	public static FactoryCommand newInstance() {
		return instance;
	}
	
	public Command createInstance(String cmd){
		if(cmd.equals("POST")){
			return new PostCommand();
		}else if(cmd.equals("LIST")){
			return new ListCommand();
		}else if(cmd.equals("LOAD")){
			return new LoadCommand();
		}else if(cmd.equals("NEW")){
			return new NewCommand();
		}else if(cmd.equals("DETAIL")){
			return new DetailCommand();
		}else if(cmd.equals("DETAILPOST")){
			return new DetailPostCommand();
		}else if(cmd.equals("DETAILLOAD")){
			return new DetailLoadCommand();
		}else if(cmd.equals("TIMETABLE")){
			return new TimeTableCommand();
		}else if(cmd.equals("DELETE")){
			return new DeleteCommand();
		}
		
		return null;
	}
}
