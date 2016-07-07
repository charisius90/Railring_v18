package mate.model;

import dbcp.Command;

//by �ս���, ������
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
			System.out.println("���� ����Ʈ������Ʈ���ø�Ŀ�ǵ�� ���Ŵ�");
			return new MateUpdateCompleteCommand();
		}
		return null;
	}
}
