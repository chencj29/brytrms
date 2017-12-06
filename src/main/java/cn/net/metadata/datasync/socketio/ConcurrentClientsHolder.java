package cn.net.metadata.datasync.socketio;

import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xiaowu on 16/8/1.
 */
public final class ConcurrentClientsHolder {
    private static final Map<String, Socket> clientsMap = new ConcurrentHashMap<>();
    private static final String socketServerUrl = DictUtils.getDictValueConstant("socket_io_setting", "url");


    public static final Socket getSocket(String namespace) {
        if (exists(namespace)) return clientsMap.get(namespace);
        return newSocket(namespace);
    }

    public static final void put(String namespace, Socket instance) {
        clientsMap.put(namespace, instance);
    }

    public static final boolean exists(String namespace) {
        return clientsMap.containsKey(namespace);
    }

    private static final Socket newSocket(String namespace) {
        Socket socket = null;
        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.query = "ns=" + namespace;

        try {
            socket = IO.socket(socketServerUrl, opts).connect();
            if (socket != null) put(namespace, socket);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.err.println("创建SocketIO实例失败, namespace=" + namespace);
        }

        return socket;
    }

}
