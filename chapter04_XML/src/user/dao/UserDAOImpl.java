package user.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.Setter;
import user.bean.UserDTO;

public class UserDAOImpl implements UserDAO {
	// DB와 연결하는 과정을 대신 해주는 탬플릿 메소드(spring framework의 jdbc에 있다)
	@Setter  //setter로 인젝션
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void write(UserDTO userDTO) {
		String sql = "insert into usertable values(?,?,?)"; // myBatis에서는 이게 xml로 빠진다.
		jdbcTemplate.update(sql, userDTO.getName(), userDTO.getId(), userDTO.getPwd()); // 스프링에서 jdbc는 insert라는 함수가 없고 Update를 쓴다.
	}
}
