package member.model;

import dbcp.Command;

public class FactoryCommand {
	private FactoryCommand(){}
	private static FactoryCommand instance = new FactoryCommand();
	public static FactoryCommand getInstance(){
		return instance;
	}
	
	public Command createInstance(String cmd){
		if(cmd.equals("LOGIN")){
			return new LoginCommand();
		}
		else if(cmd.equals("LOGOUT")){
			return new LogoutCommand();
		}
		else if(cmd.equals("CONFIRM")){
			return new RegisterCommand();
		}
		else if(cmd.equals("MYPAGE")){
			return new MyPageCommand();
		}
		else if(cmd.equals("changeInfo")){
			return new MyInfoCommand();
		}
		else if(cmd.equals("changePw")){
			return new MyPasswordCommand();
		}
		return null;
	}
}
