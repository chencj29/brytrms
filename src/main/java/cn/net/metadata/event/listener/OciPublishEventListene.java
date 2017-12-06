package cn.net.metadata.event.listener;

import cn.net.metadata.event.OciPublishEvent;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.ams.utils.MsgWebServiceUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Map,{"syncNo":"***OccupyingInfo_2fe8d431-0eb0-48c8-8ded-78038bcffcaf_INSERT","ociList":List<***OccupyingInfo>}
 * Created by wjp on 2017/5/27.
 */
@Component
public class OciPublishEventListene implements ApplicationListener<OciPublishEvent> {
    @Override
    public void onApplicationEvent(OciPublishEvent event) {
        Map<String, Object> map = (Map) event.getSource();
        ArrayList<String> ociJsonList = (ArrayList<String>) map.get("ociList");
        String syncNo = (String) map.get("syncNo");

        if (Boolean.parseBoolean(Global.getConfig("syncMiddle"))) {
            MsgWebServiceUtils.publishOci(syncNo, ociJsonList);
        }
    }
}
