package ym.chat;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
import ym.chat.util.RandomUtil;

/**
 * @Author: ym
 * @Description: 虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
 * @Date: 2019/11/10 6:20 下午
 * @Version:
 */
//WebSocket客户端建立连接的地址
@ServerEndpoint(value = "/websocket/{user}")
@Component
@Slf4j
public class webSocket {
    /**
     * 在线人数
     */
    public static AtomicInteger onlineNumber = new AtomicInteger(0);

    /**
     * 所有的对象，每次连接建立，都会将我们自己定义的MyWebSocket存放到List中，
     */
    public static List<webSocket> webSockets = new CopyOnWriteArrayList<webSocket>();

    /**
     * 会话，与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 每个会话的用户
     */
    private String user;

    /**
     * /后端RSA密钥对
     */
    private String pubk;
    private String prik;
    /**
     * 前端公钥，局限于前端需要把rsa密钥对写死，不智能
     */
    private String publickey;
    /**
     * 多个客户端可以有自己不同的密钥对
     */
    private static Map<String, String> publickeymap = new HashMap<>();
    private final static String RSA_PUBLIC = "rsa_public";
    private final static String DATA_SPLIT = "key:";


    /**
     * 建立连接
     *
     * @param session
     */


    @OnOpen
    public void onOpen(Session session, @PathParam("user") String user) throws NoSuchAlgorithmException {
        //        生成rsa密钥对,每次调用结果都不一样，所以服务端这边必须统一，放在方法外面，用于封装随机产生的公钥与私钥
        Map<Integer, String> keyMap = RSA.genKeyPair();


        if (user == null || "".equals(user)) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        onlineNumber.incrementAndGet();
        //如果是名称一样的user就剔除，断开连接
        for (webSocket myWebSocket : webSockets) {
            if (user.equals(myWebSocket.user)) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
        }
        this.session = session;
        this.user = user;
        //私钥
        this.prik = keyMap.get(1);
        //公钥
        this.pubk = keyMap.get(0);
        //        建立连接的时候产生后端RSA密钥对
        webSockets.add(this);
        JSONObject object = new JSONObject();
        try {
            //响应连接的时候将公钥返回给前端完成交换
            object.put("pubK", this.pubk);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //   将pub转为对象发送到前端
        this.sendMessage(object.toString());

        System.out.println("有新连接加入！ 当前在线人数" + onlineNumber.get());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber.decrementAndGet();
        webSockets.remove(this);
        System.out.println("有连接关闭！ 当前在线人数" + onlineNumber.get());
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("user") String user) {
        //        接收前端公钥,只有建立连接的时候调用那一次是成立的
        /**
         * 存储前端公钥
         */
        if (message.startsWith(RSA_PUBLIC)) {
            log.info("前端传来:{} 的公钥: {}", user, message.substring(RSA_PUBLIC.length()));
            //所有客户端统一使用一样的rsa密钥对的时候可以这样搞
            this.publickey = message.substring(RSA_PUBLIC.length());
            //把每个客户端自己的公钥分开存储，不然发消息的时候会覆盖
            publickeymap.put(session.getId(), message.substring(RSA_PUBLIC.length()));

        } else {
            log.info("前端传来消息内容: {}", message);
            try {
                //            解密消息
                //先拆分message得到 加密内容和加密key
                String[] split = message.split(DATA_SPLIT);
                //加密消息内容
                String aesData = split[0].trim();
                log.info("加密内容: {}", aesData);
                //加密key
                String dataKey = split[1].trim();
                log.info("加密key: {}", dataKey);
                //解密key
                String key = RSA.decrypt(dataKey, prik);
                log.info("解密key: {}", key);

                //解密内容
                String str = Aes.decrypt(aesData, key);
                log.info("解密内容: {}", str);

                System.out.println("来自" + user + "消息：" + str);
                //            加密之后再发送
                //            一定要注意不能在这里加密，因为在下一个方法中user和str拼成了一个字符串，这是个大坑，花了很多时间
                pushMessage(user, str, null, session);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息推送 这里如果连接了多个客户端的话，服务器端必须使用每个客户端自己的公钥加密， 不然会出现一个客户端发消息，只有自己能解密，其他的都不能解密，
     * 因为服务端这里用的是发消息那个客户端的公钥给所有客户端发消息， 除非所有客户端都是用一样的rsa密钥对
     *
     * @param message
     * @param flag    flag为空则推送全部人员
     * @param
     */
    public void pushMessage(String user, String message, String flag, Session session) throws Exception {
        if (flag == null || "".equals(flag)) {
            for (webSocket myWebSocket : webSockets) {
                //为了安全就随机生成一个key，不再使用前端发过来的key,不然加密数据是一样的
                String key = RandomUtil.generateRandomStr(16);
                log.info("随机生成AESkey: {}", key);
                //加密消息
                String encrypt = Aes.encrypt(user + ":" + message, key);
                log.info("AES加密后的消息内容: {}", encrypt);

                //用前端公钥加密key， 每一次加密结果不一样，因为rsa用了随机数
                //用每个客户端自己的pubkey
                log.info("正在给: {}转发消息，使用的公钥是：{}", user, publickeymap.get(session.getId()));
                String aesKey = RSA.encrypt(key, publickeymap.get(myWebSocket.session.getId()));
                //String aesKey = RSA.encrypt(key, publickey);
                log.info("RSA加密后的AESkey: {}", aesKey);

                myWebSocket.sendMessage(encrypt + DATA_SPLIT + aesKey);
                log.info("发送内容为: {}", encrypt + DATA_SPLIT + aesKey);


            }
        } else {
            for (webSocket myWebSocket : webSockets) {
                if (flag.equals(myWebSocket.user)) {
                    myWebSocket.sendMessage(message);
                }
            }
        }

    }

}
