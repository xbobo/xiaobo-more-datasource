package projecttset;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserVO {
    @JsonProperty(value = "user_id")
    private Long userId;
    @JsonProperty(value = "user_mobile")
    private String userMobile;
    @JsonProperty(value = "user_nickname")
    private String userNickname;
    @JsonProperty(value = "user_avatar")
    private String userAvatar;
    @JsonProperty(value = "user_grade")
    private String userGrade;

//    public static UserVO get(StudentDO studentDO){
//        if(studentDO==null){
//            throw new UserException("此用户不存在");
//        }
//        UserVO userVO=new UserVO();
//        userVO.setUserId(studentDO.getStudentId());
//        userVO.setUserMobile(studentDO.getStuMobile());
//        if (studentDO.getStuNickname()==null){
//            userVO.setUserNickname("");
//        }else {
//            userVO.setUserNickname(studentDO.getStuNickname());
//        }
//        if (studentDO.getStuAvatar()==null){
//            userVO.setUserAvatar("");
//        }else {
//            userVO.setUserAvatar(studentDO.getStuAvatar());
//        }
//        if (studentDO.getStuGrade()==null){
//            userVO.setUserGrade("");
//        }else {
//            userVO.setUserGrade(studentDO.getStuGrade());
//        }
//        return userVO;
//    }

}
