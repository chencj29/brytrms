package com.thinkgem.jeesite.modules.ams.entity.common;

import com.thinkgem.jeesite.modules.sys.entity.Menu;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiaopo on 15/12/8.
 */
public class MenuTreeWrapper implements Serializable {

    private static final long serialVersionUID = 9013412161233355224L;
    private Menu menu;
    private List<MenuTreeWrapper> chindren;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<MenuTreeWrapper> getChindren() {
        return chindren;
    }

    public void setChindren(List<MenuTreeWrapper> chindren) {
        this.chindren = chindren;
    }
}
