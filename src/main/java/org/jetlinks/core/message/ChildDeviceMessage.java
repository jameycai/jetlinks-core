package org.jetlinks.core.message;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.jetlinks.core.enums.ErrorCode;
import org.jetlinks.core.exception.DeviceOperationException;

import java.util.HashSet;
import java.util.Set;

/**
 * 发往子设备的消息,通常是通过网关设备接入平台的设备.
 *
 * @author zhouhao
 * @see Message
 * @see ChildDeviceMessageReply
 * @see org.jetlinks.core.device.DeviceConfigKey#parentGatewayId
 * @since 1.0.0
 */
@Getter
@Setter
public class ChildDeviceMessage extends CommonDeviceMessage implements RepayableDeviceMessage<ChildDeviceMessageReply> {
    private String childDeviceId;

    private Message childDeviceMessage;

    @Override
    public ChildDeviceMessageReply newReply() {
        ChildDeviceMessageReply reply = new ChildDeviceMessageReply();
        reply.messageId(getMessageId());
        reply.deviceId(getDeviceId());
        reply.setChildDeviceId(getChildDeviceId());
        return reply;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        if (null != childDeviceMessage) {
            json.put("childDeviceMessage", childDeviceMessage.toJson());
        }
        return json;
    }

    public MessageType getMessageType() {
        return MessageType.CHILD;
    }

    @Override
    public void validate() {
        if (childDeviceMessage instanceof ChildDeviceMessage) {
            Set<String> deviceId = new HashSet<>();
            Message msg = childDeviceMessage;
            do {
                String childId = ((ChildDeviceMessage) msg).getChildDeviceId();
                msg = ((ChildDeviceMessage) msg).getChildDeviceMessage();
                if (deviceId.contains(childId)) {
                    throw new DeviceOperationException(ErrorCode.CYCLIC_DEPENDENCE, "子设备消息存在循环引用");
                }
                deviceId.add(childId);
            } while (msg instanceof ChildDeviceMessage);
        }
    }
}
