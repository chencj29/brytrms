package cn.net.metadata.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * Map,{"syncNo":"***OccupyingInfo_2fe8d431-0eb0-48c8-8ded-78038bcffcaf_INSERT","ociList":List<***OccupyingInfo>}
 * 发送占用更新数据
 * Created by wjp on 2017/5/27.
 */
public class OciPublishEvent extends ApplicationEvent {

    public OciPublishEvent(Map<String, Object> map) {
        super(map);
    }
}
