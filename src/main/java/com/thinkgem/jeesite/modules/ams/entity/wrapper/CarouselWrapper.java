package com.thinkgem.jeesite.modules.ams.entity.wrapper;

import com.thinkgem.jeesite.modules.rms.entity.CarouselOccupyingInfo;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by xiaowu on 3/30/16.
 */
@Alias(value = "CarouselWrapper")
public class CarouselWrapper extends CarouselOccupyingInfo {
    String id;
    @Deprecated
    String flightNum;
    String flightDynamicCode;
    String aircraftNum;
    String flightDynamicId;
    String flightCompanyCode;
    String aircraftTypeCode;


    private Date departurePlanTime;  //计起

    private Date etd; // 预起

    private Date atd; //实起

    private Date arrivalPlanTime;

    private Date eta;
    private Date ata;


    private String placeNum;

    String status;

    Date planDate;

    String agentCode;

    @Override
    public String getFlightDynamicCode() {
        return flightDynamicCode;
    }

    @Override
    public void setFlightDynamicCode(String flightDynamicCode) {
        this.flightDynamicCode = flightDynamicCode;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDeparturePlanTime() {
        return departurePlanTime;
    }

    public void setDeparturePlanTime(Date departurePlanTime) {
        this.departurePlanTime = departurePlanTime;
    }

    public Date getEtd() {

        return etd;
    }

    public void setEtd(Date etd) {
        this.etd = etd;
    }

    public Date getAtd() {
        return atd;
    }

    public void setAtd(Date atd) {
        this.atd = atd;
    }

    public Date getArrivalPlanTime() {
        return arrivalPlanTime;
    }

    public void setArrivalPlanTime(Date arrivalPlanTime) {
        this.arrivalPlanTime = arrivalPlanTime;
    }

    public Date getEta() {
        return eta;
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    public Date getAta() {
        return ata;
    }

    public void setAta(Date ata) {
        this.ata = ata;
    }

    public String getFlightCompanyCode() {
        return flightCompanyCode;
    }

    public void setFlightCompanyCode(String flightCompanyCode) {
        this.flightCompanyCode = flightCompanyCode;
    }

    public String getAircraftTypeCode() {
        return aircraftTypeCode;
    }

    public void setAircraftTypeCode(String aircraftTypeCode) {
        this.aircraftTypeCode = aircraftTypeCode;
    }

    public String getPlaceNum() {
        return placeNum;
    }

    public void setPlaceNum(String placeNum) {
        this.placeNum = placeNum;
    }

    @Override
    public String getFlightDynamicId() {
        return flightDynamicId;
    }

    @Override
    public void setFlightDynamicId(String flightDynamicId) {
        this.flightDynamicId = flightDynamicId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Deprecated
    public String getFlightNum() {
        return flightNum;
    }

    @Deprecated
    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getAircraftNum() {
        return aircraftNum;
    }

    public void setAircraftNum(String aircraftNum) {
        this.aircraftNum = aircraftNum;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
}
