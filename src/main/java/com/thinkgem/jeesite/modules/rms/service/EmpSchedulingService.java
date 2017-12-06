/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.google.common.collect.ImmutableMap;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.rms.dao.EmpSchedulingDao;
import com.thinkgem.jeesite.modules.rms.entity.*;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 工作人员排班管理Service
 *
 * @author xiaopo
 * @version 2016-05-19
 */
@Service
@Transactional(readOnly = true)
public class EmpSchedulingService extends CrudService<EmpSchedulingDao, EmpScheduling> {

	private static Logger log = LoggerFactory.getLogger(EmpSchedulingService.class);

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");


	@Autowired
	private OfficeService officeService;

	@Autowired
	private RmsDutyGroupService rmsDutyGroupService;

	@Autowired
	private RmsEmpService rmsEmpService;

	@Autowired
	private RmsEmpVacationService vacationService;

	@Autowired
	private RmsBusinessNatureService businessNatureService;

	@Autowired
	private CheckinCounterService checkinCounterService;

	@Autowired
	private BoardingGateService boardingGateService;

	@Autowired
	private ArrivalGateService arrivalGateService;

	@Autowired
	private RmsCarouselService carouselService;

	@Autowired
	private AircraftStandService aircraftStandService;

	@Autowired
	private SlideCoastService slideCoastService;

	@Autowired
	private SecurityCheckinService securityCheckinService;

	@Autowired
	private DepartureHallService departureHallService;


	public EmpScheduling get(String id) {
		return super.get(id);
	}

	public List<EmpScheduling> findList(EmpScheduling empScheduling) {
		return super.findList(empScheduling);
	}

	public Page<EmpScheduling> findPage(Page<EmpScheduling> page, EmpScheduling empScheduling) {
		return super.findPage(page, empScheduling);
	}

	@Transactional(readOnly = false)
	public void save(EmpScheduling empScheduling) {
		// dept 部门
		Office dept = officeService.get(empScheduling.getDeptId());
		empScheduling.setDeptName(dept.getName());
		// post 岗位
		Office post = officeService.get(empScheduling.getPostId());
		empScheduling.setPostName(post.getName());

		super.save(empScheduling);
	}

	@Transactional(readOnly = false)
	public void delete(EmpScheduling empScheduling) {
		super.delete(empScheduling);
	}


