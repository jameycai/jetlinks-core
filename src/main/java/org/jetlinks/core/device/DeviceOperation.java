package org.jetlinks.core.device;


import org.jetlinks.core.metadata.DeviceMetadata;

/**
 * 设备操作接口
 *
 * @author zhouhao
 * @since 1.0.0
 */
public interface DeviceOperation extends Configurable {

    /**
     * @return 当前设备连接所在服务器ID，如果设备未上线{@link DeviceState#online}，则返回{@link null}
     */
    String getServerId();

    /**
     * @return 当前设备连接会话ID
     */
    String getSessionId();

    /**
     * @param state 状态
     * @see DeviceState#online
     */
    void putState(byte state);

    /**
     * @return 获取当前状态
     */
    byte getState();

    /**
     * 执行ping,设备保活
     */
    void ping();

    /**
     * 检查设备的真实状态
     */
    void checkState();

    /**
     * @return 最后一次ping的时间
     */
    long getLastPingTime();

    /**
     * @return 上线时间
     */
    long getOnlineTime();

    /**
     * @return 离线时间
     */
    long getOfflineTime();

    /**
     * 设备上线
     *
     * @param serverId  设备所在服务ID
     * @param sessionId 会话ID
     */
    void online(String serverId, String sessionId);

    /**
     * @return 是否在线
     */
    default boolean isOnline() {
        return getState() == DeviceState.online;
    }

    /**
     * 设置设备离线
     */
    void offline();

    /**
     * 进行授权
     *
     * @param request 授权请求
     * @return 授权结果
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);

    /**
     * @return 获取设备的元数据
     */
    DeviceMetadata getMetadata();

    /**
     * @return 消息操作
     */
    DeviceMessageSender messageSender();

    /**
     * @return 获取设备的基本信息
     */
    DeviceInfo getDeviceInfo();

    /**
     * 更新设备基本信息
     *
     * @param deviceInfo 设备信息
     */
    void update(DeviceInfo deviceInfo);

    /**
     * 更新元数据
     *
     * @param metadata 元数据
     */
    void updateMetadata(String metadata);

}