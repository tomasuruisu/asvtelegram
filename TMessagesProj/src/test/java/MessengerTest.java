import org.junit.Test;

import org.telegram.messenger.BaseController;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DispatchQueue;

import android.os.Message;

import org.telegram.messenger.MessagesController;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

public class MessengerTest {

    @Test
    public void messageSent() {
        final int MESSAGE_SEND_STATE_SENT = 0;
        TLRPC.TL_message newMsg = new TLRPC.TL_message();
        newMsg.send_state = MESSAGE_SEND_STATE_SENT;
        MessageObject messageObject = new MessageObject(1, newMsg,
                "message", "messageName", "userName",
                false, false, false, false);

        assertTrue(messageObject.isSent());
    }

    @Test
    public void messageIsPhoto() {
        TLRPC.TL_message newMsg = new TLRPC.TL_message();
        newMsg.media = new TLRPC.TL_messageMediaPhoto();

        assertTrue(MessageObject.isPhoto(newMsg));
    }

    @Test
    public void hasGroupChatAdminRights() {
        TLRPC.TL_chat tl_chat = new TLRPC.TL_chat();
        tl_chat.creator = true;

        assertTrue(ChatObject.hasAdminRights(tl_chat));
    }

    @Test
    public void ensurePhotoMessageIsSecret() {
        TLRPC.TL_message_secret tl_message_secret = new TLRPC.TL_message_secret();
        tl_message_secret.media = new TLRPC.TL_messageMediaPhoto();
        tl_message_secret.ttl = 1;
        MessageObject messageObject = new MessageObject(1, tl_message_secret,
                "secret message", "messageName", "userName",
                false, false, false, true);

        assertTrue(messageObject.shouldEncryptPhotoOrVideo()
                && messageObject.needDrawBluredPreview() && messageObject.isSecretMedia());
    }

    @Test
    public void setUserPhoneNumber() {
        String phoneNumber = "0612345678";
        TLRPC.User user = new TLRPC.TL_user();
        user.phone = phoneNumber;
        UserConfig userConfig = new UserConfig(1);

        userConfig.setCurrentUser(user);

        assertSame(userConfig.getClientPhone(), phoneNumber);
    }
}
