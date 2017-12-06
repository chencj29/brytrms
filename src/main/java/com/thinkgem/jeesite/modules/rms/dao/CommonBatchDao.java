package com.thinkgem.jeesite.modules.rms.dao;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.rms.entity.*;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/10/21.
 */
@Repository
public class CommonBatchDao {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public void batchInsertPairProgressInfos(List<FlightPairProgressInfo> flightPairProgressInfos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        flightPairProgressInfos.forEach(flightPairProgressInfo -> {
            flightPairProgressInfo.setId(UUID.randomUUID().toString());
            sqlSession.insert("com.thinkgem.jeesite.modules.rms.dao.FlightPairProgressInfoDao.insert", flightPairProgressInfo);
        });
        sqlSession.commit();
        sqlSession.close();
    }

    public void batchInsertGateOCI(List<GateOccupyingInfo> gateOccupyingInfos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        gateOccupyingInfos.forEach(gateOccupyingInfo ->
                sqlSession.insert("com.thinkgem.jeesite.modules.rms.dao.GateOccupyingInfoDao.insert", gateOccupyingInfo));
        sqlSession.commit();
        sqlSession.close();
    }

    public void batchInsertSecurityCheckinOCI(List<SecurityCheckinOccupyingInfo> securityCheckinOccupyingInfos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        securityCheckinOccupyingInfos.forEach(securityCheckinOccupyingInfo ->
                sqlSession.insert("com.thinkgem.jeesite.modules.rms.dao.SecurityCheckinOccupyingInfoDao.insert", securityCheckinOccupyingInfo));
        sqlSession.commit();
        sqlSession.close();
    }

    public void batchInsertDepartureHallOCI(List<DepartureHallOccupyingInfo> departureHallOccupyingInfos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        departureHallOccupyingInfos.forEach(departureHallOccupyingInfo ->
                sqlSession.insert("com.thinkgem.jeesite.modules.rms.dao.DepartureHallOccupyingInfoDao.insert", departureHallOccupyingInfo));
        sqlSession.commit();
        sqlSession.close();
    }

    public void batchInsertSlideCoastOCI(List<SlideCoastOccupyingInfo> slideCoastOccupyingInfos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        slideCoastOccupyingInfos.forEach(slideCoastOccupyingInfo ->
                sqlSession.insert("com.thinkgem.jeesite.modules.rms.dao.SlideCoastOccupyingInfoDao.insert", slideCoastOccupyingInfo));
        sqlSession.commit();
        sqlSession.close();
    }

    public void batchInsertBoardingGateOCI(List<BoardingGateOccupyingInfo> boardingGateOccupyingInfos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        boardingGateOccupyingInfos.forEach(boardingGateOccupyingInfo ->
                sqlSession.insert("com.thinkgem.jeesite.modules.rms.dao.BoardingGateOccupyingInfoDao.insert", boardingGateOccupyingInfo));
        sqlSession.commit();
        sqlSession.close();
    }

    public void batchInsertArrivalGateOCI(List<ArrivalGateOccupyingInfo> arrivalGateOccupyingInfos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        arrivalGateOccupyingInfos.forEach(arrivalGateOccupyingInfo ->
                sqlSession.insert("com.thinkgem.jeesite.modules.rms.dao.ArrivalGateOccupyingInfoDao.insert", arrivalGateOccupyingInfo));
        sqlSession.commit();
        sqlSession.close();
    }

    public void batchInsertCheckingCounterOCI(List<CheckingCounterOccupyingInfo> checkingCounterOccupyingInfos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        checkingCounterOccupyingInfos.forEach(checkingCounterOccupyingInfo ->
                sqlSession.insert("com.thinkgem.jeesite.modules.rms.dao.CheckingCounterOccupyingInfoDao.insert", checkingCounterOccupyingInfo));
        sqlSession.commit();
        sqlSession.close();
    }

    public void batchInsertCarouselOCI(List<CarouselOccupyingInfo> carouselOccupyingInfos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        carouselOccupyingInfos.forEach(carouselOccupyingInfo ->
                sqlSession.insert("com.thinkgem.jeesite.modules.rms.dao.CarouselOccupyingInfoDao.insert", carouselOccupyingInfo));
        sqlSession.commit();
        sqlSession.close();
    }

