package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemDao
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/10/13 9:53
 * @Version V1.0
 */
public interface MemberDao {

    Member findMemberByTelephone(String telephone);

    void add(Member member);

    Integer findMemberCountByRegTime(String regTime);

    Integer findTodayNewMember(String date);

    Integer findTotalMember();

    Integer findRegTimeAfterDateNewMember(String date);
    //查询男女比例
    @Select("SELECT IF(sex=1,'男','女' ) name,COUNT(*) value FROM t_member GROUP BY sex")
    List<Map<String, Object>> getMemberSexReport();
    @Select("SELECT COUNT(*) value FROM t_member WHERE birthday <= #{number1} AND birthday > #{number2}")
        //查询某年龄段
    Integer getMemberBirthdayReport(@Param(value = "number1") String number1, @Param(value = "number2") String number2);

}
