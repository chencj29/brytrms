package cn.net.metadata.webservice;

import javax.jws.WebService;

@WebService
public interface ProgressActSetService {
    String setProgressAct(String message);
}
