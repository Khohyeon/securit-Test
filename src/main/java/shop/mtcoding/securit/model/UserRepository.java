package shop.mtcoding.securit.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.securit.dto.UserReq.LoginReqDto;

@Mapper
public interface UserRepository {

    public String findByPassword(@Param("password") String password);

    public int insert(User user);

    public int updateById(User user);

    public int deleteById(int id);

    public List<User> findAll();

    public User findById(int id);

    public User findByUsernameAndPassword(LoginReqDto loginReqDto);
}