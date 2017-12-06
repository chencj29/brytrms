/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ams.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ams.dao.AircraftParametersDao;
import com.thinkgem.jeesite.modules.ams.entity.AircraftParameters;
import com.thinkgem.jeesite.modules.ams.entity.common.AutoCompleteData;
import com.thinkgem.jeesite.modules.ams.entity.common.AutoCompleteParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机型参数管理Service
 *
 * @author xiaopo
 * @version 2015-12-14
 */
@Service
@Transactional(readOnly = true)
public class AircraftParametersService extends CrudService<AircraftParametersDao, AircraftParameters> {

	/*@Autowired
    private AmsAddressDao amsAddressDao;*/

    public AircraftParameters get(String id) {
        return super.get(id);
    }

    public List<AircraftParameters> findList(AircraftParameters aircraftParameters) {
        return super.findList(aircraftParameters);
    }

    public Page<AircraftParameters> findPage(Page<AircraftParameters> page, AircraftParameters aircraftParameters) {
        return super.findPage(page, aircraftParameters);
    }

    @Transactional(readOnly = false)
    public void save(AircraftParameters aircraftParameters) {
        super.save(aircraftParameters);
    }

    @Transactional(readOnly = false)
    public void delete(AircraftParameters aircraftParameters) {
        super.delete(aircraftParameters);
    }

    @Transactional(readOnly = false)
    public List<AutoCompleteData> getTypeCode(AutoCompleteParams autocompleteparams) {
        return super.dao.findModelCode(autocompleteparams);
    }

    @Transactional(readOnly = true)
    public AircraftParameters findByAircraftModelCode(String modelCode) {
        return dao.findByAircraftModelCode(modelCode);
    }


    @Transactional(readOnly = true)
    public List<String> groupByModel() {
        return dao.groupByModel();
    }

}