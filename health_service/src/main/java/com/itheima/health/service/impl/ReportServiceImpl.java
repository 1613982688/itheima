package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.service.ReportService;
import com.itheima.health.utils.CalendarUtils;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/10/13 9:54
 * @Version V1.0
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() {
        Map<String,Object> map = new HashMap<>();
        try {
            //当前时间
            String today = DateUtils.parseDate2String(DateUtils.getToday());
            //本周的第1天
            String thisWeekFirstDay = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            //本周的最后1天
            String thisWeekLastDay = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
            //本月的第1天
            String thisMonthFirstDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            //本月的最后1天
            String thisMonthLastDay = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());

            // 今天新增会员数
            Integer todayNewMember = memberDao.findTodayNewMember(today);
            // 总会员数
            Integer totalMember = memberDao.findTotalMember();
            // 本周新增会员数
            Integer thisWeekNewMember = memberDao.findRegTimeAfterDateNewMember(thisWeekFirstDay);
            // 本月新增会员数
            Integer thisMonthNewMember = memberDao.findRegTimeAfterDateNewMember(thisMonthFirstDay);

            // 订单相关
            // 今天预约数
            Integer todayOrderNumber = orderDao.findTodayOrderNumber(today);
            // 今天到诊数
            Integer todayVisitsNumber = orderDao.findTodayVisitsNumber(today);
            // 本周预约数
            Map<String,String> params = new HashMap<>();
            params.put("begin",thisWeekFirstDay);
            params.put("end",thisWeekLastDay);
            Integer thisWeekOrderNumber = orderDao.findThisRegTimeBetweenOrderNumber(params);
            Map<String,String> params2 = new HashMap<>();
            params2.put("begin",thisWeekFirstDay);
            params2.put("end",thisWeekLastDay);
            Integer thisWeekVisitsNumber = orderDao.findThisRegTimeBetweenVisitsNumber(params2);
            // 本月预约数
            Map<String,String> params1 = new HashMap<>();
            params1.put("begin",thisMonthFirstDay);
            params1.put("end",thisMonthLastDay);
            Integer thisMonthOrderNumber = orderDao.findThisRegTimeBetweenOrderNumber(params1);
            Map<String,String> params3 = new HashMap<>();
            params3.put("begin",thisMonthFirstDay);
            params3.put("end",thisMonthLastDay);
            Integer thisMonthVisitsNumber = orderDao.findThisRegTimeBetweenVisitsNumber(params3);
            // 热门套餐
            List<Map<String,Object>> hotSetmeal = orderDao.findHotSetmeal();


            map.put("reportDate",today);
            map.put("todayNewMember",todayNewMember );
            map.put("totalMember",totalMember );
            map.put("thisWeekNewMember",thisWeekNewMember );
            map.put("thisMonthNewMember",thisMonthNewMember );
            map.put("todayOrderNumber",todayOrderNumber );
            map.put("todayVisitsNumber",todayVisitsNumber );
            map.put("thisWeekOrderNumber",thisWeekOrderNumber );
            map.put("thisWeekVisitsNumber",thisWeekVisitsNumber );
            map.put("thisMonthOrderNumber",thisMonthOrderNumber );
            map.put("thisMonthVisitsNumber",thisMonthVisitsNumber );
            map.put("hotSetmeal",hotSetmeal );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("运行时异常");
        }
        return map;
    }
    //获取会员性别统计
    @Override
    public Map<String, Object> getMemberSexReport() {
        Map<String,Object> sexMap = new HashMap<>();
        List<String> sex = new ArrayList<>();
        List<Map<String,Object>> sexCount = memberDao.getMemberSexReport();
        sex.add("男");
        sex.add("女");
        sexMap.put("sex",sex);
        sexMap.put("sexCount",sexCount);
        return sexMap;
    }
    //获取会员年龄段统计
    @Override
    public Map<String, Object> getMemberBirthdayReport() {
        //获取当前的日期
        String zero = CalendarUtils.getStringDate(0);
        //获取（距离今天）18岁之前的日期
        String eighteen = CalendarUtils.getStringDate(-18);
        //获取（距离今天）30岁之前的日期
        String thrity = CalendarUtils.getStringDate(-30);
        //获取（距离今天）45岁之前的日期
        String fortyFive = CalendarUtils.getStringDate(-45);
        //获取（距离今天）60岁之前的日期
        String sixty = CalendarUtils.getStringDate(-60);
        //获取（距离今天）300岁之前的日期
        String threeHundred = CalendarUtils.getStringDate(-300);
        //查询0-18
        Integer countLtEighteen = memberDao.getMemberBirthdayReport(zero,eighteen);
        //查询18-30岁
        Integer countEighteenAndThurty = memberDao.getMemberBirthdayReport(eighteen,thrity);
        //查询30-45岁
        Integer countThurtyAndFortyFive = memberDao.getMemberBirthdayReport(thrity,fortyFive);
        //查询45-60岁
        Integer countGtFortyFiveAndSixty = memberDao.getMemberBirthdayReport(fortyFive,sixty);
        //查询60-300岁
        Integer countcountGtSixty = memberDao.getMemberBirthdayReport(sixty,threeHundred);
        //返回controller的结果集map
        Map<String,Object> mapMap = new HashMap<>();
        //name的字段封装list
        List<String> ageGroup = new ArrayList<>();
        //封装name
        ageGroup.add("0-18岁");
        ageGroup.add("18-30岁");
        ageGroup.add("30-45岁");
        ageGroup.add("45-60岁");
        ageGroup.add("60岁以上");
        //返回的结果name字段
        mapMap.put("ageGroup",ageGroup);

        //将查询的Integer封装到map
        Map<String,Object> map = new HashMap<>();
        map.put("value",countLtEighteen);
        map.put("name","0-18岁");

        Map<String,Object> map1 = new HashMap<>();
        map1.put("value",countEighteenAndThurty);
        map1.put("name","18-30岁");
        Map<String,Object> map2 = new HashMap<>();
        map2.put("value",countThurtyAndFortyFive);
        map2.put("name","30-45岁");


        Map<String,Object> map3 = new HashMap<>();
        map3.put("value",countGtFortyFiveAndSixty);
        map3.put("name","45-60岁");


        Map<String,Object> map4 = new HashMap<>();
        map4.put("value",countcountGtSixty);
        map4.put("name","60岁以上");

        //list<Map<String,Object>>封装map集合
        List<Object> ageGroupCount = new ArrayList<>();

        ageGroupCount.add(map);
        ageGroupCount.add(map1);
        ageGroupCount.add(map2);
        ageGroupCount.add(map3);
        ageGroupCount.add(map4);
        //返回的list集合
        mapMap.put("ageGroupCount",ageGroupCount);

        return mapMap;
    }
}
