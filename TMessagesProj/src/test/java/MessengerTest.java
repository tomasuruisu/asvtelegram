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

    /*
     * Are the values CORRECT?
     * Yes, all values conform to the expected format.
     * All values are within a reasonable range: true/false,
     * no empty strings or 0-values (unless intended) and there are no null-values passed on.
     * There is also no reference to anything external that could influence it.
     * No unnecessary values are instantiated.
     * Everything is done in the correct order in a synchronous manner.
     */
    @Test
    public void messageSent() {
        // Arrange
        final int MESSAGE_SEND_STATE_SENT = 0;
        boolean messageSent;
        TLRPC.TL_message newMsg = new TLRPC.TL_message();
        newMsg.send_state = MESSAGE_SEND_STATE_SENT;
        MessageObject messageObject = new MessageObject(1, newMsg,
                "message", "messageName", "userName",
                false, false, false, false);

        // Act
        messageSent = messageObject.isSent();

        // Assert
        assertTrue(messageSent);
    }

    /*
     * Are the values CORRECT?
     * Yes, all values conform to the expected format.
     * All values are within a reasonable range: true/false,
     * no empty strings or 0-values (unless intended) and there are no null-values passed on.
     * There is also no reference to anything external that could influence it.
     * No unnecessary values are instantiated.
     * Everything is done in the correct order in a synchronous manner.
     */
    @Test
    public void messageIsPhoto() {
        // Arrange
        boolean messageIsPhoto;
        TLRPC.TL_message newMsg = new TLRPC.TL_message();
        newMsg.media = new TLRPC.TL_messageMediaPhoto();

        // Act
        messageIsPhoto = MessageObject.isPhoto(newMsg);

        // Assert
        assertTrue(messageIsPhoto);
    }

    /*
     * Are the values CORRECT?
     * Yes, all values conform to the expected format.
     * All values are within a reasonable range: true/false,
     * no empty strings or 0-values (unless intended) and there are no null-values passed on.
     * There is also no reference to anything external that could influence it.
     * No unnecessary values are instantiated.
     * Everything is done in the correct order in a synchronous manner.
     */
    @Test
    public void hasGroupChatAdminRights() {
        // Arrange
        boolean hasGroupChatAdminRights;
        TLRPC.TL_chat tl_chat = new TLRPC.TL_chat();
        tl_chat.creator = true;

        // Act
        hasGroupChatAdminRights = ChatObject.hasAdminRights(tl_chat);

        // Assert
        assertTrue(hasGroupChatAdminRights);
    }

    /*
     * Are the values CORRECT?
     * Yes, all values conform to the expected format.
     * All values are within a reasonable range: true/false,
     * no empty strings or 0-values (unless intended) and there are no null-values passed on.
     * There is also no reference to anything external that could influence it.
     * No unnecessary values are instantiated.
     * Everything is done in the correct order in a synchronous manner.
     */
    @Test
    public void ensurePhotoMessageIsSecret() {
        // Arrange
        boolean photoMessageIsSecret;
        TLRPC.TL_message_secret tl_message_secret = new TLRPC.TL_message_secret();
        tl_message_secret.media = new TLRPC.TL_messageMediaPhoto();
        tl_message_secret.ttl = 1;
        MessageObject messageObject = new MessageObject(1, tl_message_secret,
                "secret message", "messageName", "userName",
                false, false,false, true);

        // Act
        photoMessageIsSecret = messageObject.shouldEncryptPhotoOrVideo()
                && messageObject.needDrawBluredPreview() && messageObject.isSecretMedia();

        // Assert
        assertTrue(photoMessageIsSecret);
    }

    /*
     * Are the values CORRECT?
     * Yes, all values conform to the expected format.
     * All values are within a reasonable range: true/false,
     * no empty strings or 0-values (unless intended) and there are no null-values passed on.
     * There is also no reference to anything external that could influence it.
     * No unnecessary values are instantiated.
     * Everything is done in the correct order in a synchronous manner.
     */
    @Test
    public void setUserPhoneNumber() {
        // Arrange
        String phoneNumber = "0612345678";
        TLRPC.User user = new TLRPC.TL_user();
        user.phone = phoneNumber;
        UserConfig userConfig = new UserConfig(1);

        // Act
        userConfig.setCurrentUser(user);

        // Assert
        assertSame(userConfig.getClientPhone(), phoneNumber);
    }
}
