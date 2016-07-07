package mate.model;

import dbcp.Command;

//by 손승한, 강병현
public class FactoryCommand {
	private FactoryCommand() {}
	private static FactoryCommand instance = new FactoryCommand();
	public static FactoryCommand newInstance() {
		return instance;
	}
	
	public Command createInstance(String cmd){
		if(cmd.equals("MATE")){
			return new MateCommand();
		}
		else if(cmd.equals("POST")){
			return new MatePostCommand();
		}
		else if(cmd.equals("READ")){
			return new MateReadCommand();
		}
		else if(cmd.equals("UPDATE")){
			return new MateUpdateCommand();
		}
		else if(cmd.equals("DELETE")){
			return new MateDeleteCommand();
		}
		else if(cmd.equals("COMPLETE")){
			return new MatePostCompleteCommand();
		}
		else if(cmd.equals("UPCOMPLETE")){
			System.out.println("이제 메이트업데이트컴플릿커맨드로 갈거다");
			return new MateUpdateCompleteCommand();
		}
		return null;
	}
}