    @Autowired
    CarouselOccupyingInfoDao carouselOccupyingInfoDao;
    @Autowired
    ArrivalGateOccupyingInfoDao arrivalGateOccupyingInfoDao;
    @Autowired
    CheckingCounterOccupyingInfoDao checkingCounterOccupyingInfoDao;
    @Autowired
    BoardingGateOccupyingInfoDao boardingGateOccupyingInfoDao;
    @Autowired
    SlideCoastOccupyingInfoDao slideCoastOccupyingInfoDao;
    @Autowired
    DepartureHallOccupyingInfoDao departureHallOccupyingInfoDao;
    @Autowired
    SecurityCheckinOccupyingInfoDao securityCheckinOccupyingInfoDao;
    @Autowired
    GateOccupyingInfoDao gateOccupyingInfoDao;
    @Autowired
    FlightPairProgressInfoDao flightPairProgressInfoDao;


    public void batchDelete(Map<String, List> ociDatas, List<FlightPairProgressInfo> progressList) {
        if (ociDatas != null) {
            List<CarouselOccupyingInfo> carouselOccupyingInfos = ociDatas.get("行李转盘");
            List<ArrivalGateOccupyingInfo> arrivalGateOccupyingInfos = ociDatas.get("到港门");
            List<CheckingCounterOccupyingInfo> checkingCounterOccupyingInfos = ociDatas.get("值机柜台");
            List<BoardingGateOccupyingInfo> boardingGateOccupyingInfos = ociDatas.get("登机口");
            List<SlideCoastOccupyingInfo> slideCoastOccupyingInfos = ociDatas.get("滑槽");
            List<DepartureHallOccupyingInfo> departureHallOccupyingInfos = ociDatas.get("候机厅");
            List<SecurityCheckinOccupyingInfo> securityCheckinOccupyingInfos = ociDatas.get("安检口");
            List<GateOccupyingInfo> gateOccupyingInfos = ociDatas.get("机位");
            if (!Collections3.isEmpty(carouselOccupyingInfos))
                carouselOccupyingInfoDao.batchDelete(ImmutableMap.of("ids", carouselOccupyingInfos.stream().map(info -> info.getId()).collect(Collectors.toList())));
            if (!Collections3.isEmpty(arrivalGateOccupyingInfos))
                arrivalGateOccupyingInfoDao.batchDelete(ImmutableMap.of("ids", arrivalGateOccupyingInfos.stream().map(info -> info.getId()).collect(Collectors.toList())));
            if (!Collections3.isEmpty(checkingCounterOccupyingInfos))
                checkingCounterOccupyingInfoDao.batchDelete(ImmutableMap.of("ids", checkingCounterOccupyingInfos.stream().map(info -> info.getId()).collect(Collectors.toList())));
            if (!Collections3.isEmpty(boardingGateOccupyingInfos))
                boardingGateOccupyingInfoDao.batchDelete(ImmutableMap.of("ids", boardingGateOccupyingInfos.stream().map(info -> info.getId()).collect(Collectors.toList())));
            if (!Collections3.isEmpty(slideCoastOccupyingInfos))
                slideCoastOccupyingInfoDao.batchDelete(ImmutableMap.of("ids", slideCoastOccupyingInfos.stream().map(info -> info.getId()).collect(Collectors.toList())));
            if (!Collections3.isEmpty(departureHallOccupyingInfos))
                departureHallOccupyingInfoDao.batchDelete(ImmutableMap.of("ids", departureHallOccupyingInfos.stream().map(info -> info.getId()).collect(Collectors.toList())));
            if (!Collections3.isEmpty(securityCheckinOccupyingInfos))
                securityCheckinOccupyingInfoDao.batchDelete(ImmutableMap.of("ids", securityCheckinOccupyingInfos.stream().map(info -> info.getId()).collect(Collectors.toList())));
            if (!Collections3.isEmpty(gateOccupyingInfos))
                gateOccupyingInfoDao.batchDelete(ImmutableMap.of("ids", gateOccupyingInfos.stream().map(info -> info.getId()).collect(Collectors.toList())));
        }
        if (!Collections3.isEmpty(progressList)) {
            List<String> ids = Lists.newArrayList();
            for (int i = 0; i < progressList.size(); i++) {
                if ((i + 1) % 1000 == 0) {
                    flightPairProgressInfoDao.batchDelete(ImmutableMap.of("ids", ids));
                    ids.clear();
                }
                ids.add(progressList.get(i).getId());
            }
            flightPairProgressInfoDao.batchDelete(ImmutableMap.of("ids", ids));
        }
    }

    private void batchDelete(List<DataEntity> list) {
        String tpl = "com.thinkgem.jeesite.modules.rms.dao.{}Dao";
        String clazzDao = StringUtils.tpl(tpl, list.get(0).getClass().getSimpleName());
        List<String> ids = list.stream().map(o -> {
            return o.getId();
        }).collect(Collectors.toList());

        try {
            Class clazz = Class.forName(clazzDao);
            ((CrudDao) SpringContextHolder.getBean(clazz)).batchDelete(ImmutableMap.of("ids", ids));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
