package com.thinkgem.jeesite.common.utils;

import com.bstek.urule.Utils;
import com.bstek.urule.console.repository.RepositoryService;
import com.bstek.urule.console.repository.model.ResourcePackage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UruleUtils {
    final static RepositoryService repositoryService = (RepositoryService) Utils.getApplicationContext().getBean(RepositoryService.BEAN_ID);

    //获取资源包名称及id
    public static Map getUrulePackageMap() {
        Map<String, String> map = new HashMap<>();
        if (repositoryService == null) return map;

        List<String> existProjects = repositoryService.loadProject("bryt").stream().map((project) -> project.getName()).collect(Collectors.toList());
        if (!Collections3.isEmpty(existProjects)) {
            for (String existProject : existProjects) {
                try {
                    List<ResourcePackage> resourcePackages = repositoryService.loadProjectResourcePackages(existProject);
                    if (!Collections3.isEmpty(resourcePackages)) {
                        for (ResourcePackage aPackage : resourcePackages) {
                            if (aPackage.getId().endsWith("_DIST.PKG")) {
                                map.put(aPackage.getId(), aPackage.getName());
                            }
                        }
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
        return map;
    }

    public static String getNameById(String packageId) {
        if (StringUtils.isBlank(packageId)) return "";
        Map<String, String> map = getUrulePackageMap();
        String name = map.get(packageId);
        if (name == null) return "";
        return name;
    }
}
