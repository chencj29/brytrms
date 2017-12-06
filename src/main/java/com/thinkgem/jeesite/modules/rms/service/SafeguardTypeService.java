/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rms.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rms.dao.SafeguardTypeDao;
import com.thinkgem.jeesite.modules.rms.entity.SafeguardType;
import com.thinkgem.jeesite.modules.rms.entity.TreeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保障类型管理Service
 *
 * @author chrischen
 * @version 2016-03-15
 */
@Service
@Transactional(readOnly = true)
public class SafeguardTypeService extends CrudService<SafeguardTypeDao, SafeguardType> {

    public SafeguardType get(String id) {
        return super.get(id);
    }

    public List<SafeguardType> findList(SafeguardType safeguardType) {
        return super.findList(safeguardType);
    }

    public Page<SafeguardType> findPage(Page<SafeguardType> page, SafeguardType safeguardType) {
        return super.findPage(page, safeguardType);
    }

    @Transactional(readOnly = false)
    public void save(SafeguardType safeguardType) {
        if (StringUtils.isBlank(safeguardType.getFlightCompanyCode())) {
            safeguardType.setFlightCompanyName(null);
        }
        if (safeguardType.getParentSafeguardCode().equals("root")) {
            safeguardType.setType("1");
        } else {
            safeguardType.setType("2");
        }
        super.save(safeguardType);
    }

    @Transactional(readOnly = false)
    public void delete(SafeguardType safeguardType) {
        super.delete(safeguardType);
    }

    @Transactional(readOnly = true)
    public List<TreeVO> getRootAndLevelOneType(String rootName) {
        List<TreeVO> aTree = new ArrayList<TreeVO>();

        TreeVO root = new TreeVO();
        if (StringUtils.isNotBlank(rootName)) {
            root.setText(rootName);
        } else {
            root.setText("保障主类型");
        }

        root.setId("root");
        root.setState("open");

        List<TreeVO> levelOneTreeVOs = new ArrayList<TreeVO>();
        SafeguardType safeguardType = new SafeguardType();
        safeguardType.setParentSafeguardCode("root");
        List<SafeguardType> stList = super.dao.findByParentCode(safeguardType);
        for (SafeguardType st : stList) {
            TreeVO treeVO = new TreeVO();
            treeVO.setId(st.getSafeguardTypeCode());
            treeVO.setText(st.getSafeguardTypeName());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("obj", st);
            treeVO.setParams(params);
            treeVO.setChildren(null);
            treeVO.setState("close");
            levelOneTreeVOs.add(treeVO);
        }

        root.setChildren(levelOneTreeVOs);
        aTree.add(root);

        return aTree;
    }

    @Transactional(readOnly = true)
    public List<TreeVO> getWholeTree(String rootName) {
        List<TreeVO> aTree = new ArrayList<TreeVO>();
        TreeVO root = new TreeVO();
        if (StringUtils.isNotBlank(rootName)) {
            root.setText(rootName);
        } else {
            root.setText("保障主类型");
        }
        root.setId("root");
        root.setState("open");
        SafeguardType safeguardType = new SafeguardType();
        safeguardType.setParentSafeguardCode("root");
        root.setChildren(getTreesByecursion(safeguardType));
        aTree.add(root);
        return aTree;
    }

    @Transactional(readOnly = true)
    public List<TreeVO> getTypeTree() {
        SafeguardType safeguardType = new SafeguardType();
        safeguardType.setParentSafeguardCode("root");
        List<TreeVO> safeguardTypeList = getTreesByecursion(safeguardType);
        return safeguardTypeList;
    }

    /**
     * 递归查询所有子类型
     *
     * @param safeguardType
     * @return
     */
    private List<TreeVO> getTreesByecursion(SafeguardType safeguardType) {
        List<TreeVO> treeVOs = new ArrayList<TreeVO>();
        List<SafeguardType> stList = super.dao.findByParentCode(safeguardType);
        for (SafeguardType st : stList) {
            TreeVO treeVO = new TreeVO();
            treeVO.setId(st.getSafeguardTypeCode());
            treeVO.setText(st.getSafeguardTypeName());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("obj", st);
            treeVO.setParams(params);

            SafeguardType safeg = new SafeguardType();
            safeg.setParentSafeguardCode(st.getSafeguardTypeCode());

            treeVO.setChildren(getTreesByecursion(safeg));
            treeVOs.add(treeVO);
        }
        return treeVOs;
    }

    public List<SafeguardType> getSubList() {
        return dao.getSubList();
    }

}