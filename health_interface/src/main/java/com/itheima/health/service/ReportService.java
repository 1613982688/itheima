package com.itheima.health.service;

import java.util.Map;

public interface ReportService {


    Map<String,Object> getBusinessReportData();

    Map<String, Object> getMemberSexReport();

    Map<String, Object> getMemberBirthdayReport();
}