	@Transactional(readOnly = false)
	public void scheduling(Date startDate, Date endDate, String postId, EmpScheduling inScheduling) throws ParseException {
		if(StringUtils.isBlank(postId) || startDate == null || endDate == null) {
			throw new RuntimeException("参数缺失,请重试!");
		}

		endDate = sdf.parse(sdf3.format(endDate) + " " + "23:59:59");

		if(startDate.after(endDate)) {
			throw new RuntimeException("结束时间小于开始时间,不能分配.");
		}
		//
		Office office = officeService.get(postId);
		if(office == null) {
			throw new RuntimeException("岗位不存在!");
		}
		log.info("开始对[{}]进行排班...", office.getName());

		// 查询岗位业务性质表,获取一个班组所需人数
		RmsBusinessNature businessNature = new RmsBusinessNature();
		businessNature.setPostId(postId);
		List<RmsBusinessNature> businessNatureList = businessNatureService.findList(businessNature);

		// 判断员工人数是否足够
		if(businessNatureList == null || businessNatureList.size() == 0) {
			throw new RuntimeException("该岗位尚无业务性质.");
		}

		// 查询该岗位所需要的班组
		RmsDutyGroup dutyGroup = new RmsDutyGroup();
		dutyGroup.setPostId(postId);
		List<RmsDutyGroup> dutyGroupList = rmsDutyGroupService.findList(dutyGroup);
		if(dutyGroupList == null || dutyGroupList.size() == 0) {
			throw new RuntimeException("该业务尚未分配班组.");
		}

		//判断班组的人数和业务数量是否符合
		dutyGroupList.stream().forEach(rmsDutyGroup -> {
			if(rmsDutyGroup.getWorkerCount() < businessNatureList.size()) {
				throw new RuntimeException(String.format("[%s]班组人数小于业务人数", rmsDutyGroup.getGroupName()));
			}
		});

		// 获取所有班组所需的总员工数
		int allEmpCount = businessNatureList.size() * dutyGroupList.size();

		// 根据部门ID查询下面所有员工
		RmsEmp emp = new RmsEmp();
		emp.setPostId(postId);
		List<RmsEmp> empList = rmsEmpService.findList(emp);

		//判断该部门所有员工是否足够
		if(empList == null || empList.size() < allEmpCount) {
			throw new RuntimeException("该业务所在部门的员工数量不足.");
		}

		// 根据日期查询该岗位下所有员工和请假信息
		String[] ids = StringUtils.listField2Array(empList, "id");
		Map paramsMap = ImmutableMap.of("startDate", startDate, "endDate", endDate, "postId", postId);
		List<EmpWrapper> empWrapperList = vacationService.findVacationByDateAndPostIds(paramsMap);

		// 给所有员工分配班组
		List<EmpScheduling> empSchedulings = new ArrayList<>(), empSchedulingsVacation = new ArrayList<>();
		EmpScheduling scheduling = null;

		// 按照工作人员分组,业务技能不足将排除
		HashMap<String, List<EmpWrapper>> empTempMap = allocationEmpWrapperByNature(empWrapperList, businessNatureList);

		// 判断所需要的业务组和实际可工作的组
		if(empTempMap == null || dutyGroupList.size() > empTempMap.keySet().size()) {
			throw new RuntimeException("实际工作组小于所需要的工作组,分配失败.");
		}

		List<String> empTempKeys = Arrays.asList(empTempMap.keySet().toArray(new String[] {}));


		/********* 开始分配 **********/
		log.info("************ {}到{}工作人员排班开始分配 ************", startDate, endDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		// 业务组按照时间排序
		dutyGroupList = dutyGroupList.stream().sorted((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime())).collect(Collectors.toList());

		List<String[]> sortedPlan = getSortedDuty(dutyGroupList);
		int q = 0;
		List<EmpScheduling> daySchedulings = null;
		// 开始分配第n天
		while(calendar.getTime().before(endDate) || calendar.getTime().compareTo(endDate) == 0) {
			daySchedulings = new ArrayList<>();
			String nowDay = sdf3.format(calendar.getTime());
			// 获取第N天排序方案
			if(q == sortedPlan.size()) q = 0;
			String[] sortedPlanTemp = sortedPlan.get(q);

			// 恢复已分配的业务技能
			empTempMap.forEach((s, empWrapperTemp) -> empWrapperTemp.forEach(empT -> empT.set_skill(null)));

			List<EmpScheduling> dutyEmpSchedulingList = null;
			boolean f = false;
			EmpWrapper empTempVacation = null;
			for(int i = 0; i < dutyGroupList.size(); i++) {
				Date dutyStartDateTime = sdf.parse(nowDay + " " + dutyGroupList.get(i).getStartTime());
				Date dutyEndDateTime = sdf.parse(nowDay + " " + dutyGroupList.get(i).getEndTime());
				dutyEmpSchedulingList = new ArrayList<>();
				String sorted = sortedPlanTemp[i];
				f = false;
				//
				List<EmpWrapper> empWrappers = allocationEmpWrapperByDutyGroup(dutyGroupList.get(i), empTempMap, calendar, sorted);
				dutyGroupList.get(i).set_allocationEmpList(empWrappers);

				String[] names = StringUtils.listField2Array(empWrappers, "empName");
				log.info("{}日,{}组分配的员工是:{}", sdf.format(calendar.getTime()), dutyGroupList.get(i).getGroupName(), names);


				// 给每个员工分配具体任务
				allocationNature(empWrappers, businessNatureList);

				for(EmpWrapper empWrapper : empWrappers) {
					// 判断该员工是否在今天请假了
					if(empWrapper.get_empVacationList() != null && empWrapper.get_empVacationList().size() > 0) {
						for(RmsEmpVacation empVacation : empWrapper.get_empVacationList()) {
							if((empVacation.getStartTime().before(dutyStartDateTime) && empVacation.getEndTime().after(dutyStartDateTime))
									|| (empVacation.getStartTime().before(dutyStartDateTime) && empVacation.getEndTime().after(dutyEndDateTime))
									|| (empVacation.getStartTime().after(dutyStartDateTime) && empVacation.getEndTime().before(dutyEndDateTime))
									|| (empVacation.getStartTime().before(dutyEndDateTime) && empVacation.getEndTime().after(dutyEndDateTime))) {
								empTempVacation = empWrapper;
								f = true;
								break;
							}
						}
					}

					// 构造排班数据
					scheduling = new EmpScheduling();
					scheduling.setDeptId(empWrapper.getDeptId());
					scheduling.setDeptName(empWrapper.getDept());
					scheduling.setPostId(empWrapper.getPostId());
					scheduling.setPostName(empWrapper.getPost());
					scheduling.setDutyStartTime(dutyStartDateTime);
					scheduling.setDutyEndTime(dutyEndDateTime);
					scheduling.setEmpName(empWrapper.getEmpName());
					scheduling.setJobNum(empWrapper.getJobNum());
					scheduling.setGroupId(dutyGroupList.get(i).getId());
					scheduling.setGroupName(dutyGroupList.get(i).getGroupName());
					scheduling.setNatureName(empWrapper.get_skill());
					scheduling.setWorkingType(inScheduling.getWorkingType());
					scheduling.setWorkingTypeId(inScheduling.getWorkingTypeId());
					scheduling.setWorkingArea(inScheduling.getWorkingArea());
					scheduling.setWorkingAreaId(inScheduling.getWorkingAreaId());
					scheduling.setOtherWorkingArea(inScheduling.getOtherWorkingArea());

					dutyEmpSchedulingList.add(scheduling);
				}

				if(f) {
					empSchedulingsVacation.addAll(dutyEmpSchedulingList);
					log.error("{}日因员工[{}](所在组:{})请假导致业务组{}不能分配工作人员,请手动调整", nowDay, empTempVacation.getEmpName(), empTempVacation.getDutyGroup(), dutyGroupList.get(i).getGroupName());
					break;
				}

				// 把该组分配好的数据加入总数据中
				daySchedulings.addAll(dutyEmpSchedulingList);
			}
			// 当前天可分配
			if(!f) {
				empSchedulings.addAll(daySchedulings);
			}

			calendar.add(Calendar.DAY_OF_MONTH, 1);
			++q;
		}

		// 删除原来分配的
		empSchedulings.stream().forEach(empScheduling -> dao.deleteByDateAndPostId(sdf3.format(empScheduling.getDutyStartTime()), empScheduling.getPostId()));

		empSchedulings.stream().forEach(empScheduling -> {
			log.info("时间:{}到{},业务组:{},员工:{},任务:{}", sdf.format(empScheduling.getDutyStartTime()), sdf.format(empScheduling.getDutyEndTime()), empScheduling.getGroupName(), empScheduling.getEmpName(), empScheduling.getNatureName());

			//保存
			save(empScheduling);
		});


	}

	private void allocationNature(List<EmpWrapper> empWrapperList, List<RmsBusinessNature> businessNatureList) {
		// 排序防止获取不到数据
		empWrapperList = empWrapperList.stream().sorted((o1, o2) -> o1.getSkill().split(",").length - o2.getSkill().split(",").length).collect(Collectors.toList());

		for(RmsBusinessNature businessNature : businessNatureList) {
			boolean f = false;
			for(EmpWrapper empWrapper : empWrapperList) {
				if(empWrapper.get_skill() != null && empWrapper.get_skill().length() > 0) continue;
				//
				String[] tempSkill = empWrapper.getSkill().split(",");
				if(tempSkill.length == 1 && empWrapper.getSkill().equals(businessNature.getBussinessNatureName())) {
					empWrapper.set_skill(businessNature.getBussinessNatureName());
					f = true;
					break;
				} else if(tempSkill.length > 1) {
					if(Arrays.asList(tempSkill).contains(businessNature.getBussinessNatureName())) {
						empWrapper.set_skill(businessNature.getBussinessNatureName());
						f = true;
						break;
					}
				}
			}
			if(!f) {
				throw new RuntimeException("该组业务技能无法完成该岗位任务.");
			}
		}
	}

	private List<EmpWrapper> allocationEmpWrapperByDutyGroup(RmsDutyGroup dutyGroup, HashMap<String, List<EmpWrapper>> hashMap, Calendar calendarNowDate, String sorted) throws ParseException {
		List<EmpWrapper> result = null;
		Calendar startDate = calendarNowDate, endDate = calendarNowDate;
		// 获取业务组工作时间
		Date dutyStartTime = sdf2.parse(dutyGroup.getStartTime()), dutyEndTime = sdf2.parse(dutyGroup.getEndTime());
		Calendar dutyStartDate = Calendar.getInstance(), dutyEndDate = Calendar.getInstance();
		dutyStartDate.setTime(dutyStartTime);
		dutyEndDate.setTime(dutyEndTime);

		// 拼接当前时间和工作组时间
		startDate.set(Calendar.HOUR_OF_DAY, dutyStartDate.get(Calendar.HOUR_OF_DAY));
		startDate.set(Calendar.MINUTE, dutyStartDate.get(Calendar.MINUTE));
		startDate.set(Calendar.SECOND, dutyStartDate.get(Calendar.SECOND));
		//
		endDate.set(Calendar.HOUR_OF_DAY, dutyEndDate.get(Calendar.HOUR_OF_DAY));
		endDate.set(Calendar.MINUTE, dutyEndDate.get(Calendar.MINUTE));
		endDate.set(Calendar.SECOND, dutyEndDate.get(Calendar.SECOND));

		List<EmpWrapper> tempEmpWrapper = hashMap.get(hashMap.keySet().toArray()[Integer.valueOf(sorted)]);
		/*// 判断请假时间和业务组时间是否有冲突
		for(EmpWrapper empWrapper : tempEmpWrapper) {
			if(empWrapper.getStartTime() != null && empWrapper.getEndTime() != null) {
				if(startDate.getTime().before(empWrapper.getStartTime()) && endDate.getTime().after(empWrapper.getStartTime())) {
					break;
				} else if(startDate.getTime().before(empWrapper.getStartTime()) && endDate.getTime().after(empWrapper.getEndTime())) {
					break;
				} else if(startDate.getTime().after(empWrapper.getStartTime()) && endDate.getTime().before(empWrapper.getEndTime())) {
					break;
				} else if(startDate.getTime().before(empWrapper.getEndTime()) && endDate.getTime().after(empWrapper.getEndTime())) {
					break;
				}
			}
		}*/
		result = tempEmpWrapper;
		return result;
	}

	private HashMap<String, List<EmpWrapper>> allocationEmpWrapperByNature(List<EmpWrapper> empWrapperList, List<RmsBusinessNature> businessNature) {
		HashMap<String, List<EmpWrapper>> empGroupMap = new HashMap<>();
		// 获取该业务所需所有技能
		String[] natures = StringUtils.listField2Array(businessNature, "bussinessNatureName");
		// 获取改部门所有的组
		String[] groups = StringUtils.listField2Array(empWrapperList, "dutyGroup");

		// 获取第一组,判断员工请假是否能够安排
		for(String group : groups) {
			List<EmpWrapper> tempList = empWrapperList.stream().filter(emp -> emp.getDutyGroup().equals(group)).collect(Collectors.toList());

			// 判断该组员工是否已使用
			if(tempList.get(0).get_isAllocation() != null && tempList.get(0).get_isAllocation()) {
				log.info("该组[{}]员工已分配业务组.", group);
				break;
			}

			// 判断该组员工能够满足业务
			if(tempList == null || tempList.size() < businessNature.size()) {
				log.info("该组[{}]员工数量不足,不能满足业务需求.", group);
				continue;
			}

			// 获取该组员工所有技能,判断该组员工所有技能能够满足该业务
			if(judgeEmpNautres(empWrapperList, natures, group)) {
				log.info("该组[{}]员工技能不足,不能满足业务需求.", group);
				continue;
			}

			empGroupMap.put(group, tempList);

		}
		return empGroupMap;
	}

	/**
	 * 判断员工技能是否能够满足要求
	 *
	 * @param empWrapperList
	 * @param natures
	 * @param group
	 *
	 * @return
	 */
	private boolean judgeEmpNautres(List<EmpWrapper> empWrapperList, String[] natures, String group) {
		List<String> naturesList = StringUtils.listField2Collection(empWrapperList, "skill");
		List<String> naturesListTemp = new ArrayList<>();
		naturesList.stream().forEach(s -> naturesListTemp.addAll(Arrays.asList(s.split(","))));
		naturesList = naturesListTemp.stream().distinct().collect(Collectors.toList());
		// 判断是可以满足业务
		boolean f = false;
		for(String nature : natures) {
			f = false;
			if(!naturesList.contains(nature)) {
				f = true;
				break;
			}
		}
		if(f) {
			log.info("该组[{}]员工所有技能无法满足该业务需求.", group);
			return true;
		}
		return false;
	}


	/**
	 * 轮班 序号生成
	 *
	 * @param dutyGroups
	 *
	 * @return
	 */
	public List<String[]> getSortedDuty(List<RmsDutyGroup> dutyGroups) {
		String[] nums = new String[dutyGroups.size()];
		for(int i = 0; i < dutyGroups.size(); i++) {
			nums[i] = i + "";
		}
		List<String[]> strings = new ArrayList<>();
		String[] temp = new String[nums.length];
		for(int k = 0; k < nums.length; k++) {
			temp = new String[nums.length];
			int q = k;
			for(int i = 0; i < nums.length; i++) {
				if(q == nums.length) q = 0;
				temp[i] = nums[q];
				++q;
			}
			strings.add(temp);
		}

		return strings;
	}

	public void getWorkingArea(List<Map<String, Object>> mapList, WorkingTypeEnum typeEnum) {
		if(mapList == null || typeEnum == null)
			throw new RuntimeException("typeEnum参数不能为空");

		switch(typeEnum) {
			case CHECKIN:
				List<CheckinCounter> counterList = checkinCounterService.findList(new CheckinCounter());
				counterList.stream().forEach(it -> {
					Map map = new HashedMap();
					map.put("id", it.getId());
					map.put("name", it.getCheckinCounterNum());
					mapList.add(map);
				});
				break;
			case BOARDING_GATE:
				List<BoardingGate> boardingGateList = boardingGateService.findList(new BoardingGate());
				boardingGateList.stream().forEach(it -> {
					Map map = new HashedMap();
					map.put("id", it.getId());
					map.put("name", it.getBoardingGateNum());
					mapList.add(map);
				});
				break;
			case DUTY_DOOR:
				List<ArrivalGate> arrivalGateList = arrivalGateService.findList(new ArrivalGate());
				arrivalGateList.stream().forEach(it -> {
					Map map = new HashedMap();
					map.put("id", it.getId());
					map.put("name", it.getArrivalGateNum());
					mapList.add(map);
				});
				break;
			case CAROUSEL:
				List<RmsCarousel> rmsCarouselList = carouselService.findList(new RmsCarousel());
				rmsCarouselList.stream().forEach(it -> {
					Map map = new HashedMap();
					map.put("id", it.getId());
					map.put("name", it.getCarouselNum());
					mapList.add(map);
				});
				break;
			case SEATS:
				List<AircraftStand> aircraftStandList = aircraftStandService.findList(new AircraftStand());
				aircraftStandList.stream().forEach(it -> {
					Map map = new HashedMap();
					map.put("id", it.getId());
					map.put("name", it.getAircraftStandNum());
					mapList.add(map);
				});
				break;
			case CHUTE:
				List<SlideCoast> slideCoastList = slideCoastService.findList(new SlideCoast());
				slideCoastList.stream().forEach(it -> {
					Map map = new HashedMap();
					map.put("id", it.getId());
					map.put("name", it.getSlideCoastNum());
					mapList.add(map);
				});
				break;
			case CHECKPOINT:
				List<SecurityCheckin> securityCheckinList = securityCheckinService.findList(new SecurityCheckin());
				securityCheckinList.stream().forEach(it -> {
					Map map = new HashedMap();
					map.put("id", it.getId());
					map.put("name", it.getScecurityCheckinNum());
					mapList.add(map);
				});
				break;
			case TERMINAL:
				List<DepartureHall> departureHallList = departureHallService.findList(new DepartureHall());
				departureHallList.stream().forEach(it -> {
					Map map = new HashedMap();
					map.put("id", it.getId());
					map.put("name", it.getDepartureHallNum());
					mapList.add(map);
				});
				break;
		}
	}


	public void schedulingHistory(Date historyStartTime, Date historyEndTime, Date startTime, Date endTime) {
		if(historyStartTime == null || historyEndTime == null || startTime == null || endTime == null)
			throw new RuntimeException("参数不能为空!");

		// 根据历史日期查询出这一段时间内所有的排班数据
		EmpScheduling scheduling = new EmpScheduling();
		scheduling.setDutyStartTime(historyStartTime);
		scheduling.setDutyEndTime(historyEndTime);
		List<EmpScheduling> empSchedulingList = findList(scheduling);
		List<EmpScheduling> insertSchedulingList = new ArrayList<>();
		if(empSchedulingList == null || empSchedulingList.size() == 0)
			throw new RuntimeException("历史数据为空!");

		// 获取historyendtime - historystarttime 的时间段
		int historyDay = historyEndTime.compareTo(historyStartTime) + 1;
		int day = endTime.compareTo(startTime) + 1;
		int multiple = day / historyDay;
		if(day % historyDay != 0)
			throw new RuntimeException("排班时间必须是历史时间的倍数!");

		// 加上时间差 构造新数据
		int diffDay = (int) DateUtils.getDistanceOfTwoDate(historyStartTime, startTime);
		Calendar calendar = Calendar.getInstance();
		for(int i = 0; i < multiple; i++) {
			List<EmpScheduling> temp = new ArrayList();
			empSchedulingList.forEach(sc -> {
				EmpScheduling tempScheduling = new EmpScheduling();
				BeanUtils.copyProperties(sc, tempScheduling);
				temp.add(tempScheduling);
			});
//			Collections.copy(temp, empSchedulingList);

			for(EmpScheduling sc : temp) {
				calendar.setTime(sc.getDutyStartTime());
				calendar.add(Calendar.DAY_OF_MONTH, diffDay + i);
				sc.setDutyStartTime(calendar.getTime());
				//
				calendar.setTime(sc.getDutyEndTime());
				calendar.add(Calendar.DAY_OF_MONTH, diffDay + i);
				sc.setDutyEndTime(calendar.getTime());
			}
			insertSchedulingList.addAll(temp);
		}

		//
		if(insertSchedulingList != null && insertSchedulingList.size() > 0) {
			insertSchedulingList.stream().forEach(sc -> {
				sc.setActualStartTime(null);
				sc.setActualEndTime(null);
				sc.setId(null);

				save(sc);
			});
		}
	}


	public List<EmpScheduling> findListStatistics(EmpScheduling empScheduling) {
		return dao.findListStatistics(empScheduling);
	}
}