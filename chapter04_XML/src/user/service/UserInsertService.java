package user.service;

import java.util.Scanner;

import lombok.Setter;
import user.bean.UserDTO;
import user.dao.UserDAO;

public class UserInsertService implements UserService {
	@Setter
	private UserDTO userDTO;
	@Setter
	private UserDAO userDAO;
	
	//UserDAO userDAO = new UserDAOImpl(); (x)	
	
	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println();
		System.out.print("이름 입력 : ");
		String name = scan.next();
		System.out.print("이름 입력 : ");
		String id = scan.next();
		System.out.print("이름 입력 : ");
		String pwd = scan.next();
		System.out.println();
		
		userDTO.setName(name);
		userDTO.setId(id);
		userDTO.setPwd(pwd);
		
		System.out.println(userDTO);
		
		// DB
		userDAO.write(userDTO);
		
		System.out.println(name + "님의 데이터를 저장하였습니다.");
		}
}
