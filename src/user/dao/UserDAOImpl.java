package user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import user.bean.UserDTO;

/*public class UserDAOImpl implements UserDAO {
	@Setter
	private JdbcTemplate jdbcTemplate;

	@Override
	public void write(UserDTO userDTO) {
		String sql = "insert into usertable values(?,?,?)";
		jdbcTemplate.update(sql , userDTO.getName(),userDTO.getId(),userDTO.getPwd()); // 스프링에서 jdbc는 insert라는 함수가 없고 Update를 쓴다.
	}

	@Override
	public List<UserDTO> getUserList() {
		String sql = "select * from usertable"; //new BeanPropertyRowMapper() 
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<UserDTO>(UserDTO.class));//제네릭안쓰면 오류남. //오버로드 : 동일 메소드에 변수갯수를 다르게 쓰는거 // 오버라이드 : 부모메소드를 자식메소드에서 쓰는거.
	}*/

/*public class UserDAOImpl extends JdbcDaoSupport implements UserDAO { // extends 가 먼저 와야된다.
	// @Setter 부분 없앴다. extends JdbcDaoSupport해줬기 때문이다.
	@Override
	public void write(UserDTO userDTO) {
		String sql = "insert into usertable values(?,?,?)";
		getJdbcTemplate().update(sql, userDTO.getName(), userDTO.getId(), userDTO.getPwd()); // 스프링에서 jdbc는 insert라는 함수가 없고
																						// Update를 쓴다.
	}

	@Override
	public List<UserDTO> getUserList() {
		String sql = "select * from usertable"; // new BeanPropertyRowMapper()
		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<UserDTO>(UserDTO.class));// 제네릭안쓰면 오류남. //오버로드 : 동일
																							// 메소드에 변수갯수를 다르게 쓰는거 //
																							// 오버라이드 : 부모메소드를 자식메소드에서
																							// 쓰는거.
	}*/
public class UserDAOImpl extends NamedParameterJdbcDaoSupport implements UserDAO {  // 상속관계라 NamedParameter써도된다.
	// NamedParameterJdbcDaoSupport 는 (?,?,?) 같은놈 때문에 태어났다.
	@Override
	public void write(UserDTO userDTO) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", userDTO.getName());
		map.put("id", userDTO.getId());
		map.put("pwd", userDTO.getPwd());
		
		String sql = "insert into usertable values(:name,:id,:pwd)";
		getNamedParameterJdbcTemplate().update(sql, map);
	}

	@Override
	public List<UserDTO> getUserList() {
		String sql = "select * from usertable"; // new BeanPropertyRowMapper()
		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<UserDTO>(UserDTO.class));
	}

	@Override
	public UserDTO getUser(String id) {
		String sql = "select * from usertable where id=?";
		try {// JdbcTemplate의 query 메서드는 데이터베이스에서 데이터를 조회하고 조회 결과를 자바 객체로 변환
			return getJdbcTemplate().queryForObject(
													sql, 
													new BeanPropertyRowMapper<UserDTO>(UserDTO.class),
												    id); //결과가 1행을 나오게 하는것.,
		} catch (EmptyResultDataAccessException e) {
			return null; //에러메시지 찍지말고 에러 뜨면 널로보내버려라
		}
	}

	@Override
	public void userUpdate(Map<String, String> map) {
		// TODO Auto-generated method stub
		String sql = "update usertable set name = :name ,pwd =(:pwd) where id = (:id)";
		getNamedParameterJdbcTemplate().update(sql, map); //update 인서트 업데이트 딜리트 전부 업데이트다
	}

	@Override
	public void delete(String id) { //?대신에 :id써도되
		String sql = "delete usertable where id = :id";
		getJdbcTemplate().update(sql,id);
	}
	
}
